package com.pm.noidea.identityservice.repository;

import com.pm.noidea.identityservice.model.ActionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActionTokenRepository extends JpaRepository<ActionToken, UUID> {
}
