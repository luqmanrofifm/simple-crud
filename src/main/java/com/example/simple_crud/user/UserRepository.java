package com.example.simple_crud.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, java.util.UUID>, org.springframework.data.jpa.repository.JpaSpecificationExecutor<User> {
    User findFirstByEmail(String email);
}
