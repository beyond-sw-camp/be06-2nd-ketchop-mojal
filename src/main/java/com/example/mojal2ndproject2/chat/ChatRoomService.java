package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.chat.model.dto.request.RoomCreateReq;
import com.example.mojal2ndproject2.chat.model.dto.response.MessageGetRes;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.SharePostRepository;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final SharePostRepository sharePostRepository;


    //내가 참여한 채팅방 리스트 반환
    public List<Long> findMyChatRoomList(Long idx, CustomUserDetails customUserDetails) {
//        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member chatUser = Member.builder()
                .idx(idx)
                .build();// 채팅참여자
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMember1OrMember2(chatUser, chatUser);

        List<Long> myChatRoomIds = new ArrayList<>();
        for(ChatRoom r : chatRooms) {
            myChatRoomIds.add(r.getIdx());
        }

        return myChatRoomIds;
    }


    //채팅방 있나없나 검사!!
    public boolean findChatRoom(RoomCreateReq roomCreateReq) {
//        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member member2 = Member.builder()
                .idx(roomCreateReq.getParticipants())
                .build();//참여자
        Optional<ExchangePost> post = exchangePostRepository.findById(roomCreateReq.getPostIdx());
        if(post.isPresent()) {
            Member member1 = Member.builder()
                    .idx(post.get().getMember().getIdx())
                    .build(); //작성자

            Optional<ChatRoom> chatRoom = chatRoomRepository.findByMember1AndMember2AndExchangePost(member1, member2, post.get());

            if (chatRoom.isPresent()) {
                return true; //채팅방이 있다
            }

            return false;
        }
            return false;
    }


    //채팅방 생성
    public ChatRoom create(RoomCreateReq roomCreateReq) throws BaseException{

        Optional<Member> m = memberRepository.findById(roomCreateReq.getParticipants());
        Optional<ExchangePost> e = exchangePostRepository.findById(roomCreateReq.getPostIdx());

        if(m.isPresent() && e.isPresent()) {
            //채팅참여자가 선택한 카테고리 안에 교환글의 받을 카테고리가 있는지 -> 있으면 채팅방 만들어짐
            Optional<UserHaveCategory> uhc = userHaveCategoryRepository.findByMemberAndCategory(m.get(), e.get().getTakeCategory());

            if(!uhc.isPresent()) { //참여자가 선택한 카테고리에 교환 할 카테고리가 없는 이슈
                throw  new BaseException(BaseResponseStatus.CHAT_NOTFIND_CATE);
            }

        }

        if(e.isPresent()){

            Member member1 = Member.builder()
                    .idx(e.get().getMember().getIdx())
                    .build(); //작성자

            Member member2 = Member.builder()
                    .idx(roomCreateReq.getParticipants())
                    .build(); //참여자

            ChatRoom chatRoom = ChatRoom.builder()
                    .exchangePost(e.get())
                    .member1(member1)
                    .member2(member2)
                    .build();
            ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);

            return newChatRoom;
        }else{
            return null;
        }
    }


    //현재 채팅방의 과거 메세지 가져오는 부분
    //TODO 0720 순환참조 오류발생하여 수정코드
    public List<MessageGetRes> findCurrentMessageList(Long roomIdx) {
        ChatRoom chatRoom = ChatRoom.builder()
                .idx(roomIdx)
                .build();
        Optional<List<ChatMessage>> currentMessages = chatMessageRepository.findAllByChatRoom(chatRoom);

        if (currentMessages.isPresent()) {

            List<MessageGetRes> messageGetResList = new ArrayList<>();
            //dto 처리
            for(ChatMessage cm : currentMessages.get()) {
                MessageGetRes messageGetRes = MessageGetRes.builder()
                        .idx(cm.getIdx())
                        .senderIdx(cm.getSenderIdx())
                        .message(cm.getMessage())
                        .timeStamp(cm.getTimeStamp())
                        .build();

                messageGetResList.add(messageGetRes);
            }

            return messageGetResList;
        }
        return new ArrayList<>();
    }
}
