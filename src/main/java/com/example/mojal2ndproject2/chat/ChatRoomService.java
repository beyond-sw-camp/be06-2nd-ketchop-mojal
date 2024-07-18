package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.exchangepost.ExchangePostRepository;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
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

    //내가 참여한 채팅방 리스트 반환
    public List<Long> findMyChatRoomList(CustomUserDetails customUserDetails) {
        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member chatUser = Member.builder()
                .idx(loginUserIdx)
                .build();// 채팅참여자
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMember1OrMember2(chatUser, chatUser);

        List<Long> myChatRoomIds = new ArrayList<>();
        for(ChatRoom r : chatRooms) {
            myChatRoomIds.add(r.getIdx());
        }

        return myChatRoomIds;
    }


    //채팅방 있나없나 검사!!
    public boolean findChatRoom(Long postIdx, ChatMessage chatMessage, CustomUserDetails customUserDetails) {
        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member member1 = Member.builder()
                .idx(loginUserIdx)
                .build();//참여자
        Optional<ExchangePost> post = exchangePostRepository.findById(postIdx);
        if(post.isPresent()) {
            Member member2 = Member.builder()
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
    public ChatRoom create(Long postIdx, CustomUserDetails customUserDetails) {
        ExchangePost exchangePost = ExchangePost.builder()
                .idx(postIdx)
                .build();

        Long loginUserIdx = customUserDetails.getMember().getIdx();
        Member member = Member.builder()
                .idx(loginUserIdx)
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
