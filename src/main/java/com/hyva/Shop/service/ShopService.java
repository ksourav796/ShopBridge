package com.hyva.Shop.service;

import com.hyva.Shop.entities.*;

import com.hyva.Shop.pojo.*;
import com.hyva.Shop.mapper.*;
import com.hyva.Shop.respositories.*;
import com.hyva.Shop.util.FileSystemOperations;
import com.hyva.Shop.util.ObjectMapperUtils;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.io.*;;
import java.util.*;
import java.util.List;


@Component
public class ShopService {

    @Autowired
    BsUserRepository bsUserRepository;

    @Autowired
    HotelUserRepository hotelUserRepository;
    @Autowired
    ItemRepository itemRepository;


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

    public User saveUserDetails(UserPojo userPojo) {
        User user = null;
        user = bsUserRepository.findByEmail(userPojo.getEmail());
        if (user != null) {
            user = null;
        } else {
            user = UserMapper.mapPojoToEntity(userPojo);
            bsUserRepository.save(user);
        }
        return user;
    }


    public ItemPojo saveItem(ItemPojo itemPojo) {
        List<Item>list=new ArrayList<>();
        byte byteArray[];
        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + "SP" + itemPojo.getItemName() + ".png";
        if (!StringUtils.isEmpty(itemPojo.getImage())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(itemPojo.getImage().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                itemPojo.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(itemPojo.getId()!=0) {
            list = itemRepository.findByItemNameAndPriceAndIdNotIn(itemPojo.getItemName(), itemPojo.getPrice(), itemPojo.getId());
        }
        else{
            list=itemRepository.findByItemNameAndPrice(itemPojo.getItemName(),itemPojo.getPrice());
        }
        if(list.size()==0){
            Item item = null;
            item = ListMapper.mapPojoToEntity(itemPojo);
            itemRepository.save(item);
            return itemPojo;
        }
        else {
            return null;
        }
    }



    public List<ItemPojo> getItem() {
        List<Item> items = new ArrayList<>();
        items = (List<Item>) itemRepository.findAll();
        List<ItemPojo> itemPojos = ObjectMapperUtils.mapAll(items, ItemPojo.class);
        return itemPojos;
    }

    public void getDeleteItem(Long id){
        itemRepository.delete(id);
    }



    public UserPojo getuserDetails(UserPojo userPojo){
        List<User>list=new ArrayList<>();
        if(userPojo.getUseraccountId()!=null){
            list=hotelUserRepository.findByUserNameAndUseraccountIdNotIn(userPojo.getUserName(), userPojo.getUseraccountId());
        }
        else {
            list=hotelUserRepository.findByUserName(userPojo.getUserName());
        }
        if(list.size()==0){
            User user = UserMapper.mapPojoToEntity(userPojo);
            hotelUserRepository.save(user);
            return userPojo;
        }else{
            return null;
        }

    }

    public List<UserPojo> getUserList() {
        List<User> user = (List<User>) hotelUserRepository.findAll();
        List<UserPojo> usersPojo1 = ObjectMapperUtils.mapAll(user, UserPojo.class);
        return usersPojo1;
    }


    public void getDeleteUser(Long useraccountId) {
        hotelUserRepository.delete(useraccountId);
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
                list1 = hotelUserRepository.findAllByUserNameContaining(searchText);
            }else {
                list1=hotelUserRepository.findAll();
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
            user=hotelUserRepository.findFirstByUserNameContaining(searchText,sort);
            list = hotelUserRepository.findAllByUserNameContaining(searchText,pageable);
        } else {
            user=hotelUserRepository.findFirstBy(sort);
            list = hotelUserRepository.findAllBy(pageable);
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


    public static BaseFont getcustomfont() {
        String relativeWebPath = "fonts/arial.ttf";
        return FontFactory.getFont("arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK).getBaseFont();
    }



    public BasePojo getPaginatedItemList(BasePojo basePojo,String searchText) {
        List<Item> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        if(basePojo.isLastPage()==true){
            List<Item> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = itemRepository.findAllByItemNameContaining(searchText);
            }else {
                list1=itemRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        }
        Item item=new Item();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            item=itemRepository.findFirstByItemNameAndPriceContaining(searchText,sort);
            list = itemRepository.findAllByItemNameContaining(searchText,pageable);
        } else {
            item=itemRepository.findFirstBy(sort);
            list = itemRepository.findAllBy(pageable);
        }
        if(list.contains(item)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<ItemPojo> typesList =ListMapper.mapEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }





    public List<UserPojo> getUserPojoList(String searchText){
        List<User> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = hotelUserRepository.findByUserName(searchText);
        } else {
            list = hotelUserRepository.findAll();
        }
        List<UserPojo> userPojoList =UserMapper.mapEntityToPojo(list);
        return userPojoList;
    }





}





