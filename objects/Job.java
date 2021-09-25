package com.alertslambda.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job {
    private int id;
    private String timestamp;
    private String last_update;
    private String origin_id;
    private String name;
    private String company;
    private String integration;
    private String disposeDate;
    private String seniority;
    private String type;
    private int ts_user_id;
    private String origin_department;
    private String department;

    public Job(int id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}