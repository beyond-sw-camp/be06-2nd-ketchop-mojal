package com.example.mojal2ndproject2.category;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.member.model.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
