package com.apellikka.fotosoppi.foto_soppi.service;

public class UserApiResponse {
   private String username;
   private String message;
   private int statusCode; 

   public UserApiResponse(String username, String message) {
       this.username = username;
       this.message = message;
   }

    public String getUsername() {
         return username;
    }

    public String getMessage() {
         return message;
    }

    public int getStatusCode() {
         if(message.equals("User created successfully!")) {
             statusCode = 201; 
         } else if(message.equals("User already exists!")) {
             statusCode = 409;
         } else {
             statusCode = 400;
         }
         return statusCode;
    }
}
