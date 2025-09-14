package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.model.ActionToken;
import com.pm.noidea.identityservice.model.AuthUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserTokenService {



    private String generateCode(){
        RandomStringUtils generator = RandomStringUtils.insecure();
        return generator.next(6, 'A', 'Z' + 1, false, false);
    }
}
