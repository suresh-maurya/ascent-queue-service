package com.ascent.message.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class AscentQueue {

    @Id
    @Column(length = 64)
    private String id = UUID.randomUUID().toString();

    @Column(unique = true)
    private String queueName;

    @Setter
    private Integer capacity;

    @Setter
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name="ascent_queue_message_mapping", joinColumns = @JoinColumn(name = "queue_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Deque<Message> messages;

    private LocalDateTime created;

    public AscentQueue (String queueName, Integer capacity) {
        this.queueName = queueName;
        this.capacity = capacity;
        this.created = LocalDateTime.now();
        this.messages = new LinkedList<>();
    }
}