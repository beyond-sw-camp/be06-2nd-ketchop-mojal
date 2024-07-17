package com.example.mojal2ndproject2.member;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.member.model.dto.request.MemberSignupReq;
import com.example.mojal2ndproject2.member.model.dto.response.MemberSignupRes;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BaseResponse<MemberSignupRes> signup(MemberSignupReq request) {
        if(memberRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(POST_USERS_EXISTS_EMAIL);
        }

        //1. 멤버 저장
        Member member = Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .memberAuth(false)
                .role("ROLE_USER")
                .build();

        Member savedMember = memberRepository.save(member); //Todo byul : 예외처리 필요???

        //2. 카테고리 저장
//        for (Long categoryIdx : request.getCategories()) {
//            Optional<Category> result = categoryRepository.findById(categoryIdx);
//            if(result.isPresent()){
//                Category category = result.get();
//
//                UserHaveCategory userHaveCategory = UserHaveCategory.builder()
//                        .category(category)
//                        .member(savedMember)
//                        .build();
//
//                userHaveCategoryRepository.save(userHaveCategory); //
//            }else{
//                return null;
//            }
//
//        }

        //3. 응답객체
        MemberSignupRes memberSignupRes = MemberSignupRes.builder()
                .idx(savedMember.getIdx())
                .email(savedMember.getEmail())
                .nickName(savedMember.getNickname())
                .build();

        return new BaseResponse<>(memberSignupRes);
    }
}
