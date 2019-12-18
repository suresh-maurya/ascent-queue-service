package com.ascent.message.queue.repository;

import com.ascent.message.queue.AscentQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AscentQueueRepository extends JpaRepository<AscentQueue, Long> {

    AscentQueue findByName(String queueName);
}