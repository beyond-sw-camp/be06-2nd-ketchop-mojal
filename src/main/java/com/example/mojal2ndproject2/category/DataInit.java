package com.example.mojal2ndproject2.category;

import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.PostMatchingMemberRepository;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.SharePostRepository;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInit {
    private final CategoryRepository categoryRepository;
    private final SharePostRepository sharePostRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final MemberRepository memberRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final PostMatchingMemberRepository postMatchingMemberRepository;

    @PostConstruct
    public void dataInsert() {

        Set<String> categories = Set.of(
                "언어와 문화", "스포츠와 레크리에이션", "음악과 예술", "학문과 교육", "프로그래밍과 IT",
                "건강과 웰빙", "요리와 제과", "공예와 DIY", "비즈니스와 경영", "재정 및 투자",
                "사진과 비디오", "문학과 글쓰기", "여행과 탐험", "사회적 기술과 커뮤니케이션",
                "디자인과 크리에이티브", "자연과 과학", "정치와 사회", "자동차와 기술", "게임과 취미",
                "애완동물과 동물 관리", "나눔"
        );

        for (String c : categories) {
            Category category = Category.builder().name(c).build();
            categoryRepository.save(category);
        }

        //member 1,2,3,4 생성
        for(int i=1;i<=4;i++){
            if(i==3){
                Member member = Member.builder()
                        .email("member"+i+"@email.com")
                        .nickname("member"+i)
                        .password("$2a$10$Rsya1PvPOGeDGcLThcgji.oP9RTgf8zzvHF5TwnHXIGX1hqvDC/QC")
                        .role("ROLE_USER")
                        .emailAuth(true)
                        .signupDate(LocalDateTime.now())
                        .build();
                Member saved = memberRepository.save(member);
                Set<Long> userCategories = Set.of(1L, 3L, 14L);
                for (Long userCategory : userCategories) {
                    UserHaveCategory userHaveCategory = UserHaveCategory.builder()
                            .category(Category.builder().idx(userCategory).build())
                            .member(saved)
                            .build();
                    userHaveCategoryRepository.save(userHaveCategory);
                }
            }else{
                Member member = Member.builder()
                        .email("member"+i+"@email.com")
                        .nickname("member"+i)
                        .password("$2a$10$Rsya1PvPOGeDGcLThcgji.oP9RTgf8zzvHF5TwnHXIGX1hqvDC/QC")
                        .role("ROLE_USER")
                        .emailAuth(true)
                        .signupDate(LocalDateTime.now())
                        .build();
                Member saved = memberRepository.save(member);
                Set<Long> userCategories = Set.of(1L, 2L, 14L);
                for (Long userCategory : userCategories) {
                    UserHaveCategory userHaveCategory = UserHaveCategory.builder()
                            .category(Category.builder().idx(userCategory).build())
                            .member(saved)
                            .build();
                    userHaveCategoryRepository.save(userHaveCategory);
                }
            }
        }

        //member1 나눔글 생성
        for(int i=1;i<=30;i++){
            SharePost sharePost = SharePost.builder()
                    .member(Member.builder().idx(1L).build())
                    .btmCategory("bottom catrgory")
                    .title("member1 title"+i)
                    .contents("content"+i)
                    .category(Category.builder().idx(14L).build())
                    .currentEnrollment(0)
                    .capacity(5)
                    .deadline(10)
                    .postType("share")
                    .status(false)
                    .timeStamp(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();
            sharePostRepository.save(sharePost);
        }
        //member2 나눔글 생성
        for(int i=31;i<32;i++){
            SharePost sharePost = SharePost.builder()
                    .member(Member.builder().idx(2L).build())
                    .btmCategory("bottom catrgory")
                    .title("member2 title"+i)
                    .contents("content"+i)
                    .category(Category.builder().idx(14L).build())
                    .currentEnrollment(0)
                    .capacity(5)
                    .deadline(10)
                    .postType("share")
                    .status(false)
                    .timeStamp(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();
            sharePostRepository.save(sharePost);
        }

        //member3 나눔글 생성
        for(int i=32;i<33;i++){
            SharePost sharePost = SharePost.builder()
                    .member(Member.builder().idx(3L).build())
                    .btmCategory("java")
                    .title("member3 title"+i)
                    .contents("content"+i)
                    .category(Category.builder().idx(14L).build())
                    .currentEnrollment(1)
                    .capacity(1)
                    .deadline(10)
                    .postType("share")
                    .status(true)
                    .timeStamp(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();
            SharePost saved = sharePostRepository.save(sharePost);
            //member2가 3의 글에 참여
            PostMatchingMember postMatchingMember = PostMatchingMember.builder()
                    .sharePost(saved)
                    .member(Member.builder().idx(2L).build())
                    .build();
            postMatchingMemberRepository.save(postMatchingMember);
        }


        //member4 나눔글 생성
        for(int i=33;i<34;i++){
            SharePost sharePost = SharePost.builder()
                    .member(Member.builder().idx(4L).build())
                    .btmCategory("java")
                    .title("member3 title"+i)
                    .contents("content"+i)
                    .category(Category.builder().idx(14L).build())
                    .currentEnrollment(1)
                    .capacity(5)
                    .deadline(10)
                    .postType("share")
                    .status(false)
                    .timeStamp(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();
            SharePost saved = sharePostRepository.save(sharePost);
            //member1이 4에 참여
            PostMatchingMember postMatchingMember2 = PostMatchingMember.builder()
                    .sharePost(saved)
                    .member(Member.builder().idx(1L).build()).build();
            postMatchingMemberRepository.save(postMatchingMember2);
        }

        //member1 교환글 생성
        for(int i=1;i<=30;i++){
            ExchangePost exchangePost = ExchangePost.builder()
                    .member(Member.builder().idx(1L).build())
                    .contents("content"+i)
                    .postType("exchange")
                    .status(false)
                    .modifyTime(LocalDateTime.now())
                    .timeStamp(LocalDateTime.now())
                    .giveBtmCategory("give btm category")
                    .giveCategory(Category.builder().idx(1L).build())
                    .title("title"+i)
                    .takeBtmCategory("give btm category")
                    .takeCategory(Category.builder().idx(2L).build())
                    .build();
            exchangePostRepository.save(exchangePost);
        }

        //member2 교환글 생성
        for(int i=31;i<33;i++){
            if(i==31){
                ExchangePost exchangePost = ExchangePost.builder()
                        .member(Member.builder().idx(2L).build())
                        .contents("content"+i)
                        .postType("exchange")
                        .status(false)
                        .modifyTime(LocalDateTime.now())
                        .timeStamp(LocalDateTime.now())
                        .giveBtmCategory("give btm category")
                        .giveCategory(Category.builder().idx(1L).build())
                        .title("title"+i)
                        .takeBtmCategory("give btm category")
                        .takeCategory(Category.builder().idx(2L).build())
                        .build();
                exchangePostRepository.save(exchangePost);

            }else {
                ExchangePost exchangePost = ExchangePost.builder()
                        .member(Member.builder().idx(2L).build())
                        .contents("content"+i)
                        .postType("exchange")
                        .status(false)
                        .modifyTime(LocalDateTime.now())
                        .timeStamp(LocalDateTime.now())
                        .giveBtmCategory("give btm category")
                        .giveCategory(Category.builder().idx(1L).build())
                        .title("title"+i)
                        .takeBtmCategory("give btm category")
                        .takeCategory(Category.builder().idx(5L).build())
                        .build();
                exchangePostRepository.save(exchangePost);
            }
        }

    }

}
