package com.pm.noidea.identityservice.repository;

import com.pm.noidea.identityservice.model.ActionToken;
import com.pm.noidea.identityservice.model.ActionType;
import com.pm.noidea.identityservice.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActionTokenRepository extends JpaRepository<ActionToken, UUID> {
    void deleteByActionTypeAndUser(ActionType actionType, AuthUser user);

    Optional<ActionToken> findByActionTypeAndUser(ActionType actionType, AuthUser user);
}
