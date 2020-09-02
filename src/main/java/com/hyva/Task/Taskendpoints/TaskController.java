package com.hyva.Task.Taskendpoints;

import com.hyva.Task.entities.*;
import com.hyva.Task.pojo.*;
import com.hyva.Task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;


    @RequestMapping(value = "/userValidate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User userValidate(@RequestBody UserPojo userPojo) {
        return taskService.userValidate(userPojo);
    }


    @RequestMapping(value = "/getuserDetails", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity getuserDetails(@RequestBody UserPojo userpojo) {
        userpojo = taskService.getuserDetails(userpojo);
        return ResponseEntity.status(200).body(userpojo);
    }
    @RequestMapping(value = "/getPaginatedTaskList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedTaskList(@RequestParam(value = "searchText",required = false) String searchText,
                                                 @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(taskService.getPaginatedTaskList(basePojo,searchText));
    }



    @RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getUserList() {
        List<UserPojo> userPojo = taskService.getUserList();
        return new EntityResponse(HttpStatus.OK.value(), " success", userPojo);
    }


    @RequestMapping(value = "/getDeleteUser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteUser(@RequestParam(value = "useraccountId") Long useraccountId) {
        taskService.getDeleteUser(useraccountId);
        return ResponseEntity.status(200).body(null);
    }


    @RequestMapping(value = "/savetask", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity savetask(@RequestBody TaskMasterPojo taskMasterPojo) throws Exception {
        taskMasterPojo = taskService.savetask(taskMasterPojo);
        return ResponseEntity.status(200).body(taskMasterPojo);
    }




    @RequestMapping(value = "/getDeleteTask", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteTask(@RequestParam(value = "taskId") Long taskId) {
        taskService.getDeleteTask(taskId);
        return ResponseEntity.status(200).body(null);
    }




    @RequestMapping(value = "/getPaginatedUsersList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedUsersList(@RequestParam(value = "searchText", required = false) String searchText,
                                                @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(taskService.getPaginatedUsersList(basePojo, searchText));
    }



}
