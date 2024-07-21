package com.example.mojal2ndproject2.member.emailAuth;

import com.example.mojal2ndproject2.member.emailAuth.model.EmailAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByEmailAndUuid(String email, String uuid);
}
