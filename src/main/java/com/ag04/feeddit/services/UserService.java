package com.ag04.feeddit.services;


import com.ag04.feeddit.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByUsername(String username);
    List<User> listAll();
    User getById(Long id);
    User saveOrUpdate(User user);
    void delete(Long id);
    Object find(Pageable pageable);
}