package com.example.mojal2ndproject2.userhavecategory;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.member.model.dto.request.MemberAddCategoryReq;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import com.example.mojal2ndproject2.userhavecategory.model.dto.response.GetUserCategoryRes;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHaveCategoryService {
    private final UserHaveCategoryRepository userHaveCategoryRepository;

    public List<GetUserCategoryRes> getMyCategories(Member member) throws BaseException {
        List<UserHaveCategory> result = userHaveCategoryRepository.findAllByMemberWithCategory(member);
        if(result.size()<=0){
            throw new BaseException(BaseResponseStatus.CHECK_CATEGORY_MORE_THAN_ONE);
        }
        List<GetUserCategoryRes> response = new ArrayList<>();
        for (UserHaveCategory userHaveCategory : result){
            response.add(GetUserCategoryRes.builder()
                    .idx(userHaveCategory.getIdx()).name(userHaveCategory.getCategory().getName()).build());
        }
        return response;
    }

    public List<Long> addCategory(Member member, MemberAddCategoryReq request) {
        List<UserHaveCategory> categories = new ArrayList<>();

        for (Long categoryIdx : request.getCategories()) {
            Category category = Category.builder().idx(categoryIdx).build();
            UserHaveCategory userHaveCategory = UserHaveCategory.builder()
                    .member(member)
                    .category(category)
                    .build();

            userHaveCategoryRepository.save(userHaveCategory); //Todo byul : 세이브가 잘 안된 경우도 예외처리?
        }
        return request.getCategories();
    }
}
