package com.hyva.Task.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity

public class TaskMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long taskId;
    private String taskName;
    private String taskFrequency;
    private String taskBusinessSegment;
    private String taskCategory;
    private String taskProvenanceFlag;
    private String taskNature;
    private Integer taskWeightageScore;
    private String taskCreatedBy;
    private Date taskCreationTimestamp;
    private String taskApprover;
    private String status;


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskFrequency() {
        return taskFrequency;
    }

    public void setTaskFrequency(String taskFrequency) {
        this.taskFrequency = taskFrequency;
    }

    public String getTaskBusinessSegment() {
        return taskBusinessSegment;
    }

    public void setTaskBusinessSegment(String taskBusinessSegment) {
        this.taskBusinessSegment = taskBusinessSegment;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskProvenanceFlag() {
        return taskProvenanceFlag;
    }

    public void setTaskProvenanceFlag(String taskProvenanceFlag) {
        this.taskProvenanceFlag = taskProvenanceFlag;
    }

    public String getTaskNature() {
        return taskNature;
    }

    public void setTaskNature(String taskNature) {
        this.taskNature = taskNature;
    }

    public Integer getTaskWeightageScore() {
        return taskWeightageScore;
    }

    public void setTaskWeightageScore(Integer taskWeightageScore) {
        this.taskWeightageScore = taskWeightageScore;
    }

    public String getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(String taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }

    public Date getTaskCreationTimestamp() {
        return taskCreationTimestamp;
    }

    public void setTaskCreationTimestamp(Date taskCreationTimestamp) {
        this.taskCreationTimestamp = taskCreationTimestamp;
    }

    public String getTaskApprover() {
        return taskApprover;
    }

    public void setTaskApprover(String taskApprover) {
        this.taskApprover = taskApprover;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
