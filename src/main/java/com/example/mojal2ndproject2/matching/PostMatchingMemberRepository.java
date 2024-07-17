package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostMatchingMemberRepository extends JpaRepository<PostMatchingMember, Long> {
    List<PostMatchingMember> findAllByMember(Member member);
}
