package com.hyva.Shop.mapper;

import com.hyva.Shop.entities.Item;
import com.hyva.Shop.pojo.ItemPojo;

import java.util.ArrayList;
import java.util.List;

public class ListMapper {
    public static Item mapPojoToEntity(ItemPojo pojo){
          Item item=new Item();
        item.setId(pojo.getId());
        item.setItemName(pojo.getItemName());
        item.setDescription(pojo.getDescription());
        item.setPrice(pojo.getPrice());
        item.setImage(pojo.getImage());
        return item;
    }
    public static List<ItemPojo> mapEntityToPojo(List<Item> itemList){
        List<ItemPojo> list=new ArrayList<>();
        for(Item item:itemList) {
            ItemPojo itemPojo = new ItemPojo();
            itemPojo.setId(item.getId());
            itemPojo.setItemName(item.getItemName());
            itemPojo.setDescription(item.getDescription());
            itemPojo.setPrice(item.getPrice());
            itemPojo.setImage(item.getImage());
            list.add(itemPojo);
        }
        return list;
    }
}

