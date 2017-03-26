package com.ag04.feeddit.repositories;

import com.ag04.feeddit.domain.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    @Query("select a.role from UserRole a, User b where b.userName=?1 and a.user.userId=b.userId")
    public List<String> findRolesByUsername(String username);
}