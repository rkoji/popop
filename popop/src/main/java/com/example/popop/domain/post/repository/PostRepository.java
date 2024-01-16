package com.example.popop.domain.post.repository;

import com.example.popop.domain.post.entity.Post;
import com.example.popop.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findByAuthor(User user);
}
