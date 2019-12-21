package com.ascent.message.service;

import com.ascent.message.data.DefaultData;
import com.ascent.message.exception.QueueException;
import com.ascent.message.queue.AscentQueue;
import com.ascent.message.queue.Message;
import com.ascent.message.queue.repository.AscentQueueRepository;
import com.ascent.message.queue.service.AscentQueueService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AscentQueueServiceTest {

    @Autowired
    AscentQueueRepository ascentQueueRepository;

    @Autowired
    AscentQueueService ascentQueueService;

    @After
    public void tearDown() {
        ascentQueueRepository.deleteAll();
    }

    private static final String QUEUE_NAME = "Ascent HR Message Queue";
    private static final int QUEUE_SIZE = 5;

    @Test
    public void testCreateQueue() throws QueueException {

        AscentQueue ascentQueue = ascentQueueService.createQueue(QUEUE_NAME, QUEUE_SIZE);
        Assert.assertNotNull(ascentQueue.getId());
    }

    @Test
    public void testUpdateQueueSize() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        ascentQueue = ascentQueueService.updateQueueSize(ascentQueue.getQueueName(), 10);
        Assert.assertTrue(ascentQueue.getCapacity() == 10);
    }

    @Test
    public void testDeleteQueue() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        boolean flag = ascentQueueService.deleteQueue(ascentQueue.getQueueName());
        Assert.assertTrue(flag);
    }

    @Test
    public void testGetQueue() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        ascentQueue = ascentQueueService.getQueue(ascentQueue.getQueueName());
        Assert.assertTrue(ascentQueue.getQueueName().equals(QUEUE_NAME));
    }

    @Test
    public void testEnQueue() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        ascentQueue = ascentQueueService.enQueue(ascentQueue.getQueueName(), "Test message");
        Assert.assertTrue(ascentQueue.getMessages().size() == 2);
    }

    @Test
    public void testDeQueue() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        Message message = ascentQueueService.deQueue(ascentQueue.getQueueName());
        Assert.assertTrue(message.getContent().equals("Default message"));
    }

    @Test
    public void testPurge() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        boolean flag = ascentQueueService.purge(ascentQueue.getQueueName());
        Assert.assertTrue(flag);
    }

    @Test
    public void testPeek() throws QueueException {

        AscentQueue ascentQueue = defaultQueue();
        Message message = ascentQueueService.peek(ascentQueue.getQueueName());
        Assert.assertTrue(message.getContent().equals("Default message"));
    }

    private AscentQueue defaultQueue () {
        AscentQueue ascentQueue = DefaultData.createQueue(QUEUE_NAME, QUEUE_SIZE);
        return ascentQueueRepository.save(ascentQueue);
    }
}