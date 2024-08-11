package com.example.mojal2ndproject2.member;


import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.member.emailAuth.EmailAuthRepository;
import com.example.mojal2ndproject2.member.emailAuth.model.EmailAuth;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.member.model.dto.request.MemberAddCategoryReq;
import com.example.mojal2ndproject2.member.model.dto.request.MemberModifyReq;
import com.example.mojal2ndproject2.member.model.dto.request.MemberSignupReq;
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
    private final EmailAuthRepository emailAuthRepository;

    public BaseResponse<MemberSignupRes> signup(MemberSignupReq request) throws BaseException{
        if(memberRepository.existsByEmail(request.getEmail())){
//            return new BaseResponse<>(POST_USERS_EXISTS_EMAIL);
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        }

        EmailAuth result = emailAuthRepository.findByEmailAndUuid(request.getEmail(), request.getUuid())
                .orElseThrow(()->new BaseException(POST_USERS_UNAUTH_EMAIL));


        Member member = Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .emailAuth(true)
                .role("ROLE_USER")
                .firstLogin(true)
                .signupDate(LocalDateTime.now())
//                .profileImageUrl(imageUrl)
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

    public String modify(MemberModifyReq request) throws BaseException {
        Member member = memberRepository.findById(request.getIdx()).orElseThrow(
                ()-> new BaseException(NOT_MEMBER)
        );

        Member saved = memberRepository.save( Member.builder()
                .idx(request.getIdx())
                .nickname(request.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .signupDate(member.getSignupDate())
                .emailAuth(member.getEmailAuth())
                .kakaoIdx(member.getKakaoIdx())
                .role(member.getRole())
                .profileImageUrl(member.getProfileImageUrl())
                .firstLogin(request.getFirstLogin()).build());

        if(saved==null || request.getIdx() != member.getIdx()){
            return "회원 정보 저장 실패";
        }
        return "회원 정보 저장 성공";
    }
}
