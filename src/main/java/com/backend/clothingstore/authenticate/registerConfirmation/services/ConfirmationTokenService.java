package com.backend.clothingstore.authenticate.registerConfirmation.services;


import com.backend.clothingstore.authenticate.registerConfirmation.repository.ConfirmationTokenRepository;
import com.backend.clothingstore.model.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class ConfirmationTokenService {


    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;



    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> findByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void deleteToken(int id) {
        confirmationTokenRepository.deleteById(id);
    }
}
