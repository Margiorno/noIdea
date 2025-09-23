package com.pm.noidea.trackingservice.service;

import com.pm.noidea.trackingservice.model.User;
import com.pm.noidea.trackingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UUID userId){
        userRepository.save(new User(userId));
    }

    public boolean existsByUserId(UUID userId){
        return userRepository.existsById(userId);
    }
}
