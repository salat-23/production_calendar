package com.example.demo.requests;

import lombok.Data;

@Data
public class ChangeDateRequest {
    private String date;
    private String dayType;
    private String login;
    private String password;
}
