package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
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
    public boolean findChatRoom(Long postIdx, ChatMessage chatMessage) {
//        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member member2 = Member.builder()
                .idx(chatMessage.getSenderIdx())
                .build();//참여자
        Optional<ExchangePost> post = exchangePostRepository.findById(postIdx);
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
    public ChatRoom create(Long postIdx, Long senderIdx) throws BaseException{

        Optional<Member> m = memberRepository.findById(senderIdx);
        Optional<SharePost> e = sharePostRepository.findById(postIdx);

        if(m.isPresent() && e.isPresent()) {
            //채팅참여자가 선택한 카테고리 안에 교환글의 받을 카테고리가 있는지 -> 있으면 채팅방 만들어짐
            Optional<UserHaveCategory> uhc2 = userHaveCategoryRepository.findByMemberAndCategory(m.get(),e.get().getCategory());

            if(!uhc2.isPresent()) { //참여자가 선택한 카테고리에 교환 할 카테고리가 없는 이슈
                throw  new BaseException(BaseResponseStatus.CHAT_NOTFIND_CATE);
            }

        }

        ExchangePost exchangePost = ExchangePost.builder()
                .idx(postIdx)
                .build();

//        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member member = Member.builder()
                .idx(senderIdx)
                .build();

        Optional<ExchangePost> post = exchangePostRepository.findById(postIdx);
        if(post.isPresent()){

            Member member1 = Member.builder()
                    .idx(post.get().getMember().getIdx())
                    .build(); //작성자

            Member member2 = Member.builder()
                    .idx(member.getIdx())
                    .build(); //참여자

            ChatRoom chatRoom = ChatRoom.builder()
                    .exchangePost(exchangePost)
                    .member1(member1)
                    .member2(member2)
                    .build();
            ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);

            return newChatRoom;
        }else{
            return null;
        }
    }

}
