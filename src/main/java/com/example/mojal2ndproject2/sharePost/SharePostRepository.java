package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SharePostRepository extends JpaRepository<SharePost, Long> {
    List<SharePost> findAllByMember(Member member);

    @Query("SELECT sp FROM SharePost sp JOIN FETCH sp.member WHERE sp.idx = :idx")
    Optional<SharePost> findByIdWithMember(Long idx);
}
