package com.example.mojal2ndproject2.userhavecategory;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UserHaveCategoryRepository extends JpaRepository<UserHaveCategory, Long> {
    Optional<UserHaveCategory> findByMemberAndCategory(Member member, Category category);

    Optional<UserHaveCategory> findByMember(Member newMember);
    List<UserHaveCategory> findAllByMember(Member member);

    @Query("SELECT uhc FROM UserHaveCategory uhc JOIN FETCH uhc.category WHERE uhc.member= :member")
    List<UserHaveCategory> findAllByMemberWithCategory(Member member);
}
