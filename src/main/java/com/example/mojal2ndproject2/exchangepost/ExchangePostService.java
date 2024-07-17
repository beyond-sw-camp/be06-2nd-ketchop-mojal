package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.exchangepost.model.dto.respone.ExchangePostReadRes;
import com.example.mojal2ndproject2.matching.PostMatchingMemberRepository;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangePostService {
    private final ExchangePostRepository exchangePostRepository;
    private final PostMatchingMemberRepository postMatchingMemberRepository;
    private final CategoryRepository categoryRepository;


    public List<ExchangePostReadRes> authorExchangeList(Long requestIdx) {
        Member member = Member.builder()
                .idx(requestIdx)
                .build();
        List<ExchangePost> result = exchangePostRepository.findAllByMember(member);
        List<ExchangePostReadRes> exchangePostReadResList = new ArrayList<>();
        for (ExchangePost e : result) {
            if (e.getMember().getIdx() == requestIdx) {
                ExchangePostReadRes exchangePostReadRes = ExchangePostReadRes.builder()
                        .idx(e.getIdx())
                        .title(e.getTitle())
                        .timeStamp(e.getTimeStamp())
                        .modifyTime(e.getModifyTime())
                        .status(e.getStatus())
                        .postType(e.getPostType())
                        .memberIdx(e.getMember().getIdx())
                        .memberNickname(e.getMember().getNickname())
                        .giveBtmCategory(e.getGiveBtmCategory())
                        .takeBtmCategory(e.getTakeBtmCategory())
                        .build();
                exchangePostReadResList.add(exchangePostReadRes);
            }
        }
        return exchangePostReadResList;
    }

    public List<ExchangePostReadRes> exchangeList(Long requestIdx) {
        Member member = Member.builder()
                .idx(requestIdx)
                .build();
        List<ExchangePostReadRes> exchangePostReadResList = new ArrayList<>();
        List<PostMatchingMember> postMatchingMemberList = postMatchingMemberRepository.findAllByMember(member);

        for (PostMatchingMember p : postMatchingMemberList ) {
            if (p.getExchangePost() != null) {
                ExchangePostReadRes exchangePostReadRes = ExchangePostReadRes.builder()
                        .idx(p.getExchangePost().getIdx())
                        .title(p.getExchangePost().getTitle())
                        .timeStamp(p.getExchangePost().getTimeStamp())
                        .modifyTime(p.getExchangePost().getModifyTime())
                        .status(p.getExchangePost().getStatus())
                        .postType(p.getExchangePost().getPostType())
                        .memberIdx(p.getMember().getIdx())
                        .memberNickname(p.getMember().getNickname())
                        .giveBtmCategory(p.getExchangePost().getGiveBtmCategory())
                        .takeBtmCategory(p.getExchangePost().getTakeBtmCategory())
                        .giveCategoryName(p.getExchangePost().getGiveCategory().getName())
                        .takeCategoryName(p.getExchangePost().getTakeCategory().getName())
                        .build();
                exchangePostReadResList.add(exchangePostReadRes);
            }
        }
        return exchangePostReadResList;
    }
    public CreateExchangePostRes create(CreateExchangePostReq req, CustomUserDetails customUserDetails){
        Category newGiveCategory = Category.builder()
                .idx(req.getGiveCategoryIdx())
                .build();

        Category newTakeCategory = Category.builder()
                .idx(req.getTakeCategoryIdx())
                .build();


        Long memberIdx = customUserDetails.getMember().getIdx();
        Member newMember = Member.builder()
                .idx(memberIdx)
                .build();
        String nickname = customUserDetails.getMember().getNickname();

        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ExchangePost exchangePost = ExchangePost.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .postType(req.getPostType())
                .giveCategory(newGiveCategory)
                .takeCategory(newTakeCategory)
                .giveBtmCategory(req.getGiveBtmCategory())
                .takeBtmCategory(req.getTakeBtmCategory())
                .timeStamp(createdAt)
                .modifyTime(createdAt)
                .member(newMember)
                .status(false)
                .build();
        exchangePostRepository.save(exchangePost);


        return CreateExchangePostRes.builder()
                .idx(memberIdx)
                .title(req.getTitle())
                .content(req.getContents())
                .build();
    }
}
