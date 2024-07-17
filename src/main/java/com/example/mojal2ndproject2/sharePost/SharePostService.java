package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.PostMatchingMemberRepository;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostListRes;
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
    private final PostMatchingMemberRepository postMatchingMemberRepository;
    
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

    //내가 작성한 나눔글 전체조회
    public List<SharePostListRes> userlist(Long loginUserIdx) {
        Member member = Member.builder()
                .idx(loginUserIdx)
                .build();
        List<SharePost> posts = sharePostRepository.findAllByMember(member);

        List<SharePostListRes> sharePostListRess = new ArrayList<>();
        for (SharePost post : posts) {
            sharePostListRess.add(SharePostListRes.builder()
                    .writer(post.getMember())
                    .title(post.getTitle())
                    .timeStamp(post.getTimeStamp())
                    .status(post.getStatus())
                    .postType(post.getPostType())
                    .deadline(post.getDeadline())
                    .capacity(post.getCapacity())
                    .currentEnrollment(post.getCurrentEnrollment())
                    .category(post.getCategory().getName())
                    .btmCategory(post.getBtmCategory())
                    .build());
        }
        return sharePostListRess;
    }

    //내가 참여한 나눔글 전체조회
    //포스트매칭멤버 테이블에서 멤버-나눔글 짝을 찾아서 있으면 조회
    public List<SharePostListRes> listOpen(Long loginUserIdx) {
        Member member = Member.builder()
                .idx(loginUserIdx)
                .build();
        List<PostMatchingMember> pmms = postMatchingMemberRepository.findAllByMember(member);

        List<SharePostListRes> sharePostListRess = new ArrayList<>();

        for (PostMatchingMember pmm : pmms) {
            if(pmm.getExchangePost() != null) {
                continue;
            }

            sharePostListRess.add(SharePostListRes.builder()
                    .writer(pmm.getSharePost().getMember())
                    .title(pmm.getSharePost().getTitle())
                    .timeStamp(pmm.getSharePost().getTimeStamp())
                    .status(pmm.getSharePost().getStatus())
                    .postType(pmm.getSharePost().getPostType())
                    .deadline(pmm.getSharePost().getDeadline())
                    .capacity(pmm.getSharePost().getCapacity())
                    .currentEnrollment(pmm.getSharePost().getCurrentEnrollment())
                    .category(pmm.getSharePost().getCategory().getName())
                    .btmCategory(pmm.getSharePost().getBtmCategory())
                    .build());
        }
        return sharePostListRess;
    }


    //나눔글 조회
    public SharePostReadRes read(Long requestIdx, Long idx) {
        Optional<SharePost> result = sharePostRepository.findById(idx);

        if(result.isPresent()){
            SharePost sharePost = result.get();
            Member autor = sharePost.getMember();
            Long authorIdx = autor.getIdx();
            String author = autor.getNickname();

            if(authorIdx==requestIdx){
                List<String> members = new ArrayList<>();

                List<PostMatchingMember> postMatchingMembers = sharePost.getPostMatchingMembers();
                for (PostMatchingMember m : postMatchingMembers) {
                    String nickname = m.getMember().getNickname();
                    members.add(nickname);
                }

                SharePostReadRes sharePostReadRes = SharePostReadRes.builder()
                        .authorIdx(authorIdx)
                        .author(author)
                        .title(sharePost.getTitle())
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
                        .authorIdx(authorIdx)
                        .author(author)
                        .title(sharePost.getTitle())
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


    public List<SharePostReadRes> list(Long requestIdx){
        List<SharePost> posts = sharePostRepository.findAll();
        List<SharePostReadRes> results = new ArrayList<>();
        for (SharePost post : posts) {
            Member autor = post.getMember();
            Long authorIdx = autor.getIdx();
            String author = autor.getNickname();

            if(authorIdx==requestIdx){
                List<String> members = new ArrayList<>();

                List<PostMatchingMember> postMatchingMembers = post.getPostMatchingMembers();
                for (PostMatchingMember m : postMatchingMembers) {
                    String nickname = m.getMember().getNickname();
                    int index = nickname.indexOf("kToken"); // Todo 회원가입할 때 "kToken이라는 키워드 못들어가게 처리"
                    if(index != -1){
                        nickname = nickname.substring(0, index);
                    }
                    members.add(nickname);
                }

                SharePostReadRes sharePostReadRes = SharePostReadRes.builder()
                        .authorIdx(authorIdx)
                        .author(author)
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .timeStamp(post.getTimeStamp())
                        .status(post.getStatus())
                        .postType(post.getPostType())
                        .deadline(post.getDeadline())
                        .capacity(post.getCapacity())
                        .currentEnrollment(post.getCurrentEnrollment())
                        .category(post.getCategory().getName())
                        .btmCategory(post.getBtmCategory())
                        .matchingMembers(members)
                        .build();

                results.add(sharePostReadRes);
            }
            else{
                SharePostReadRes sharePostReadRes = SharePostReadRes.builder()
                        .authorIdx(authorIdx)
                        .author(author)
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .timeStamp(post.getTimeStamp())
                        .status(post.getStatus())
                        .postType(post.getPostType())
                        .deadline(post.getDeadline())
                        .capacity(post.getCapacity())
                        .currentEnrollment(post.getCurrentEnrollment())
                        .category(post.getCategory().getName())
                        .btmCategory(post.getBtmCategory())
                        .build();

                results.add(sharePostReadRes);
            }
        }

        return results;
    }

    public String enrollment(Long requestIdx, Long idx) {
      Optional<SharePost> result = sharePostRepository.findById(idx);
      Member member = Member.builder().idx(requestIdx).build();
      String response="";
      if (result.isPresent()) {
          SharePost sharePost = result.get();
          if (sharePost.getStatus() == false) {
              if (sharePost.getCapacity() == sharePost.getCurrentEnrollment() + 1) {
                  PostMatchingMember pmm = PostMatchingMember.builder()
                          .sharePost(sharePost)
                          .member(member)
                          .build();
                  SharePost sharePost1 = SharePost.builder()
                          .idx(sharePost.getIdx())
                          .member(member)
                          .title(sharePost.getTitle())
                          .contents(sharePost.getContents())
                          .timeStamp(sharePost.getTimeStamp())
                          .modifyTime(sharePost.getModifyTime())
                          .status(false)
                          .postType(true)
                          .deadline(sharePost.getDeadline())
                          .capacity(sharePost.getCapacity())
                          .currentEnrollment(sharePost.getCurrentEnrollment() + 1)
                          .category(sharePost.getCategory())
                          .btmCategory(sharePost.getBtmCategory())
                          .build();
                  sharePostRepository.save(sharePost1);
              }
              else {
                  PostMatchingMember pmm = PostMatchingMember.builder()
                          .sharePost(sharePost)
                          .member(member)
                          .build();
                  SharePost sharePost1 = SharePost.builder()
                          .idx(sharePost.getIdx())
                          .member(member)
                          .title(sharePost.getTitle())
                          .contents(sharePost.getContents())
                          .timeStamp(sharePost.getTimeStamp())
                          .modifyTime(sharePost.getModifyTime())
                          .status(false)
                          .postType(true)
                          .deadline(sharePost.getDeadline())
                          .capacity(sharePost.getCapacity())
                          .currentEnrollment(sharePost.getCurrentEnrollment() + 1)
                          .category(sharePost.getCategory())
                          .btmCategory(sharePost.getBtmCategory())
                          .build();
                  sharePostRepository.save(sharePost1);
              }
              response = "나눔글 참여에 성공하였습니다";
          }
      }else{
          response="이미 마감됐습니다";
      }
      return response;
    }
}
