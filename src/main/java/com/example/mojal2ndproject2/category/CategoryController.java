package com.example.mojal2ndproject2.category;

import com.example.mojal2ndproject2.category.model.dto.response.GetCategoriesRes;
import com.example.mojal2ndproject2.common.BaseResponse;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation( summary = "저장된 카테고리 조회",
            description = "어떤 카테고리가 저장되어 있는지 리스트로 조회합니다.")
    @RequestMapping(method = RequestMethod.GET, value = "/get/categories")
    public List<GetCategoriesRes> getCategories(){
        return categoryService.getCategories();
    }
}
