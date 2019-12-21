package com.ascent.message.queue.service;

import com.ascent.message.exception.ErrorCode;
import com.ascent.message.exception.ErrorMessage;
import com.ascent.message.exception.QueueException;
import com.ascent.message.queue.AscentQueue;
import com.ascent.message.queue.Message;
import com.ascent.message.queue.repository.AscentQueueRepository;
import com.ascent.message.util.QueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AscentQueueServiceImpl implements AscentQueueService {

    @Autowired
    AscentQueueRepository ascentQueueRepository;

    @Override
    public AscentQueue createQueue(String queueName, Integer size) throws QueueException {

        if(QueueUtil.checkNullAndEmpty(queueName)) {
            throw new QueueException(ErrorCode.QUEUE_NAME_EMPTY, ErrorMessage.QUEUE_NAME_EMPTY);
        }
        if (ascentQueueRepository.findByQueueName(queueName) != null) {
            throw new QueueException(ErrorCode.QUEUE_ALREADY_EXISTS, ErrorMessage.QUEUE_ALREADY_EXISTS);
        }
        AscentQueue ascentQueue = new AscentQueue(queueName, size);
        ascentQueue = ascentQueueRepository.save(ascentQueue);
        return ascentQueue;
    }

    @Override
    public AscentQueue updateQueueSize(String queueName, Integer size) throws QueueException {

        AscentQueue ascentQueue = getQueue(queueName);
        ascentQueue.setCapacity(size);
        ascentQueue = ascentQueueRepository.save(ascentQueue);
        return ascentQueue;
    }

    @Override
    public boolean deleteQueue(String queueName) throws QueueException {

        AscentQueue ascentQueue = getQueue(queueName);
        ascentQueueRepository.delete(ascentQueue);
        return true;
    }

    @Override
    public AscentQueue getQueue(String queueName) throws QueueException {

        if(QueueUtil.checkNullAndEmpty(queueName))
            throw new QueueException(ErrorCode.QUEUE_NAME_EMPTY, ErrorMessage.QUEUE_NAME_EMPTY);

        return ascentQueueRepository.findByQueueName(queueName);
    }

    @Override
    public AscentQueue enQueue(String queueName, String message) throws QueueException {

        if(QueueUtil.checkNullAndEmpty(message))
            throw new QueueException(ErrorCode.MESSAGE_EMPTY, ErrorMessage.MESSAGE_EMPTY);

        AscentQueue ascentQueue = getQueue(queueName);

        Message queueMessage = new Message(message);
        ascentQueue.getMessages().add(queueMessage);
        ascentQueue = ascentQueueRepository.save(ascentQueue);
        return ascentQueue;
    }

    @Override
    public Message deQueue(String queueName) throws QueueException {

        AscentQueue ascentQueue = getQueue(queueName);
        Message message = ascentQueue.getMessages().stream().findFirst().get();
        ascentQueue.getMessages().remove(message);
        ascentQueueRepository.save(ascentQueue);
        return message;
    }

    @Override
    public boolean purge(String queueName) throws QueueException {

        AscentQueue ascentQueue = getQueue(queueName);
        ascentQueue.getMessages().clear();
        ascentQueueRepository.save(ascentQueue);
        return true;
    }

    @Override
    public Message peek(String queueName) throws QueueException {

        AscentQueue ascentQueue = getQueue(queueName);
        Message message = ascentQueue.getMessages().stream().findFirst().get();
        ascentQueueRepository.save(ascentQueue);
        return message;
    }
}