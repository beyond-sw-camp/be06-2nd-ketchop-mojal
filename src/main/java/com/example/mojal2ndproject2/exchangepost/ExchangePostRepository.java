package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangePostRepository extends JpaRepository<ExchangePost, Long> {
    List<ExchangePost> findAllByMember(Member member);
}
