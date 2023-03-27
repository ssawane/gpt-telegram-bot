package com.mara.tbot.chatgptbot.repositories;

import com.mara.tbot.chatgptbot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTgUserId(Long tgUserId);
}
