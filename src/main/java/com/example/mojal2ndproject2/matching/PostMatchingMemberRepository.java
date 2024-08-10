package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostMatchingMemberRepository extends JpaRepository<PostMatchingMember, Long> {

    List<PostMatchingMember> findAllByMemberAndSharePost(Member member, SharePost sharePost);

    Optional<PostMatchingMember> findByMemberAndSharePost(Member member, SharePost sharePost); //나눔글에 참여하기
    Optional<PostMatchingMember> findByMemberAndExchangePost(Member member, ExchangePost exchangePost);
    @Query("SELECT pmm FROM PostMatchingMember pmm JOIN FETCH pmm.sharePost JOIN FETCH pmm.sharePost.category WHERE pmm.member = :member")
    List<PostMatchingMember> findAllByMemberWithSharePostAndCategory(Member member); //내가 참여한 나눔글 전체 조회

    @Query("SELECT pmm FROM PostMatchingMember pmm JOIN FETCH pmm.exchangePost JOIN FETCH pmm.exchangePost.giveCategory JOIN FETCH pmm.exchangePost.takeCategory WHERE pmm.member = :member")
    List<PostMatchingMember> findAllByMemberWithExchangePostAndGiveCategoryAndTakeCategory(Member member);//내가 참여한 교환글 전체 조회
    @Query("SELECT pmm FROM PostMatchingMember pmm JOIN FETCH pmm.exchangePost JOIN FETCH pmm.exchangePost.giveCategory JOIN FETCH pmm.exchangePost.takeCategory WHERE pmm.member = :member")
    Slice<PostMatchingMember> findAllByMemberWithExchangePostAndGiveCategoryAndTakeCategory(Member member, Pageable pageable);//내가 참여한 교환글 전체 조회 - 페이징 처리

    Optional<PostMatchingMember> findByExchangePost(ExchangePost exchangePost);
}
