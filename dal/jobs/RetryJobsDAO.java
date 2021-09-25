package com.alertslambda.dal.jobs;

import com.alertslambda.exceptions.DBException;
import com.alertslambda.infrastructure.jdbi.jobs.JobsJdbcProvider;
import com.alertslambda.infrastructure.retry.Retry;
import com.alertslambda.objects.Job;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * A retrying data access layer implementation of the JobsDAO
 */
@Slf4j
public class RetryJobsDAO implements JobsDAO {
    private final Retry retry;
    private final JobsDAO jobsDao;

    public RetryJobsDAO(Retry retry, JobsJdbcProvider jdbcProvider) {
        this.retry = retry;
        this.jobsDao = jdbcProvider.getJobsDao();
    }

    @Override
    public int getWeekJobCount() {
        try {
            return retry.retryGetNow(() -> jobsDao.getWeekJobCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting last week's job count", e);
        }
    }

    @Override
    public int getWeek2JobCount() {
        try {
            return retry.retryGetNow(() -> jobsDao.getWeek2JobCount());
        } catch (RuntimeException e) {
            throw new DBException("Error getting this week's job count", e);
        }
    }
}

