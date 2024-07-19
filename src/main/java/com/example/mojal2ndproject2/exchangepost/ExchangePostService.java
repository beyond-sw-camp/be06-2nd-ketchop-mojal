package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.PostMatchingMemberRepository;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ReadExchangePostRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ExchangePostService {
    private final ExchangePostRepository exchangePostRepository;
    private final PostMatchingMemberRepository postMatchingMemberRepository;
    private final CategoryRepository categoryRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;

    public BaseResponse<List<ReadExchangePostRes>> authorExchangeList(Long requestIdx) {
        Member member = Member.builder()
                .idx(requestIdx)
                .build();
        List<ExchangePost> result = exchangePostRepository.findAllByMember(member);
        List<ReadExchangePostRes> exchangePostReadResList = new ArrayList<>();
        for (ExchangePost e : result) {
            if (e.getMember().getIdx() == requestIdx) {
                ReadExchangePostRes exchangePostReadRes = ReadExchangePostRes.builder()
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
        return new BaseResponse<>(exchangePostReadResList);
    }

    public BaseResponse<List<ReadExchangePostRes>> exchangeList(Member member) {

        List<ReadExchangePostRes> exchangePostReadResList = new ArrayList<>();
//        List<PostMatchingMember> postMatchingMemberList = postMatchingMemberRepository.findAllByMember(member);
        List<ExchangePost> exchangePosts = exchangePostRepository.findAllByMemberWithMatchingMemberAndGiveCategoryAndTakeCategory(member);

            for (ExchangePost e : exchangePosts) {
                ReadExchangePostRes exchangePostReadRes = ReadExchangePostRes.builder()
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
                        .giveCategory(e.getGiveCategory().getName())
                        .takeCategory(e.getTakeCategory().getName())
                        .build();
                exchangePostReadResList.add(exchangePostReadRes);
            }
//        for (PostMatchingMember p : postMatchingMemberList ) {
//            if (p.getExchangePost() != null) {
//                ReadExchangePostRes exchangePostReadRes = ReadExchangePostRes.builder()
//                        .idx(p.getExchangePost().getIdx())
//                        .title(p.getExchangePost().getTitle())
//                        .timeStamp(p.getExchangePost().getTimeStamp())
//                        .modifyTime(p.getExchangePost().getModifyTime())
//                        .status(p.getExchangePost().getStatus())
//                        .postType(p.getExchangePost().getPostType())
//                        .memberIdx(p.getMember().getIdx())
//                        .memberNickname(p.getMember().getNickname())
//                        .giveBtmCategory(p.getExchangePost().getGiveBtmCategory())
//                        .takeBtmCategory(p.getExchangePost().getTakeBtmCategory())
//                        .giveCategory(p.getExchangePost().getGiveCategory().getName())
//                        .takeCategory(p.getExchangePost().getTakeCategory().getName())
//                        .build();
//                exchangePostReadResList.add(exchangePostReadRes);
//            }
//        }
        return new BaseResponse<>(exchangePostReadResList);
    }
    //교환게시글 생성
    public CreateExchangePostRes create(CreateExchangePostReq req, CustomUserDetails customUserDetails) throws BaseException {
        LocalDateTime createdAt = LocalDateTime.now();

        Category newGiveCategory = Category.builder()
                .idx(req.getGiveCategoryIdx())
                .build();
        Category newTakeCategory = Category.builder()
                .idx(req.getTakeCategoryIdx())
                .build();

        Long memberIdx = customUserDetails.getMember().getIdx();
        Member newMember = Member.builder()
                .idx(memberIdx)
                .build();

        //회원가입시 선택한 카테고리가 없을때 예외처리
        UserHaveCategory userHaveCategory = userHaveCategoryRepository.findById(req.getGiveCategoryIdx()).orElseThrow(
                () -> new BaseException(CHECK_CATEGORY_MORE_THAN_ONE)
        );

        //givecategory가 내가 선택한 카테고리 범위에 없으면 에러처리
        //즉, 해당카테고리가 없으면 예외처리
        Category resultCategory = categoryRepository.findById(req.getGiveCategoryIdx()).orElseThrow(
                () -> new BaseException(GIVE_CATEGORY_NOT_IN_LIST)
        );

        if(resultCategory == null){
            throw new BaseException(GIVE_CATEGORY_NOT_IN_LIST);
        }

        //회원이 없으면 예외처리
        if(customUserDetails.getMember() == null){
            throw new BaseException(BaseResponseStatus.MEMBER_NOT_LOGIN);
        }




        ExchangePost exchangePost = ExchangePost.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .postType("exchange")
                .giveCategory(newGiveCategory)
                .takeCategory(newTakeCategory)
                .giveBtmCategory(req.getGiveBtmCategory())
                .takeBtmCategory(req.getTakeBtmCategory())
                .timeStamp(createdAt)
                .modifyTime(createdAt)
                .member(newMember)
                .status(false)
                .build();
        exchangePostRepository.save(exchangePost);


        return CreateExchangePostRes.builder()
                .idx(exchangePost.getIdx())
                .title(req.getTitle())
                .content(req.getContents())
                .build();
    }

    //교환게시글 전체조회
    public List<ReadExchangePostRes> list() throws BaseException{
        List<ExchangePost> result = exchangePostRepository.findAllPostWithMemberAndGiveCategoryAndTakeCategory();

        List<ReadExchangePostRes> getExchangePostReadList = new ArrayList<>();

        for (ExchangePost post: result) {
            ReadExchangePostRes getReadRes = ReadExchangePostRes.builder()
                    .idx(post.getIdx())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .postType(post.getPostType())
                    .giveCategory(post.getGiveCategory().getName())
                    .takeCategory(post.getTakeCategory().getName())
                    .giveBtmCategory(post.getGiveBtmCategory())
                    .takeBtmCategory(post.getTakeBtmCategory())
                    .timeStamp(post.getTimeStamp())
                    .modifyTime(post.getModifyTime())
                    .memberIdx(post.getMember().getIdx())
                    .status(post.getStatus())
                    .build();
            getExchangePostReadList.add(getReadRes);
        }

        return getExchangePostReadList;
    }

    //교환해당게시글 조회
    public ReadExchangePostRes read(Long idx) throws BaseException {
        ExchangePost getExchangePost = exchangePostRepository.findPostByIdxWithMemberAndGiveCategoryAAndTakeCategory(idx).orElseThrow(
                () -> new BaseException(THIS_POST_NOT_EXIST)
        );

//        ExchangePost getExchangePost = result.get();
        ReadExchangePostRes getExchangePostRes = ReadExchangePostRes.builder()
                .idx(getExchangePost.getIdx())
                .title(getExchangePost.getTitle())
                .contents(getExchangePost.getContents())
                .postType(getExchangePost.getPostType())
                .giveCategory(getExchangePost.getGiveCategory().getName())
                .takeCategory(getExchangePost.getTakeCategory().getName())
                .giveBtmCategory(getExchangePost.getGiveBtmCategory())
                .takeBtmCategory(getExchangePost.getTakeBtmCategory())
                .timeStamp(getExchangePost.getTimeStamp())
                .modifyTime(getExchangePost.getModifyTime())
                .memberIdx(getExchangePost.getMember().getIdx())
                .status(getExchangePost.getStatus())
                .build();

        return getExchangePostRes;
    }

}
