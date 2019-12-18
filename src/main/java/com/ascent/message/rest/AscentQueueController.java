package com.ascent.message.rest;

import com.ascent.message.exception.ErrorMessage;
import com.ascent.message.exception.QueueException;
import com.ascent.message.queue.AscentQueue;
import com.ascent.message.queue.Message;
import com.ascent.message.queue.service.AscentQueueService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author suresh
 * @since 18/12/19
 */
@RestController
public class AscentQueueController {


    @Autowired
    private AscentQueueService ascentQueueService;

    @PostMapping("/queues")
    public ResponseEntity<?> createAscentQueue(@RequestBody AscentQueueDto ascentQueueDto) throws QueueException {
        return ResponseEntity.ok(ascentQueueService.createQueue(ascentQueueDto.getQueueName(), ascentQueueDto.getSize()));
    }

    @PutMapping("/queues")
    public ResponseEntity<?> updateAscentQueue(@RequestBody AscentQueueDto ascentQueueDto) throws QueueException{
        return ResponseEntity.ok(ascentQueueService.updateQueueSize(ascentQueueDto.getQueueName(), ascentQueueDto.getSize()));
    }

    @GetMapping("/queues")
    public ResponseEntity<?> getAscentQueue(@RequestParam(value = "queueName") String queueName) throws QueueException{
        return ResponseEntity.ok(ascentQueueService.getQueue(queueName));
    }

    @DeleteMapping("/queues")
    public ResponseEntity<?> deleteMessageQueue(@RequestParam(value = "queueName") String queueName) throws QueueException {

        if(ascentQueueService.deleteQueue(queueName))
            return ResponseEntity.ok(ErrorMessage.QUEUE_DELETED);
        else
            return ResponseEntity.ok(ErrorMessage.QUEUE_NOT_DELETED);
    }

    @PutMapping(value = "/queues/enqueue")
    public ResponseEntity<?> enqueueMessage(@RequestBody AscentQueueDto ascentQueueDto) throws QueueException{
        return ResponseEntity.ok(ascentQueueService.enQueue(ascentQueueDto.getQueueName(), ascentQueueDto.getMessage()));
    }

    @PutMapping(value = "/queues/dequeue")
    public ResponseEntity<?> dequeueMessage(@RequestBody AscentQueueDto ascentQueueDto) throws QueueException{
        return ResponseEntity.ok(ascentQueueService.deQueue(ascentQueueDto.getQueueName()));
    }

    @PutMapping(value = "/queues/purge")
    public ResponseEntity<?> purgeQueue(@RequestBody AscentQueueDto ascentQueueDto) throws QueueException{

        boolean isPurged = ascentQueueService.purge(ascentQueueDto.getQueueName());
        if(isPurged)
            return ResponseEntity.ok(ErrorMessage.QUEUE_PURGED);
        else
            return ResponseEntity.ok(ErrorMessage.NOT_PURGED);
    }

    @GetMapping(value = "/queues/peek")
    public ResponseEntity<?> peekQueue(@RequestParam(value = "queueName") String queueName) throws QueueException {

        return ResponseEntity.ok(ascentQueueService.peek(queueName));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AscentQueueDto{
        private String queueName;
        private Integer size;
        private String message;
    }

}