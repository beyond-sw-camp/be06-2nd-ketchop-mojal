package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface SharePostRepository extends JpaRepository<SharePost, Long> {
    List<SharePost> findAllByMember(Member member);

    //SELECT o FROM One o JOIN FETCH o.manyList
    @Query("SELECT sp FROM SharePost sp JOIN FETCH sp.member JOIN FETCH sp.category")
    List<SharePost> findAllPostWithMemberAndCategory();
    @Query("SELECT sp FROM SharePost sp JOIN FETCH sp.member JOIN FETCH sp.category WHERE sp.idx = :postIdx")
    Optional<SharePost> findByIdxWithMemberAndCategory(Long postIdx);
}
