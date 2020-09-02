package com.hyva.Task.respositories;

import com.hyva.Task.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface taskUserRepository extends JpaRepository<User, Long> {
        List<User> findByUserNameAndUseraccountIdNotIn(String username, Long id);
        List<User> findByUserName(String username);
        List<User> findAllByUserNameContaining(String searchText, Pageable pageable);
        List<User> findAllByUserNameContaining(String searchText);
        User findFirstBy(Sort sort);
        User findFirstByUserNameContaining(String searchText, Sort sort);
        List<User> findAllBy(Pageable pageable);
}

