package com.animeweb.repository;

import com.animeweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT a from User a where a.userName=:userName and a.status = true and a.authenticated = true and a.userType=1" )
    Optional<User> findByUserName(String userName);
    @Query("select count(a) > 0 from User a where a.userName=:userName and a.status=true and  a.authenticated = true")
    Boolean existsByUserName(String userName);
    @Query( "SELECT a from User a where a.email= :email and a.userType = 2 and  a.status  = true " )
    User findByEmailGoogle(String email);

    @Query( "SELECT a from User a where a.email = :email and a.userType = 3 and  a.status  = true " )
    User findByEmailFacebook(String email);
    @Query("select count(a) > 0 from User a where a.email = :email and a.status=true and  a.authenticated = true")
    Boolean existByEmail(String email);
    @Modifying
    @Transactional
    @Query("update User a set a.authenticated = true where a.userName = :userName and a.email = :email and a.authCode = :verifyCode and a.status=true and a.authenticated = false and a.expiredAt > CURRENT_TIMESTAMP")
    Integer updateUserVerificationStatus(String userName, String email, Integer verifyCode);
}


