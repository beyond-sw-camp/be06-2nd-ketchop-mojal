package com.example.mojal2ndproject2.member;


import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.member.model.dto.request.MemberAddCategoryReq;
import com.example.mojal2ndproject2.member.model.dto.request.MemberSignupReq;
import com.example.mojal2ndproject2.member.model.dto.response.MemberAddCategoryRes;
import com.example.mojal2ndproject2.member.model.dto.response.MemberSignupRes;
import com.example.mojal2ndproject2.member.model.dto.response.MyInfoReadRes;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BaseResponse<MemberSignupRes> signup(MemberSignupReq request) throws BaseException{
        if(memberRepository.existsByEmail(request.getEmail())){
//            return new BaseResponse<>(POST_USERS_EXISTS_EMAIL);
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        }

        Member member = Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .emailAuth(false)
                .role("ROLE_USER")
                .signupDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);

        //3. 응답객체
        MemberSignupRes memberSignupRes = MemberSignupRes.builder()
                .idx(savedMember.getIdx())
                .email(savedMember.getEmail())
                .nickName(savedMember.getNickname())
                .build();

        return new BaseResponse<>(memberSignupRes);
    }

    public List<Long> addCategory(Member user, MemberAddCategoryReq request) {//Todo byul: 테스트 해보기
        List<UserHaveCategory> categories = new ArrayList<>();

        for (Long categoryIdx : request.getCategories()) {
            Category category = Category.builder().idx(categoryIdx).build();
            UserHaveCategory userHaveCategory = UserHaveCategory.builder()
                    .member(user)
                    .category(category)
                    .build();

            userHaveCategoryRepository.save(userHaveCategory); //Todo byul : 세이브가 잘 안된 경우도 예외처리?
        }
        return request.getCategories();
    }

    public MyInfoReadRes myInfoRead(Member user) throws BaseException{

        Optional<Member> InMember = memberRepository.findById(user.getIdx());

        if(InMember.isPresent()){
            MyInfoReadRes myInfoReadRes = MyInfoReadRes.builder()
                    .idx(InMember.get().getIdx())
                    .email(InMember.get().getEmail())
                    .nickName(InMember.get().getNickname())
                    .build();

            return myInfoReadRes;
        }
        //내 정보 조회에 실패할 때 (디비에 없다던지 말이 안되긴 하는데)
        throw new BaseException(MYINFO_NOT_FOUND);
    }
}
