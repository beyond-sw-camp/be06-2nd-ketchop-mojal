package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangePostRepository extends JpaRepository<ExchangePost, Long> {
    @Query("SELECT ep FROM ExchangePost ep JOIN FETCH ep.member")
    List<ExchangePost> findAllByMember(Member member);
    @Query("SELECT ep FROM ExchangePost ep JOIN FETCH ep.postMatchingMembers JOIN FETCH ep.giveCategory JOIN FETCH ep.takeCategory")
    List<ExchangePost> findAllByMemberWithMatchingMemberAndGiveCategoryAndTakeCategory(Member member);
}
