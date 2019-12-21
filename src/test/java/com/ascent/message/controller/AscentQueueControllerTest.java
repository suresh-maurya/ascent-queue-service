package com.ascent.message.controller;

import com.ascent.message.data.DefaultData;
import com.ascent.message.queue.AscentQueue;
import com.ascent.message.queue.repository.AscentQueueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AscentQueueControllerTest {

    @Autowired
    private AscentQueueRepository ascentQueueRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private static final String QUEUE_NAME = "Ascent HR Message Queue";
    private static final int QUEUE_SIZE = 5;

    @After
    public void tearDown() {
        ascentQueueRepository.deleteAll();
    }

    @Test
    public void testCreateAscentQueue() throws Exception {

        final Map<String, String> map = new HashMap<>();
        map.put("queueName", QUEUE_NAME);
        map.put("size", "5");
        String requestContent = this.objectMapper.writeValueAsString(map);
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .content(requestContent).contentType("application/json")
                .when().port(this.port).post("/queues")
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testUpdateAscentQueue() throws Exception {

        AscentQueue ascentQueue = defaultQueue();
        final Map<String, String> map = new HashMap<>();
        map.put("queueName", ascentQueue.getQueueName());
        map.put("size", "10");
        String requestContent = this.objectMapper.writeValueAsString(map);
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .content(requestContent).contentType("application/json")
                .when().port(this.port).put("/queues")
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testGetAscentQueue() throws Exception {

        AscentQueue ascentQueue = defaultQueue();
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .contentType("application/json")
                .when().port(this.port).get("/queues?queueName="+ascentQueue.getQueueName())
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testDeleteAscentQueue() throws Exception {

        AscentQueue ascentQueue = defaultQueue();
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .contentType("application/json")
                .when().port(this.port).delete("/queues?queueName="+ascentQueue.getQueueName())
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testEnqueueAscentQueue() throws Exception {

        AscentQueue ascentQueue = defaultQueue();
        final Map<String, String> map = new HashMap<>();
        map.put("queueName", ascentQueue.getQueueName());
        map.put("message", "Update queue");
        String requestContent = this.objectMapper.writeValueAsString(map);
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .content(requestContent).contentType("application/json")
                .when().port(this.port).put("/queues/enqueue")
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testDequeueAscentQueue() throws Exception {

        AscentQueue ascentQueue = defaultQueue();
        final Map<String, String> map = new HashMap<>();
        map.put("queueName", ascentQueue.getQueueName());
        String requestContent = this.objectMapper.writeValueAsString(map);
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .content(requestContent).contentType("application/json")
                .when().port(this.port).put("/queues/dequeue")
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testPurgeAscentQueue() throws Exception {

        AscentQueue ascentQueue = defaultQueue();
        final Map<String, String> map = new HashMap<>();
        map.put("queueName", ascentQueue.getQueueName());
        String requestContent = this.objectMapper.writeValueAsString(map);
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .content(requestContent).contentType("application/json")
                .when().port(this.port).put("/queues/purge")
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    @Test
    public void testPeekAscentQueue() {

        AscentQueue ascentQueue = defaultQueue();
        RestAssured.given()
                .accept("application/json")
                .log().all()
                .contentType("application/json")
                .when().port(this.port).get("/queues/peek?queueName="+ascentQueue.getQueueName())
                .then()
                .assertThat().statusCode(200)
                .log().body();
    }

    private AscentQueue defaultQueue () {
        AscentQueue ascentQueue = DefaultData.createQueue(QUEUE_NAME, QUEUE_SIZE);
        return ascentQueueRepository.save(ascentQueue);
    }
}