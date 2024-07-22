package com.example.mojal2ndproject2.category;

import com.example.mojal2ndproject2.category.dto.response.GetCategoriesRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/get/categories")
    public List<GetCategoriesRes> getCategories(){
        return categoryService.getCategories();
    }
}
