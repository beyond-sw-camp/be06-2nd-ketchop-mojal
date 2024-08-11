package com.example.mojal2ndproject2.userhavecategory;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.member.model.dto.request.MemberAddCategoryReq;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import com.example.mojal2ndproject2.userhavecategory.model.dto.response.GetUserCategoryRes;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserHaveCategoryController {
    private final UserHaveCategoryService userHaveCategoryService;

    @Operation(summary = "내 재능 카테고리 조회", description = "내가 선택한 재능 카테고리를 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/my/category")
    public BaseResponse<List<GetUserCategoryRes>> getMyCategory(
            @AuthenticationPrincipal CustomUserDetails customUserDetails)
            throws BaseException {
        Member member = customUserDetails.getMember();
        List<GetUserCategoryRes> result = userHaveCategoryService.getMyCategories(member);
        return new BaseResponse<>(result);
    }

    @Operation( summary = "내 재능 카테고리 추가",
            description = "회원가입 후 첫 로그인 시, 본인의 재능을 5개 이하로 추가해야 합니다. " +
                    "재능으로 내세우고 싶은 카테고리를 바꾸고 싶다면 언제든지 수정도 가능합니다. ")
    @RequestMapping(method = RequestMethod.POST, value = "/add/category")
    public BaseResponse<List<Long>> addCategory(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                @RequestBody MemberAddCategoryReq request){
        Member member = customUserDetails.getMember();
        List<Long> ret = userHaveCategoryService.addCategory(member, request);
        BaseResponse<List<Long>> result = new BaseResponse<>(ret);
        return result;
    }
}
