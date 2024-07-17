package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SharePostRepository extends JpaRepository<SharePost, Long> {
    List<SharePost> findAllByMember(Member member);
}
