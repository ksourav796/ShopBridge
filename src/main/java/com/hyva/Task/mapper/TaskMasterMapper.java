package com.hyva.Task.mapper;

import com.hyva.Task.entities.TaskMaster;

import com.hyva.Task.pojo.TaskMasterPojo;

import java.util.ArrayList;
import java.util.List;

public class TaskMasterMapper {
    public static TaskMaster mapPojoToEntity(TaskMasterPojo pojo){
          TaskMaster taskMaster=new TaskMaster();
        taskMaster.setTaskId(pojo.getTaskId());
        taskMaster.setTaskName(pojo.getTaskName());
        taskMaster.setTaskFrequency(pojo.getTaskFrequency());
        taskMaster.setTaskBusinessSegment(pojo.getTaskBusinessSegment());
        taskMaster.setTaskCategory(pojo.getTaskCategory());
        taskMaster.setTaskProvenanceFlag(pojo.getTaskProvenanceFlag());
        taskMaster.setTaskNature(pojo.getTaskNature());
        taskMaster.setTaskWeightageScore(pojo.getTaskWeightageScore());
        taskMaster.setTaskCreatedBy(pojo.getTaskCreatedBy());
        taskMaster.setTaskCreationTimestamp(pojo.getTaskCreationTimestamp());
        taskMaster.setTaskApprover(pojo.getTaskApprover());
        taskMaster.setStatus(pojo.getStatus());
        return taskMaster;
    }
    public static List<TaskMasterPojo> mapEntityToPojo(List<TaskMaster> taskMasterList){
        List<TaskMasterPojo> list=new ArrayList<>();
        for(TaskMaster taskMaster:taskMasterList) {
            TaskMasterPojo taskMasterPojo = new TaskMasterPojo();
            taskMasterPojo.setTaskId(taskMaster.getTaskId());
            taskMasterPojo.setTaskName(taskMaster.getTaskName());
            taskMasterPojo.setTaskFrequency(taskMaster.getTaskFrequency());
            taskMasterPojo.setTaskBusinessSegment(taskMaster.getTaskBusinessSegment());
            taskMasterPojo.setTaskCategory(taskMaster.getTaskCategory());
            taskMasterPojo.setTaskProvenanceFlag(taskMaster.getTaskProvenanceFlag());
            taskMasterPojo.setTaskNature(taskMaster.getTaskNature());
            taskMasterPojo.setTaskWeightageScore(taskMaster.getTaskWeightageScore());
            taskMasterPojo.setTaskCreatedBy(taskMaster.getTaskCreatedBy());
            taskMasterPojo.setTaskCreationTimestamp(taskMaster.getTaskCreationTimestamp());
            taskMasterPojo.setTaskApprover(taskMaster.getTaskApprover());
            taskMasterPojo.setStatus(taskMaster.getStatus());
            list.add(taskMasterPojo);
        }
        return list;
    }
}

