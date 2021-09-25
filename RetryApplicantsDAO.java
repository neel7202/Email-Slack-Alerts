package com.alertslambda.dal.applicants;

import com.alertslambda.exceptions.DBException;
import com.alertslambda.infrastructure.jdbi.applicants.ApplicantsJdbcProvider;
import com.alertslambda.infrastructure.retry.Retry;

public class RetryApplicantsDAO implements ApplicantsDAO {
    private final Retry retry;
    private final ApplicantsDAO applicantsDao;

    public RetryApplicantsDAO(Retry retry, ApplicantsJdbcProvider jdbcProvider) {
        this.retry = retry;
        this.applicantsDao = jdbcProvider.getApplicantsDAO();
    }

    @Override
    public int lastWeekApplicantCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.lastWeekApplicantCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting last week's candidate count", e);
        }
    }

    @Override
    public int thisWeekApplicantCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.thisWeekApplicantCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting this week's candidate count", e);
        }
    }

    @Override
    public int raceMinorityCandidateCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.raceMinorityCandidateCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting minority candidate count", e);
        }
    }

    @Override
    public int totalCandidateCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.totalCandidateCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting total candidate count", e);
        }
    }

    @Override
    public int femaleCandidateCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.femaleCandidateCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting total female candidate count", e);
        }
    }

    @Override
    public int veteranCandidateCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.veteranCandidateCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting all veteran candidate count", e);
        }
    }

    @Override
    public int lastWeekOptInCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.lastWeekOptInCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting last week's opt ins count", e);
        }
    }

    @Override
    public int thisWeekOptInCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.thisWeekOptInCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting this week's opt ins count", e);
        }
    }

    @Override
    public int lastWeekRecommendationCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.lastWeekRecommendationCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting last week's recommendations count", e);
        }
    }

    @Override
    public int thisWeekRecommendationCount() {
        try {
            return retry.retryGetNow(() -> applicantsDao.thisWeekRecommendationCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting this week's recommendations count", e);
        }
    }
}

