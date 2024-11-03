package com.backend.clothingstore.email;

public interface EmailSender {
    void send(String to, String email);
    void sendPassChange(String to, String email);
}
