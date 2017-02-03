package com.example.tacademy.recyclerviewtest.model;

/**
 * 채팅 메시지 구조 -> 디비 구조
 * 내가 쓴데로 틀에 맞춰서 들어간다. key and Value
 */

public class ChatMessage {

    String username;
    String message;

    // 기본 생성자를 꼭 만든다
    public ChatMessage(){

    }

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
