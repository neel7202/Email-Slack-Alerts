package com.alertslambda.dal.jobs;

import com.alertslambda.objects.Job;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Result mapper for job object
 */
public class JobsMapper implements ResultSetMapper<Job> {
    @Override
    public Job map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        int id = r.getInt("id");
        String timestamp = r.getString("timestamp");
        String lastUpdate = r.getString("last_update");
        String originID = r.getString("origin_id");
        String name = r.getString("name");
        String company = r.getString("company");
        String integration = r.getString("integration");
        String disposeDate = r.getString("dispose_date");
        String seniority = r.getString("seniority");
        String type = r.getString("type");
        int ts_user_id = r.getInt("ts_user_id");
        String department = r.getString("department");
        String origin_department = r.getString("origin_department");

        return new Job(id, timestamp, lastUpdate,
                originID, name, company,
                integration, disposeDate,
                seniority, type, ts_user_id,
                origin_department, department);
    }
}