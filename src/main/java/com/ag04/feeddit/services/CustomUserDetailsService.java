package com.ag04.feeddit.services;

/**
 * Created by marko on 24.03.17..
 */

import com.ag04.feeddit.domain.CustomUserDetails;
import com.ag04.feeddit.domain.User;
import com.ag04.feeddit.repositories.UserRepository;
import com.ag04.feeddit.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            List<String> userRoles = userRoleRepository.findRolesByUsername(username);
            return new CustomUserDetails(user, userRoles);
        }
    }
}

