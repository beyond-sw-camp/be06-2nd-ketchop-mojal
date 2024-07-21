package com.example.mojal2ndproject2.userhavecategory;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHaveCategoryRepository extends JpaRepository<UserHaveCategory, Long> {
    Optional<UserHaveCategory> findByMemberAndCategory(Member member, Category category);

    Optional<Object> findByMember(Member newMember);
}
