package com.ascent.message.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author suresh
 * @since 18/12/19
 */
@Data
@NoArgsConstructor
public class QueueException extends Exception{

    private Integer errorCode;

    public QueueException(Integer errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}