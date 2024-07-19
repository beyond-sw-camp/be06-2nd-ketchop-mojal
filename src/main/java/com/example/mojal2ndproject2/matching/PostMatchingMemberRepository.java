package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostMatchingMemberRepository extends JpaRepository<PostMatchingMember, Long> {
    @Query("SELECT pmm FROM PostMatchingMember pmm JOIN FETCH pmm.member")
    List<PostMatchingMember> findAllByMember(Member member);

    Optional<PostMatchingMember> findByMemberAndSharePost(Member member, SharePost sharePost);
    Optional<PostMatchingMember> findByMemberAndExchangePost(Member member, ExchangePost exchangePost);
}
