package com.pm.noidea.identityservice;

import com.pm.noidea.identityservice.configuration.ActionTokenProperties;
import com.pm.noidea.identityservice.repository.ActionTokenRepository;
import com.pm.noidea.identityservice.service.UserTokenService;
import com.pm.noidea.identityservice.util.TimeFormatter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTokenServiceTest {

    @Mock private ActionTokenRepository actionTokenRepository;
    @Mock private ActionTokenProperties actionTokenProperties;
    @Mock private TimeFormatter timeFormatter;

    @InjectMocks
    private UserTokenService userTokenService;

    //TODO
}
