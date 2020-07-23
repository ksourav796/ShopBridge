package com.hyva.Shop.respositories;

import com.hyva.Shop.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface BsUserRepository extends CrudRepository<User, Long> {

    User findByUserNameAndPasswordUserAndStatus(String userName, String Password, String Status);

    User findByEmail(String Email);

    User findByUserName(String name);
}
