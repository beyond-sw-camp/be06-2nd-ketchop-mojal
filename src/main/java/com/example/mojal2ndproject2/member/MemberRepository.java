package com.example.mojal2ndproject2.member;

import com.example.mojal2ndproject2.member.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmailAndMemberAuth(String email, Boolean memberAuth);
}
