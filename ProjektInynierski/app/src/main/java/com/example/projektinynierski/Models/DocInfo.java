package com.example.projektinynierski.Models;

public class DocInfo {

    private String token;
    private Long id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocInfo(String token, Long id) {
        this.token = token;
        this.id = id;
    }

    public DocInfo() {
    }

    @Override
    public String toString() {
        return "DocInfo{" +
                "token='" + token + '\'' +
                ", id=" + id +
                '}';
    }
}
