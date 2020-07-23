package com.hyva.Shop.Shopendpoints;

import com.hyva.Shop.entities.*;
import com.hyva.Shop.pojo.*;
import com.hyva.Shop.service.ShopService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class ShopController {
    @Autowired
    ShopService hotelService;


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse login(@RequestBody User credentials) throws Exception {
//            User bshimData = bshimUserService.get(credentials) ;
        String accessToken = "12345";
        if (StringUtils.isBlank(credentials.getEmail()) || StringUtils.isBlank(credentials.getUserName()) || StringUtils.isBlank(credentials.getPasswordUser())) {
            return new EntityResponse(HttpStatus.OK.value(), "Invalid User");
        }
        return new EntityResponse(HttpStatus.OK.value(), "success", credentials);
    }

    @RequestMapping(value = "/saveLoginDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveLoginDetails(@RequestBody UserPojo userPojo) {
        return hotelService.saveUserDetails(userPojo);
    }


//    @RequestMapping(value = "/getcheckinList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public EntityResponse getcheckinList(@RequestParam(value = "type") String status) {
//        List<OrdersPojo> ordersPojo = hotelService.getcheckinList(status);
//        return new EntityResponse(HttpStatus.OK.value(), " success", ordersPojo);
//    }


    @RequestMapping(value = "/userValidate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User userValidate(@RequestBody UserPojo userPojo) {
        return hotelService.userValidate(userPojo);
    }


    @RequestMapping(value = "/getuserDetails", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity getuserDetails(@RequestBody UserPojo userpojo) {
        userpojo = hotelService.getuserDetails(userpojo);
        return ResponseEntity.status(200).body(userpojo);
    }



    @RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getUserList() {
        List<UserPojo> userPojo = hotelService.getUserList();
        return new EntityResponse(HttpStatus.OK.value(), " success", userPojo);
    }


    @RequestMapping(value = "/getDeleteUser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteUser(@RequestParam(value = "useraccountId") Long useraccountId) {
        hotelService.getDeleteUser(useraccountId);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/saveItem", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveItem(@RequestBody ItemPojo itemPojo) throws Exception {
        itemPojo = hotelService.saveItem(itemPojo);
        return ResponseEntity.status(200).body(itemPojo);
    }

    @RequestMapping(value = "/getItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getItem() {
        List<ItemPojo> getItem = hotelService.getItem();
        return new EntityResponse(HttpStatus.OK.value(), " success", getItem);
    }

    @RequestMapping(value = "/getDeleteItem", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteItem(@RequestParam(value = "id") Long id) {
        hotelService.getDeleteItem(id);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/getPaginatedUsersList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedUsersList(@RequestParam(value = "searchText", required = false) String searchText,
                                                @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedUsersList(basePojo, searchText));
    }


    @RequestMapping(value = "/getPaginatedItemList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedItemList(@RequestParam(value = "searchText",required = false) String searchText,
                                                 @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedItemList(basePojo,searchText));
    }
}
