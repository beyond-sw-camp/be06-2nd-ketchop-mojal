package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.ChatRoomController;
import com.example.mojal2ndproject2.chat.ChatRoomRepository;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.matching.model.dto.MatchingConfirmedReq;
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
    private final ChatRoomRepository chatRoomRepository;


    //TODO ej 나눔글에서 선착순매칭도 이동하기?

    //채팅(교환글)에서 교환매칭 확정 저장
    public MatchingMemberRes checkExchange(MatchingConfirmedReq confirmedReq) throws BaseException {

//        ChatRoom chatRoom = ChatRoom.builder().idx(confirmedReq.getChatRoomIdx()).build(); //이건 아이디만 있는 껍데기라서 교환글을 가져올 수 없다
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(confirmedReq.getChatRoomIdx());

        if(chatRoom.isPresent()) {

            //작성자만 확정할 수 있는 예외처리
            //챗룸에서 가져온 작성자 정보가 현재 로그인 한 유저가 맞는지
            if(!chatRoom.get().getExchangePost().getMember().getIdx().equals(confirmedReq.getLoginUserIdx())) {
                throw new BaseException(BaseResponseStatus.CHAT_NOT_WRITER);
            }

            //매칭정보 저장
            ExchangePost post = chatRoom.get().getExchangePost();

            PostMatchingMember postMatchingMember = PostMatchingMember.builder()
                    .member(chatRoom.get().getMember2())
                    .exchangePost(post)
                    .build(); //채팅참여자와 교환글
            PostMatchingMember pm = matchingRepository.save(postMatchingMember);

            MatchingMemberRes matchingMemberRes = MatchingMemberRes.builder()
                    .exchangePostIdx(pm.getExchangePost().getIdx())
                    .member1Idx(pm.getExchangePost().getMember().getIdx()) //작성자idx
                    .member2Idx(pm.getMember().getIdx()) //참여자idx
                    .build();

            return matchingMemberRes;

        }
        return null; //TODO 리턴값 수정필요
    }

}

