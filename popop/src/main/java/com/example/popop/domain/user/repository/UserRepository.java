package com.example.popop.domain.user.repository;

import com.example.popop.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u.loginId  FROM User u WHERE u.loginId = :loginId")
    Optional<String> findLoginIdByLoginId(@Param("loginId") String loginId);

    @Query("SELECT u.nickname FROM User u WHERE u.nickname = :nickname")
    Optional<String> findByNicknameByNickname(@Param("nickname")String nickname);

    @Query("SELECT u.email FROM User u WHERE u.email = :email")
    Optional<String> findByEmailByEmail(@Param("email") String email);

    Optional<User> findByLoginId(String loginId);


}
