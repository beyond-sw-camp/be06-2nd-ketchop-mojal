package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
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

@Service
@RequiredArgsConstructor
public class ExchangePostService {
    private final ExchangePostRepository exchangePostRepository;
    private final CategoryRepository categoryRepository;

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
