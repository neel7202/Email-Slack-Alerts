package com.alertslambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.alertslambda.dal.applicants.ApplicantsDAO;
import com.alertslambda.dal.jobs.JobsDAO;
import com.alertslambda.infrastructure.ResourcesProvider;
import com.alertslambda.objects.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EventHandler implements RequestHandler<Map<String, Object>, AwsResponse> {
    @Override
    public AwsResponse handleRequest(Map<String, Object> input, Context context) {
        ApplicantsDAO applicantsDAO = ResourcesProvider.getInstance().getApplicantsDAO();
        JobsDAO jobsDAO = ResourcesProvider.getInstance().getJobsDAO();

        double lastWeekJobCount = jobsDAO.getWeekJobCount();
        double thisWeekJobCount = jobsDAO.getWeek2JobCount();
        // One sided test so used -infinity to ensure it will not be an anomaly on decrease side
        jobOrEmailAnomaly("new jobs", lastWeekJobCount, thisWeekJobCount, 75, -10000000);

        double lastWeekApplicantCount = applicantsDAO.lastWeekApplicantCount();
        double thisWeekApplicantCount = applicantsDAO.thisWeekApplicantCount();
        // No alert if not greater than a 75 percent increase and not less than a 100 percent decrease
        jobOrEmailAnomaly("new candidates", lastWeekApplicantCount, thisWeekApplicantCount, 75, -100);

        double raceMinorityCount = applicantsDAO.raceMinorityCandidateCount();
        double totalCount = applicantsDAO.totalCandidateCount();
        diversityAnomaly("race minority proportion", raceMinorityCount, totalCount, 0.4, 0.2);

        double femaleCount = applicantsDAO.femaleCandidateCount();
        diversityAnomaly("female proportion", femaleCount, totalCount, 0.55, 0.4);

        double veteranCount = applicantsDAO.veteranCandidateCount();
        diversityAnomaly("veteran proportion", veteranCount, totalCount, 0.15, 0.05);

        double lastWeekOptInCount = applicantsDAO.lastWeekOptInCount();
        double thisWeekOptInCount = applicantsDAO.thisWeekOptInCount();
        jobOrEmailAnomaly("new opt-ins", lastWeekOptInCount, thisWeekOptInCount, 40, -50);

        double lastWeekRecommendationCount = applicantsDAO.lastWeekRecommendationCount();
        double thisWeekRecommendationCount = applicantsDAO.thisWeekRecommendationCount();
        jobOrEmailAnomaly("new recommendations", lastWeekRecommendationCount, thisWeekRecommendationCount, 100, -70);

        return new AwsResponse();
    }

    public void jobOrEmailAnomaly(String statistic, double lastWeekCount, double thisWeekCount, int testAnomalyInc, int testAnomalyDec) {
        double percentageChange = Math.round((thisWeekCount / lastWeekCount) * 100 - 100);

        if (percentageChange > testAnomalyInc || percentageChange < testAnomalyDec) {
            String alert = "ALERT: There is a new jobs/emails anomaly. The " + statistic + " count has changed by " + percentageChange + " percent since the last time period.";
            System.out.println(alert);
            sendEmail(alert);
            sendSlack(alert);
        } else {
            System.out.println("There is no anomaly for this job/email");
        }
    }

    public void diversityAnomaly(String statistic, double diversityCount, double totalCount, double maxProportion, double minProportion) {
        double dataProp = diversityCount / totalCount;

        if (dataProp > maxProportion || dataProp < minProportion) {
            String alert;

            if (dataProp > maxProportion) {
                alert = "ALERT: There is a new diversity anomaly: the " + statistic + " is greater than " + Math.round(dataProp * 100) + " percent, which does not fulfill diversity standards";
                System.out.println(alert);
            } else {
                alert = "ALERT: There is a new diversity anomaly: the " + statistic + " is less than " + Math.round(dataProp * 100) + " percent, which does not fulfill diversity standards";
                System.out.println(alert);
            }

            sendEmail(alert);
            sendSlack(alert);
        } else {
            System.out.println("There is no anomaly for this diversity statistic");
        }
    }

    public void sendEmail(String alert) {
        MailerService mailerService = new MailerService();
        Map<String, String> mailInfo = new HashMap<String, String>();
        mailInfo.put("TYPE_OF_MAIL", "SENDGRID_TEXT");
        mailInfo.put("content", alert);
        mailInfo.put("subject", "new Alert");

        Map<String, String> substitution = new HashMap<String, String>();
        mailerService.send("dev_user@company.co", mailInfo, substitution);
    }

    public void sendSlack(String alert) {
        SlackMessage slackmessage = SlackMessage.builder()
                .username("alertBot")
                .text(alert)
                .build();
        SlackUtils.sendMessage(slackmessage);
    }
}
