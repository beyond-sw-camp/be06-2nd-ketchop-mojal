package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByMember1AndMember2AndExchangePost(Member member1, Member member2, ExchangePost exchangePost);

    //TODO ej optinal로 바꾸기?
    List<ChatRoom> findAllByMember1OrMember2(Member chatUser1, Member chatUser2);
}
