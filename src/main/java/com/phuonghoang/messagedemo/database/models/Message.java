package com.phuonghoang.messagedemo.database.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Message {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String content;
    private boolean isPalindrome;
    private Instant createdAt;
    private Instant updatedAt;

    public Message(String id, String content, boolean isPalindrome, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.content = content;
        this.isPalindrome = isPalindrome;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Message() {

    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
