package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.matching.model.dto.MatchingMemberRes;
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


    //TODO ej 나눔글에서 선착순매칭도 이동하기?

    //채팅(교환글)에서 교환매칭 확정 저장
    public MatchingMemberRes checkExchange(Member user, ChatRoom chatRoom) throws BaseException {
        //작성자만 확정할 수 있는 예외처리
        //챗룸에서 가져온 작성자 정보가 현재 로그인 한 유저가 맞는지
        if(!chatRoom.getExchangePost().getIdx().equals(chatRoom.getMember1().getIdx())) {
            throw new BaseException(BaseResponseStatus.CHAT_NOT_WRITER);
        }

        ExchangePost post = chatRoom.getExchangePost();

        PostMatchingMember postMatchingMember = PostMatchingMember.builder()
                .member(chatRoom.getMember2())
                .exchangePost(post)
                .build(); //채팅참여자와 교환글
        PostMatchingMember pm = matchingRepository.save(postMatchingMember);

        MatchingMemberRes matchingMemberRes = MatchingMemberRes.builder()
                .exchangePostIdx(pm.getExchangePost().getIdx())
                .memberIdx(pm.getMember().getIdx())
                .build();


        return matchingMemberRes;
    }

}
//        //작성자가 선택한 카테고리 안에 교환글의 줄 카테고리가 있는지
//        Optional<UserHaveCategory> uhc = userHaveCategoryRepository.findByMemberAndCategory(chatRoom.getMember1(),post.getGiveCategory());
//
//        if(!uhc.isPresent()) {
//            return "no!!!!";
//        }
