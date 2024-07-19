package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
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
import org.springframework.web.method.annotation.SessionAttributesHandler;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.THIS_POST_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class SharePostService {
    private final SharePostRepository sharePostRepository;
    private final MemberRepository memberRepository;
    private final PostMatchingMemberRepository postMatchingMemberRepository;
    
  public SharePostCreateRes create(Long requestIdx, SharePostCreateReq request) {
        Member member = Member.builder().idx(requestIdx).build();

        Category category = Category.builder().idx(request.getCategoryIdx()).build();

//        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        SharePost sharePost = SharePost.builder()
                .member(member)
                .title(request.getTitle())
                .contents(request.getContents())
                .timeStamp(LocalDateTime.now())
                .modifyTime(LocalDateTime.now())
                .status(false)
                .postType("share")
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
    public List<SharePostListRes> authorList(Long loginUserIdx) {
        Member member = Member.builder()
                .idx(loginUserIdx)
                .build();
//        List<SharePost> posts = sharePostRepository.findAllByMember(member);
        List<SharePost> posts = sharePostRepository.findAllByMemberWithMemberAndCategory(member);

        List<SharePostListRes> sharePostListRes = new ArrayList<>();
        for (SharePost post : posts) {
            sharePostListRes.add(SharePostListRes.builder()
                    .writerIdx(post.getMember().getIdx())
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
        return sharePostListRes;
    }

    //내가 참여한 나눔글 전체조회
    //포스트매칭멤버 테이블에서 멤버-나눔글 짝을 찾아서 있으면 조회
    public List<SharePostListRes> enrolledList(Member member) {

        //List<PostMatchingMember> pmms = postMatchingMemberRepository.findAllByMember(member);
        List<SharePost> posts = sharePostRepository.findAllByMemberWithMatchingMembersAndCategory(member);

        List<SharePostListRes> sharePostEnrollmentListRes = new ArrayList<>();

        for (SharePost post : posts) {
            sharePostEnrollmentListRes.add(
                    SharePostListRes.builder()
                    .title(post.getTitle())
                    .writerIdx(post.getMember().getIdx())
                    .capacity(post.getCapacity())
                    .currentEnrollment(post.getCurrentEnrollment())
                    .deadline(post.getDeadline())
                    .category(post.getCategory().getName())
                    .btmCategory(post.getBtmCategory())
                    .postType(post.getPostType())
                    .status(post.getStatus())
                    .timeStamp(post.getTimeStamp())
                    .build());
        }

//        for (PostMatchingMember pmm : pmms) {
//            if(pmm.getExchangePost() != null) { //Todo byul : 교환글 참여도 있을 때 한번 더 확인
//                continue;
//            }
//
//            sharePostListRes.add(SharePostListRes.builder()
//                    .writerIdx(pmm.getSharePost().getMember().getIdx())
//                    .title(pmm.getSharePost().getTitle())
//                    .timeStamp(pmm.getSharePost().getTimeStamp())
//                    .status(pmm.getSharePost().getStatus())
//                    .postType(pmm.getSharePost().getPostType())
//                    .deadline(pmm.getSharePost().getDeadline())
//                    .capacity(pmm.getSharePost().getCapacity())
//                    .currentEnrollment(pmm.getSharePost().getCurrentEnrollment())
//                    .category(pmm.getSharePost().getCategory().getName())
//                    .btmCategory(pmm.getSharePost().getBtmCategory())
//                    .build());
//        }
        return sharePostEnrollmentListRes;
    }


    //나눔글 조회
    public SharePostReadRes read(Long requestIdx, Long idx) throws BaseException {
//        Optional<SharePost> result = sharePostRepository.findById(idx);
        SharePost sharePost = sharePostRepository.findByIdxWithMemberAndCategory(idx)
                .orElseThrow(() -> new BaseException(THIS_POST_NOT_EXIST));

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
    }


    public List<SharePostReadRes> list(Long requestIdx){
        List<SharePost> posts = sharePostRepository.findAll();
//        List<SharePost> posts = sharePostRepository.findAllPostWithMemberAndCategory();
        List<SharePostReadRes> results = new ArrayList<>();
        for (SharePost post : posts) {
            Member autor = post.getMember(); //여기서 조인?
            Long authorIdx = autor.getIdx();
            String author = autor.getNickname();

//            if(authorIdx==requestIdx){
//                List<String> members = new ArrayList<>();
//
//                //Todo byul : 근데 전체 리스트라 이거 안필요하지 않나?!
//                List<PostMatchingMember> postMatchingMembers = post.getPostMatchingMembers();
//                for (PostMatchingMember m : postMatchingMembers) {
//                    String nickname = m.getMember().getNickname();
//                    members.add(nickname);
//                }

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
//                        .matchingMembers(members)
                        .build();

                results.add(sharePostReadRes);
//            }
//            else{
//                SharePostReadRes sharePostReadRes = SharePostReadRes.builder()
//                        .authorIdx(authorIdx)
//                        .author(author)
//                        .title(post.getTitle())
//                        .contents(post.getContents())
//                        .timeStamp(post.getTimeStamp())
//                        .status(post.getStatus())
//                        .postType(post.getPostType())
//                        .deadline(post.getDeadline())
//                        .capacity(post.getCapacity())
//                        .currentEnrollment(post.getCurrentEnrollment())
//                        .category(post.getCategory().getName())
//                        .btmCategory(post.getBtmCategory())
//                        .build();
//
//                results.add(sharePostReadRes);
//            }
        }

        return results;
    }

    public BaseResponse<String> enrollment(Member member, Long idx) throws BaseException {
//      SharePost sharePost = sharePostRepository.findById(idx)
//              .orElseThrow(() -> new BaseException(THIS_POST_NOT_EXIST));
        SharePost sharePost = sharePostRepository.findByIdWithMember(idx)
                .orElseThrow(()-> new BaseException(THIS_POST_NOT_EXIST));
      Optional<PostMatchingMember> now = postMatchingMemberRepository.findByMemberAndSharePost(member, sharePost);
        if (now.isPresent()) {
            throw new BaseException(BaseResponseStatus.ALREADY_REQUEST);
        }
      String response="";

//          SharePost sharePost = result.get();
      if (sharePost.getStatus() == false) {
          if (sharePost.getCapacity() == sharePost.getCurrentEnrollment() + 1) {
              PostMatchingMember pmm = PostMatchingMember.builder()
                      .sharePost(sharePost)
                      .member(member)
                      .exchangePost(null)
                      .build();
              postMatchingMemberRepository.save(pmm);
              SharePost sharePost1 = SharePost.builder()
                      .idx(sharePost.getIdx())
                      .member(member)
                      .title(sharePost.getTitle())
                      .contents(sharePost.getContents())
                      .timeStamp(sharePost.getTimeStamp())
                      .modifyTime(sharePost.getModifyTime())
                      .status(false)
                      .postType("share")
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
                      .exchangePost(null)
                      .build();
              postMatchingMemberRepository.save(pmm);
              SharePost sharePost1 = SharePost.builder()
                      .idx(sharePost.getIdx())
                      .member(member)
                      .title(sharePost.getTitle())
                      .contents(sharePost.getContents())
                      .timeStamp(sharePost.getTimeStamp())
                      .modifyTime(sharePost.getModifyTime())
                      .status(false)
                      .postType("share")
                      .deadline(sharePost.getDeadline())
                      .capacity(sharePost.getCapacity())
                      .currentEnrollment(sharePost.getCurrentEnrollment() + 1)
                      .category(sharePost.getCategory())
                      .btmCategory(sharePost.getBtmCategory())
                      .build();
              sharePostRepository.save(sharePost1);
          }
              response = "나눔글 참여 성공하였습니다";
      }
      return new BaseResponse<>(response);
    }
}
