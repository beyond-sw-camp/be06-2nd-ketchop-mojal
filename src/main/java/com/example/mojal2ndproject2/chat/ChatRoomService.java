package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.chat.model.dto.request.RoomCreateReq;
import com.example.mojal2ndproject2.chat.model.dto.response.MessageGetRes;
import com.example.mojal2ndproject2.chat.model.dto.response.RoomGetRes;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.SharePostRepository;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final SharePostRepository sharePostRepository;


    //로그인한 유저가 참여한 채팅방 리스트 반환
    public List<RoomGetRes> findMyChatRoomList(Long idx, CustomUserDetails customUserDetails) {
//        Long loginUserIdx = customUserDetails.getMember().getIdx();

        Member loginUser = Member.builder()
                .idx(idx)
                .build();// 로그인한 유저

        //로그인 한 유저가 작성자이거나 참여자일때 생성된 채팅방 모두 검색
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMember1OrMember2(loginUser, loginUser);

        //반환할 리스트 생성
        List<RoomGetRes> myChatRoomsList = new ArrayList<>();

        for(ChatRoom cr : chatRooms) {

            // 각 채팅방의 최근 메시지 가져오기
            Optional<ChatMessage> lastMessageOpt = chatMessageRepository.findFirstByChatRoomOrderByTimeStampDesc(cr);
            String lastMessage = "";
            String lastMessageTimeStamp = "";

            if (lastMessageOpt.isPresent()) {
                lastMessage = lastMessageOpt.get().getMessage();
                lastMessageTimeStamp = lastMessageOpt.get().getTimeStamp();
            }

            RoomGetRes roomGetRes = RoomGetRes.builder()
                    .roomIdx(cr.getIdx())
                    .postIdx(cr.getExchangePost().getIdx())
                    .postWriterNickname(cr.getMember1().getNickname())
                    .participantNickname(cr.getMember2().getNickname())
                    .title(cr.getExchangePost().getTitle())
                    .giveBtmCategory(cr.getExchangePost().getGiveBtmCategory())
                    .takeBtmCategory(cr.getExchangePost().getTakeBtmCategory())
                    .status(cr.getExchangePost().getStatus())
                    .lastMessage(lastMessage) // 최근 메시지 추가
                    .lastMessageTimeStamp(lastMessageTimeStamp) // 최근 메시지 시간 추가
                    .build();

            myChatRoomsList.add(roomGetRes);
        }

        return myChatRoomsList;
    }


    //채팅방 유무 검사
    public Long findChatRoom(RoomCreateReq roomCreateReq) {
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
                return chatRoom.get().getIdx();
//                return true; //채팅방이 있다
            }

            return 0L;
//            return false;
        }
        return 0L;
//        return false;
    }


    //채팅방 생성
    public ChatRoom create(RoomCreateReq roomCreateReq) throws BaseException{

        Optional<ExchangePost> e = exchangePostRepository.findById(roomCreateReq.getPostIdx()); //TODO 성능개선 필요(바로 req에서 카테고리 IDX넘길 수 있게)

        if(e.isPresent()) {
            //채팅참여자가 선택한 카테고리 안에 교환글의 받을 카테고리가 있는지 -> 있으면 채팅방 만들어짐
            Optional<UserHaveCategory> uhc = userHaveCategoryRepository.findByMemberAndCategory(
                    Member.builder().idx(roomCreateReq.getParticipants()).build(),
                    Category.builder().idx(e.get().getTakeCategory().getIdx()).build());

            if(!uhc.isPresent()) { //참여자가 선택한 카테고리에 교환 할 카테고리가 없는 이슈
                log.info("participants category is not matched exchange post of take category.");
                throw new BaseException(BaseResponseStatus.CHAT_NOTFIND_CATE);
            }

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

        }
        return null;

    }


    //현재 채팅방의 과거 메세지 가져오는 부분
    //순환참조 오류발생하여 수정코드
    public List<MessageGetRes> findCurrentMessageList(Long roomIdx) {

//        List<ChatMessage> currentMessages = chatMessageRepository.findAllByChatRoom(ChatRoom.builder().idx(roomIdx).build());
        List<MessageGetRes> messageGetResList = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "idx"));
        Slice<ChatMessage> currentMessages = chatMessageRepository.findAllByChatRoom(ChatRoom.builder().idx(roomIdx).build(), pageable);

        for(ChatMessage cm : currentMessages) {
            MessageGetRes messageGetRes = MessageGetRes.builder()
                    .idx(cm.getIdx())
                    .senderIdx(cm.getSenderIdx())
//                    .senderIdx(cm.getMember().getIdx()) //챗메시지에 멤버가 저장되지 않아서 일단 뺌
                    .message(cm.getMessage())
                    .timeStamp(cm.getTimeStamp())
                    .build();

            messageGetResList.add(messageGetRes);
        }

        return messageGetResList;


//        Optional<List<ChatMessage>> currentMessages = chatMessageRepository.findAllByChatRoom(chatRoom);
//
//        if (currentMessages.isPresent()) {
//
//            List<MessageGetRes> messageGetResList = new ArrayList<>();
//            //dto 처리
//            for(ChatMessage cm : currentMessages.get()) {
//                MessageGetRes messageGetRes = MessageGetRes.builder()
//                        .idx(cm.getIdx())
//                        .senderIdx(cm.getSenderIdx())
//                        .message(cm.getMessage())
//                        .timeStamp(cm.getTimeStamp())
//                        .build();
//
//                messageGetResList.add(messageGetRes);
//            }
//
//            return messageGetResList;
//        }
//        return new ArrayList<>();
    }
}
