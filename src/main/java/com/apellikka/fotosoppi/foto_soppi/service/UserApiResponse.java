package com.apellikka.fotosoppi.foto_soppi.service;

public class UserApiResponse {
   private String username;
   private String message;
   private int statusCode; 

   public UserApiResponse(String username, String message, int statusCode) {
       this.username = username;
       this.message = message;
       this.statusCode = statusCode;
   }

    public String getUsername() {
         return username;
    }

    public String getMessage() {
         return message;
    }

    public int getStatusCode() {
         return statusCode;
    }

}
