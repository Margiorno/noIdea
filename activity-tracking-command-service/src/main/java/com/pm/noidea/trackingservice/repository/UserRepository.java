package com.pm.noidea.trackingservice.repository;

import com.pm.noidea.trackingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {}
