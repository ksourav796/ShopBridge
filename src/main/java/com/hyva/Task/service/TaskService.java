package com.hyva.Task.service;

import com.hyva.Task.entities.*;

import com.hyva.Task.pojo.*;
import com.hyva.Task.mapper.*;
import com.hyva.Task.respositories.*;

import com.hyva.Task.util.ObjectMapperUtils;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


;
import java.util.*;
import java.util.List;


@Component
public class TaskService {

    @Autowired
    BsUserRepository bsUserRepository;

    @Autowired
    taskUserRepository taskUserRepository;

    @Autowired
    TaskMasterRepository taskMasterRepository;


    int paginatedConstants=5;

    public User userValidate(UserPojo userPojo) {
        User user = bsUserRepository.findByUserNameAndPasswordUserAndStatus(
                userPojo.getUserName(), userPojo.getPasswordUser(),"Active");
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

//    public User saveUserDetails(UserPojo userPojo) {
//        User user = null;
//        user = bsUserRepository.findByEmail(userPojo.getEmail());
//        if (user != null) {
//            user = null;
//        } else {
//            user = UserMapper.mapPojoToEntity(userPojo);
//            bsUserRepository.save(user);
//        }
//        return user;
//    }

    public TaskMasterPojo savetask(TaskMasterPojo taskMasterPojo) throws JSONException {
        List<TaskMaster>list=new ArrayList<>();
        if(taskMasterPojo.getTaskId()!=null){
            list=taskMasterRepository.findByTaskNameAndTaskCategoryAndTaskIdNotIn(taskMasterPojo.getTaskName(),taskMasterPojo.getTaskCategory(),taskMasterPojo.getTaskId());
        }
        else{
            list=taskMasterRepository.findByTaskName(taskMasterPojo.getTaskName());
        }
        if(list.size()==0){
            TaskMaster taskMaster = null;
            taskMaster = TaskMasterMapper.mapPojoToEntity(taskMasterPojo);
            taskMasterRepository.save(taskMaster);
            return taskMasterPojo;
        }
        else{
            return null;
        }

    }

    public void getDeleteTask(Long id){
        taskMasterRepository.delete(id);
    }



    public UserPojo getuserDetails(UserPojo userPojo){
        List<User>list=new ArrayList<>();
        if(userPojo.getUseraccountId()!=null){
            list= taskUserRepository.findByUserNameAndUseraccountIdNotIn(userPojo.getUserName(), userPojo.getUseraccountId());
        }
        else {
            list= taskUserRepository.findByUserName(userPojo.getUserName());
        }
        if(list.size()==0){
            User user = UserMapper.mapPojoToEntity(userPojo);
            taskUserRepository.save(user);
            return userPojo;
        }else{
            return null;
        }

    }

    public List<UserPojo> getUserList() {
        List<User> user = (List<User>) taskUserRepository.findAll();
        List<UserPojo> usersPojo1 = ObjectMapperUtils.mapAll(user, UserPojo.class);
        return usersPojo1;
    }

    public BasePojo getPaginatedTaskList(BasePojo basePojo,String searchText) {
        List<TaskMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"taskId"));
        if(basePojo.isLastPage()==true){
            List<TaskMaster> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = taskMasterRepository.findAllByTaskNameContaining(searchText);
            }else {
                list1=taskMasterRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"taskId"));
        }
        TaskMaster guests=new TaskMaster();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"taskId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            guests=taskMasterRepository.findFirstByTaskNameAndTaskCategoryContaining(searchText,sort);
            list = taskMasterRepository.findAllByTaskNameContaining(searchText,pageable);
        } else {
            guests=taskMasterRepository.findFirstBy(sort);
            list = taskMasterRepository.findAllBy(pageable);
        }
        if(list.contains(guests)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<TaskMasterPojo> typesList =TaskMasterMapper.mapEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }


    public void getDeleteUser(Long useraccountId) {
        taskUserRepository.delete(useraccountId);
    }




    public BasePojo calculatePagination(BasePojo basePojo,int size){
        if(basePojo.isLastPage()==true){
            basePojo.setFirstPage(false);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(false);
        }else if(basePojo.isFirstPage()==true){
            basePojo.setLastPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(true);
            if(basePojo.isStatus()==true){
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        }else if(basePojo.isNextPage()==true){
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setPrevPage(false);
            basePojo.setNextPage(false);
            if(basePojo.isStatus()==true){
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        }else if(basePojo.isPrevPage()==true){
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(false);
            if(basePojo.isStatus()==true){
                basePojo.setPrevPage(true);
                basePojo.setFirstPage(true);
            }
        }
        if(size==0){
            basePojo.setLastPage(true);
            basePojo.setFirstPage(true);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }
        return basePojo;
    }

    public BasePojo getPaginatedUsersList(BasePojo basePojo,String searchText) {
        List<User> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"useraccountId"));
        if(basePojo.isLastPage()==true){
            List<User> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = taskUserRepository.findAllByUserNameContaining(searchText);
            }else {
                list1= taskUserRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"useraccountId"));
        }
        User user=new User();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"useraccountId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            user= taskUserRepository.findFirstByUserNameContaining(searchText,sort);
            list = taskUserRepository.findAllByUserNameContaining(searchText,pageable);
        } else {
            user= taskUserRepository.findFirstBy(sort);
            list = taskUserRepository.findAllBy(pageable);
        }
        if(list.contains(user)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<UserPojo> typesList =UserMapper.mapEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }



    public List<UserPojo> getUserPojoList(String searchText){
        List<User> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = taskUserRepository.findByUserName(searchText);
        } else {
            list = taskUserRepository.findAll();
        }
        List<UserPojo> userPojoList =UserMapper.mapEntityToPojo(list);
        return userPojoList;
    }





}





