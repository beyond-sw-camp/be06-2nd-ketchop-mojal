package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.exchangepost.model.dto.respone.ExchangePostReadRes;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangePostService {
    private final ExchangePostRepository exchangePostRepository;

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

//    public List<ExchangePostReadRes> exchangeList(Long requestIdx) {
//        Member member = Member.builder()
//                .idx(requestIdx)
//                .build();
//        List<ExchangePostReadRes> exchangePostReadResList = new ArrayList<>();
//        List<PostMatchingMember> postMatchingMemberList =
//
//
//        return exchangePostReadResList;
//    }
}
