package com.example.mojal2ndproject2.category;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.category.model.dto.response.GetCategoriesRes;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.member.model.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<GetCategoriesRes> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        List<GetCategoriesRes> response = new ArrayList<>();
        for (Category category : categories) {
            response.add(GetCategoriesRes.builder()
                    .idx(category.getIdx())
                    .name(category.getName())
                    .build());
        }
        return response;
    }
}
