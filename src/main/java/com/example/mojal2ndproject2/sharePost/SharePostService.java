package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.image.SharePostImages;
import com.example.mojal2ndproject2.image.SharePostImagesRepository;
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
import java.util.ArrayList;
import java.util.List;

import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.CHECK_CATEGORY_MORE_THAN_ONE;

@Service
@RequiredArgsConstructor
public class SharePostService {
    private final SharePostRepository sharePostRepository;
    private final MemberRepository memberRepository;
    private final PostMatchingMemberRepository postMatchingMemberRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;
    private final SharePostImagesRepository sharePostImagesRepository;

    /*******나눔글 생성***********/
  public SharePostCreateRes create(Long requestIdx, SharePostCreateReq request, List<String> images) throws BaseException{

      //회원가입시 선택한 카테고리가 없을때 예외처리
      UserHaveCategory userHaveCategory = userHaveCategoryRepository.findByMemberAndCategory(Member.builder().idx(requestIdx).build(), Category.builder().idx(request.getCategoryIdx()).build()).orElseThrow(
              () -> new BaseException(CHECK_CATEGORY_MORE_THAN_ONE)
      );


        Member member = Member.builder().idx(requestIdx).build();

        Category category = Category.builder().idx(request.getCategoryIdx()).build();

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

      for (String image : images) {
          sharePostImagesRepository.save(SharePostImages.builder()
                  .url(image)
                  .sharePost(result).build());
      }

        SharePostCreateRes sharePostCreateRes = SharePostCreateRes.builder()
                .idx(result.getIdx())
                .writerIdx(result.getMember().getIdx())
                .title(result.getTitle())
                .build();


        return sharePostCreateRes;
    }

    /****************내가 작성한 나눔글 전체조회************/
    public List<SharePostListRes> authorList(Long loginUserIdx) {
        Member member = Member.builder()
                .idx(loginUserIdx)
                .build();
//        List<SharePost> posts = sharePostRepository.findAllByMember(member);
        List<SharePost> posts = sharePostRepository.findAllByMemberWithMemberAndCategory(member);

        List<SharePostListRes> responses = makeResponseList(posts);
        return responses;
    }

    /***********내가 참여한 나눔글 전체조회************/
    //포스트매칭멤버 테이블에서 멤버-나눔글 짝을 찾아서 있으면 조회
    public List<SharePostListRes> enrolledList(Member member) {

        //List<PostMatchingMember> pmms = postMatchingMemberRepository.findAllByMember(member);
        List<PostMatchingMember> posts = postMatchingMemberRepository.findAllByMemberWithSharePostAndCategory(member);


        List<SharePostListRes> sharePostEnrollmentListRes = new ArrayList<>();

        for(PostMatchingMember post : posts){
            sharePostEnrollmentListRes.add(
                    SharePostListRes.builder()
                            .title(post.getSharePost().getTitle())
                            .memberIdx(post.getSharePost().getMember().getIdx())
                            .capacity(post.getSharePost().getCapacity())
                            .currentEnrollment(post.getSharePost().getCurrentEnrollment())
                            .deadline(post.getSharePost().getDeadline())
                            .category(post.getSharePost().getCategory().getName())
                            .btmCategory(post.getSharePost().getBtmCategory())
                            .postType(post.getSharePost().getPostType())
                            .status(post.getSharePost().getStatus())
                            .timeStamp(post.getSharePost().getTimeStamp())
                            .build());
        }

        return sharePostEnrollmentListRes;
    }


    /***************나눔글 하나 조회************/
    public SharePostReadRes read(Long requestIdx, Long idx) throws BaseException {
//        Optional<SharePost> result = sharePostRepository.findById(idx);
        SharePost sharePost = sharePostRepository.findByIdxWithMemberAndCategory(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.THIS_POST_NOT_EXIST));

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
                    .contents(sharePost.getContents())
                    .postIdx(sharePost.getIdx())
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
                    .contents(sharePost.getContents())
                    .postIdx(sharePost.getIdx())
                    .build();

            return sharePostReadRes;
        }
    }

    /**************나눔글 전체조회***************/
    public List<SharePostListRes> list(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "idx"));
        Slice<SharePost> posts = sharePostRepository.findAllPostWithMemberAndCategory(pageable);

//        List<SharePost> posts = sharePostRepository.findAll();
//        List<SharePost> posts = sharePostRepository.findAllPostWithMemberAndCategory();
        List<SharePostListRes> responses = new ArrayList<>();
        for (SharePost post : posts) {
            responses.add(
                    SharePostListRes.builder()
                            .title(post.getTitle())
                            .timeStamp(post.getTimeStamp())
                            .status(post.getStatus())
                            .postType(post.getPostType())
                            .postIdx(post.getIdx())
                            .category(post.getCategory().getName())
                            .btmCategory(post.getBtmCategory())
                            .currentEnrollment(post.getCurrentEnrollment())
                            .capacity(post.getCapacity())
                            .deadline(post.getDeadline())
                            .memberIdx(post.getMember().getIdx())
                            .build()
            );
        }

        return responses;
    }

    /****************내가 나눔글에 참여하기******************/
    public BaseResponse<String> enrollment(Member member, Long idx) throws BaseException {

      SharePost sharePost = sharePostRepository.findById(idx)
              .orElseThrow(() -> new BaseException(BaseResponseStatus.THIS_POST_NOT_EXIST));

        //작성자가 본인글의 나눔을 신청하려고 할 때 예외처리
        if(member.getIdx().equals(sharePost.getMember().getIdx())){
            throw new BaseException(BaseResponseStatus.UNABLE_MY_SHAREPOST);
        }

      PostMatchingMember now = postMatchingMemberRepository.findByMemberAndSharePost(member, sharePost)
              .orElseThrow(()->new BaseException(BaseResponseStatus.ALREADY_REQUEST));

      String response="";

        if(sharePost.getStatus()==true){
            throw new BaseException(BaseResponseStatus.CLOSED_POST);
        }

//          SharePost sharePost = result.get();
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
      return new BaseResponse<>(response);
    }

    public List<SharePostListRes> searchByCategory(Long categoryIdx) {
        List<SharePost> posts = sharePostRepository.findAllByCategory(Category.builder().idx(categoryIdx).build());
        List<SharePostListRes> responses = makeResponseList(posts);
        return responses;
    }

    public List<SharePostListRes> searchByKeyword(String keyword) {
        List<SharePost> posts = sharePostRepository.findAllByKeyword(keyword);
        List<SharePostListRes> responses = makeResponseList(posts);
        return responses;
    }

    private List<SharePostListRes> makeResponseList(List<SharePost> posts){
        List<SharePostListRes> responses = new ArrayList<>();
        for (SharePost post : posts) {
            responses.add(
                    SharePostListRes.builder()
                            .title(post.getTitle())
                            .timeStamp(post.getTimeStamp())
                            .status(post.getStatus())
                            .postType(post.getPostType())
                            .postIdx(post.getIdx())
                            .category(post.getCategory().getName())
                            .btmCategory(post.getBtmCategory())
                            .currentEnrollment(post.getCurrentEnrollment())
                            .capacity(post.getCapacity())
                            .deadline(post.getDeadline())
                            .memberIdx(post.getMember().getIdx())
                            .build()
            );
        }
        return responses;
    }
}
