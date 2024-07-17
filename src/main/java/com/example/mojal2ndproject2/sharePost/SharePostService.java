package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostReadRes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharePostService {
    private final SharePostRepository sharePostRepository;
    private final MemberRepository memberRepository;

    public SharePostReadRes read(Long requestIdx, Long idx) {
        Optional<SharePost> result = sharePostRepository.findById(idx);
        if(result.isPresent()){
            SharePost sharePost = result.get();
            Member writer = sharePost.getMember();
            if(writer.getIdx()==requestIdx){
                List<String> members = new ArrayList<>();

                List<PostMatchingMember> postMatchingMembers = sharePost.getPostMatchingMembers();
                for (PostMatchingMember m : postMatchingMembers) {
                    String nickname = m.getMember().getNickname();
                    int index = nickname.indexOf("kToken"); // Todo 회원가입할 때 "kToken이라는 키워드 못들어가게 처리"
                    if(index != -1){
                        nickname = nickname.substring(0, index);
                    }
                    members.add(nickname);
                }

                SharePostReadRes sharePostReadRes = SharePostReadRes.builder()
                        .writer(sharePost.getMember()) //Todo 객체를 따로 만들지? 아니면 각각 name, idx 쓸지?
                        .title(sharePost.getTitle())
                        .contents(sharePost.getContents())
                        .timeStamp(sharePost.getTimeStamp())
                        .status(sharePost.getStatus())
                        .postType(sharePost.getPostType())
                        .deadline(sharePost.getDeadline())
                        .capacity(sharePost.getCapacity())
                        .currentEnrollment(sharePost.getCurrentEnrollment())
                        .category(sharePost.getCategory().getName())
                        .btmCategory(sharePost.getBtmCategory())
                        .matchingMembers(members)
                        .build();

                return sharePostReadRes;
            }else{
                SharePostReadRes sharePostReadRes = SharePostReadRes.builder()
                        .writer(sharePost.getMember())
                        .title(sharePost.getTitle())
                        .contents(sharePost.getContents())
                        .timeStamp(sharePost.getTimeStamp())
                        .status(sharePost.getStatus())
                        .postType(sharePost.getPostType())
                        .deadline(sharePost.getDeadline())
                        .capacity(sharePost.getCapacity())
                        .currentEnrollment(sharePost.getCurrentEnrollment())
                        .category(sharePost.getCategory().getName())
                        .btmCategory(sharePost.getBtmCategory())
                        .build();

                return sharePostReadRes;
            }
        }else{
            return null;
        }
    }

    public SharePostCreateRes create(Long requestIdx, SharePostCreateReq request) {
        Member member = Member.builder().idx(requestIdx).build();

        Category category = Category.builder().idx(request.getCategoryIdx()).build();

        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        SharePost sharePost = SharePost.builder()
                .member(member)
                .title(request.getTitle())
                .contents(request.getContents())
                .timeStamp(createdAt)
                .modifyTime(createdAt)
                .status(false)
                .postType(true)
                .deadline(request.getDeadline())
                .capacity(request.getCapacity())
                .currentEnrollment(0)
                .category(category)
                .btmCategory(request.getBtmCategory())
                .build();

        SharePost result = sharePostRepository.save(sharePost);

        SharePostCreateRes sharePostCreateRes = SharePostCreateRes.builder()
                .idx(result.getIdx())
                .writerIdx(result.getMember().getIdx())
                .title(result.getTitle())
                .build();

        return sharePostCreateRes;
    }

    public void test(String email) {
//        memberRepository.findByEmail(email);
        throw new ArrayIndexOutOfBoundsException();
    }
}
