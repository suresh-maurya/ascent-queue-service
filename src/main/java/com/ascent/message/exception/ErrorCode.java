package com.ascent.message.exception;

/**
 * @author suresh
 * @since 18/12/19
 */
public interface ErrorCode {

    Integer QUEUE_NAME_EMPTY = 1000;
    Integer MESSAGE_EMPTY = QUEUE_NAME_EMPTY+1;
    Integer QUEUE_DOES_NOT_EXIST = QUEUE_NAME_EMPTY+2;
    Integer ID_NOT_PROVIDED = QUEUE_NAME_EMPTY+3;
    Integer NOT_PURGED = QUEUE_NAME_EMPTY+4;
    Integer NOT_DELETED = QUEUE_NAME_EMPTY+5;
    Integer QUEUE_ALREADY_EXISTS = QUEUE_NAME_EMPTY+6;
}
