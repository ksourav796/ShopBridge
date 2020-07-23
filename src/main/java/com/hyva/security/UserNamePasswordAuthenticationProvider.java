package com.hyva.security;

import com.hyva.Shop.entities.User;
import com.hyva.Shop.respositories.BsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    BsUserRepository userRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

      User userEntity= userRepository.findByUserName(name);

        if(userEntity!=null){
            if(bCryptPasswordEncoder.matches(password,userEntity.getPasswordUser())){

                return new UsernamePasswordAuthenticationToken(
                        name, password, new ArrayList<>());

            }


        }else{
        throw new
                BadCredentialsException("External system authentication failed");
    }

    return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
