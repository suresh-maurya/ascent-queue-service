package com.ascent.message.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author suresh
 * @since 18/12/19
 */

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @Column(length = 64)
    private String id = UUID.randomUUID().toString();

    private LocalDateTime created;

    @Setter
    private MessageStatus messageStatus;

    @Setter
    private String content;

    public Message (String content) {
        this.content = content;
        this.created = LocalDateTime.now();
        messageStatus = MessageStatus.ADDED;
    }

}