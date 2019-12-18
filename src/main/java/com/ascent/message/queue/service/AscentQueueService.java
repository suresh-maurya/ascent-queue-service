package com.ascent.message.queue.service;

import com.ascent.message.exception.QueueException;
import com.ascent.message.queue.AscentQueue;
import com.ascent.message.queue.Message;

public interface AscentQueueService {

    /**
     *
     * @param queueName
     * @param size
     * @return
     * @throws QueueException
     */
    AscentQueue createQueue(String queueName, Integer size) throws QueueException;

    /**
     *
     * @param queueName
     * @param size
     * @return AscentQueue
     */
    AscentQueue updateQueueSize(String queueName, Integer size) throws QueueException;

    /**
     *
     * @param queueName
     * @return boolean
     */
    boolean deleteQueue(String queueName) throws QueueException;

    /**
     *
     * @param queueName
     * @return
     */
    AscentQueue getQueue(String queueName) throws QueueException;

    /**
     *
     * @param queueName
     * @param message
     * @return AscentQueue
     */
    AscentQueue enQueue(String queueName, String message) throws QueueException;

    /**
     *
     * @param queueName
     * @return
     */
    Message deQueue(String queueName) throws QueueException;

    /**
     *
     * @param queueName
     * @return Message
     */
    Message peek(String queueName) throws QueueException;

    /**
     *
     * @param queueName
     * @return
     */
    boolean purge(String queueName) throws QueueException;
}