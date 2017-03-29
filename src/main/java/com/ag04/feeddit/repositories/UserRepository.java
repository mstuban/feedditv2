package com.ag04.feeddit.repositories;

import com.ag04.feeddit.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String username);
}