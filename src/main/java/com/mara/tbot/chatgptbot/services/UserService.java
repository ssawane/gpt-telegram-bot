package com.mara.tbot.chatgptbot.services;

import com.mara.tbot.chatgptbot.models.User;
import com.mara.tbot.chatgptbot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void save(User user) {
        enrichUser(user);
        userRepository.save(user);
    }

    public Optional<User> findByTgUserId(Long tgUserId) {
        return userRepository.findByTgUserId(tgUserId);
    }

    @Transactional
    public void setActiveSession(User user) {
        user.setActiveSession(true);
        userRepository.save(user);
    }

    @Transactional
    public void setInactiveSession(User user) {
        user.setActiveSession(false);
        userRepository.save(user);
    }

    @Transactional
    public void subtractUsedTokens(User user, int amount) {
        user.setTokensLeft(user.getTokensLeft() - amount);
        userRepository.save(user);
    }

    private void enrichUser(User user) {
        user.setActiveSession(false);
        user.setTokensLeft(5000);
        user.setRegisteredAt(LocalDateTime.now());
    }
}
