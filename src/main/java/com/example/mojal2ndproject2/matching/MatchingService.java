package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.SharePostRepository;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MemberRepository memberRepository;
    private final SharePostRepository sharePostRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final PostMatchingMemberRepository matchingRepository;


    //채팅(교환글)에서 교환매칭 저장
    public String checkExchange(ChatRoom chatRoom) {

        ExchangePost post = chatRoom.getExchangePost();

//        //작성자가 선택한 카테고리 안에 교환글의 줄 카테고리가 있는지
//        Optional<UserHaveCategory> uhc = userHaveCategoryRepository.findByMemberAndCategory(chatRoom.getMember1(),post.getGiveCategory());
//
//        if(!uhc.isPresent()) {
//            return "no!!!!";
//        }

        //채팅참여자가 선택한 카테고리 안에 교환글의 받을 카테고리가 있는지
        Optional<UserHaveCategory> uhc2 = userHaveCategoryRepository.findByMemberAndCategory(chatRoom.getMember2(),post.getTakeCategory());

        if(!uhc2.isPresent()) {
            return "no!!!!";
        }

        PostMatchingMember postMatchingMember = PostMatchingMember.builder()
                .member(chatRoom.getMember2())
                .exchangePost(post)
                .build(); //채팅참여자와 교환글
        matchingRepository.save(postMatchingMember);

        return "matching ok";
    }

}
