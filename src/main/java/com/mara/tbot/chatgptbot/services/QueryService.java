package com.mara.tbot.chatgptbot.services;

import com.mara.tbot.chatgptbot.models.Query;
import com.mara.tbot.chatgptbot.models.User;
import com.mara.tbot.chatgptbot.repositories.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;

    @Transactional
    public void setUserAndSave(Query query, User user) {
        query.setUser(user);
        enrichQuery(query);
        queryRepository.save(query);
    }

    public List<Query> findByUserIdOrderByReceivedAtDesc(int userId) {
        return queryRepository.findByUserIdOrderByReceivedAtDesc(userId);
    }

    private void enrichQuery(Query query) {
        query.setReceivedAt(LocalDateTime.now());
    }
}
