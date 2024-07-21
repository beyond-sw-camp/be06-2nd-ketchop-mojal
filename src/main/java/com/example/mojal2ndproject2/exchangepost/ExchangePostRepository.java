package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExchangePostRepository extends JpaRepository<ExchangePost, Long> {
    List<ExchangePost> findAllByMember(Member member);
    @Query("SELECT ep FROM ExchangePost ep JOIN FETCH ep.member JOIN FETCH ep.giveCategory JOIN FETCH ep.takeCategory WHERE ep.member= :member")
    List<ExchangePost> findAllByMemberWithMemberAndCategory(Member member);
    @Query("SELECT ep FROM ExchangePost ep JOIN FETCH ep.postMatchingMembers JOIN FETCH ep.giveCategory JOIN FETCH ep.takeCategory")
    List<ExchangePost> findAllByMemberWithMatchingMemberAndGiveCategoryAndTakeCategory(Member member);
    //SELECT o FROM One o JOIN FETCH o.manyList
    @Query("SELECT ep FROM ExchangePost ep JOIN FETCH ep.member JOIN FETCH ep.giveCategory JOIN FETCH ep.takeCategory")
//    List<ExchangePost> findAllPostWithMemberAndGiveCategoryAndTakeCategory();
    Slice<ExchangePost> findAllPostWithMemberAndGiveCategoryAndTakeCategory(Pageable pageable);
    @Query("SELECT ep FROM ExchangePost ep JOIN FETCH ep.member JOIN FETCH ep.giveCategory JOIN FETCH ep.takeCategory WHERE ep.idx = :postIdx")
    Optional<ExchangePost> findPostByIdxWithMemberAndGiveCategoryAAndTakeCategory(Long postIdx);
}
