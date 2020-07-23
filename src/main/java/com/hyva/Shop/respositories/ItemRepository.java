package com.hyva.Shop.respositories;

import com.hyva.Shop.entities.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemNameAndPriceAndIdNotIn(String firstname, String lastname, Long id);
    List<Item> findByItemNameAndPrice(String firstname, String lastname);
    Item findFirstByItemNameAndPriceContaining(String searchText, Sort sort);
  List<Item> findAllByItemNameContaining(String searchText, Pageable pageable);
    Item findFirstBy(Sort sort);
  List<Item> findAllBy(Pageable pageable);
  List<Item> findAllByItemNameContaining(String searchText);
}