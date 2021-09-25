package com.alertslambda.dal.jobs;


import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * A data access layer interface for storing talent switch job
 */
public interface JobsDAO {
    @SqlQuery("SELECT count(timestamp) FROM jobs WHERE timestamp BETWEEN now() - INTERVAL 1 WEEK AND now()")
    int getWeekJobCount();

    @SqlQuery("SELECT count(timestamp) FROM jobs WHERE timestamp BETWEEN now() - INTERVAL 1 WEEK AND now()")
    int getWeek2JobCount();
}