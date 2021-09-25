package com.alertslambda.dal.applicants;

import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface ApplicantsDAO {
    @SqlQuery("SELECT count(*) FROM applicants WHERE timestamp BETWEEN now() - INTERVAL 1 WEEK AND now()")
    int lastWeekApplicantCount();

    @SqlQuery("SELECT count(*) FROM applicants WHERE timestamp BETWEEN now() - INTERVAL 1 WEEK AND now()")
    int thisWeekApplicantCount();

    @SqlQuery("SELECT count(race) FROM applicants WHERE race = 'TRUE' AND timestamp BETWEEN now() - INTERVAL 4 WEEK and now()")
    int raceMinorityCandidateCount();

    @SqlQuery("SELECT count(*) FROM applicants WHERE timestamp BETWEEN now() - INTERVAL 4 WEEK and now()")
    int totalCandidateCount();

    @SqlQuery("SELECT count(*) FROM applicants WHERE gender = 'FEMALE' AND timestamp BETWEEN now() - INTERVAL 4 WEEK and now()")
    int femaleCandidateCount();

    @SqlQuery("SELECT count(*) FROM applicants WHERE veteran = 1 AND timestamp BETWEEN now() - INTERVAL 4 WEEK and now()")
    int veteranCandidateCount();

    @SqlQuery("SELECT count(*) FROM applicants WHERE opt_in = 1 AND timestamp BETWEEN now() - INTERVAL 1 WEEK and now()")
    int lastWeekOptInCount();

    @SqlQuery("SELECT count(*) FROM applicants WHERE opt_in = 1 AND timestamp BETWEEN now() - INTERVAL 1 WEEK and now()")
    int thisWeekOptInCount();

    @SqlQuery("SELECT count(*) FROM recommendations WHERE timestamp BETWEEN now() - INTERVAL 1 WEEK and now()")
    int lastWeekRecommendationCount();

    @SqlQuery("SELECT count(*) FROM recommendations WHERE timestamp BETWEEN now() - INTERVAL 1 WEEK and now()")
    int thisWeekRecommendationCount();
}
