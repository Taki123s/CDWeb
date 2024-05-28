package com.animeweb.repository;

import com.animeweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String userName);

    @Query( "SELECT a from User a where a.email= :email and a.userType = 2 and  a.status  = true" )
    User findByEmailGoogle(String email);

    @Query( "SELECT a from User a where a.email = :email and a.userType = 3 and  a.status  = true" )
    User findByEmailFacebook(String email);

}