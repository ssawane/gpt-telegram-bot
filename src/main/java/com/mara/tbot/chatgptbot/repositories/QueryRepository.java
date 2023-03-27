package com.mara.tbot.chatgptbot.repositories;

import com.mara.tbot.chatgptbot.models.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query, Integer> {
    List<Query> findByUserIdOrderByReceivedAtDesc(int id);
}
