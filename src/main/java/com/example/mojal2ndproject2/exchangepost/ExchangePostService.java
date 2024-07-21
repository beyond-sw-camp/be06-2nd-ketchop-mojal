package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.category.CategoryRepository;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.PostMatchingMemberRepository;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ReadExchangePostRes;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.userhavecategory.UserHaveCategoryRepository;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ExchangePostService {
    private final ExchangePostRepository exchangePostRepository;
    private final PostMatchingMemberRepository postMatchingMemberRepository;
    private final CategoryRepository categoryRepository;
    private final UserHaveCategoryRepository userHaveCategoryRepository;

    /********************내가 작성한 교환글 전체조회*********************/
    public BaseResponse<List<ReadExchangePostRes>> authorExchangeList(Member member) {
//                List<ExchangePost> result = exchangePostRepository.findAllByMember(member);
        List<ExchangePost> result = exchangePostRepository.findAllByMemberWithMemberAndCategory(member);
        List<ReadExchangePostRes> exchangePostReadResList = new ArrayList<>();

        for (ExchangePost e : result) {
            ReadExchangePostRes exchangePostReadRes = ReadExchangePostRes.builder()
                    .postIdx(e.getIdx())
                    .title(e.getTitle())
                    .contents(e.getContents())
                    .timeStamp(e.getTimeStamp())
                    .modifyTime(e.getModifyTime())
                    .status(e.getStatus())
                    .postType(e.getPostType())
                    .memberIdx(e.getMember().getIdx())
                    .memberNickname(e.getMember().getNickname())
                    .giveCategory(e.getGiveCategory().getName())
                    .takeCategory(e.getTakeCategory().getName())
                    .giveBtmCategory(e.getGiveBtmCategory())
                    .takeBtmCategory(e.getTakeBtmCategory())
                    .build();
            exchangePostReadResList.add(exchangePostReadRes);
        }
        return new BaseResponse<>(exchangePostReadResList);
    }

/***************************내가 참여한 교환 게시글 전체조회**********************************/
    public BaseResponse<List<ReadExchangePostRes>> exchangeList(Member member) {

        List<ReadExchangePostRes> exchangePostReadResList = new ArrayList<>();
//        List<PostMatchingMember> posts = postMatchingMemberRepository.findAllByMemberAndSharePost(member, null);
        List<PostMatchingMember> posts = postMatchingMemberRepository.findAllByMemberWithExchangePostAndGiveCategoryAndTakeCategory(member);

            for (PostMatchingMember post : posts) {
                ReadExchangePostRes exchangePostReadRes = ReadExchangePostRes.builder()
                        .postIdx(post.getExchangePost().getIdx())
                        .title(post.getExchangePost().getTitle())
                        .memberIdx(post.getExchangePost().getMember().getIdx())
                        .memberNickname(post.getExchangePost().getMember().getNickname())
                        .giveCategory(post.getExchangePost().getGiveCategory().getName())
                        .giveBtmCategory(post.getExchangePost().getGiveBtmCategory())
                        .takeCategory(post.getExchangePost().getTakeCategory().getName())
                        .takeBtmCategory(post.getExchangePost().getTakeBtmCategory())
                        .timeStamp(post.getExchangePost().getTimeStamp())
                        .modifyTime(post.getExchangePost().getModifyTime())
                        .status(post.getExchangePost().getStatus())
                        .postType(post.getExchangePost().getPostType())
                        .build();
                exchangePostReadResList.add(exchangePostReadRes);
            }
        return new BaseResponse<>(exchangePostReadResList);
    }

    /*****************************교환게시글 생성*********************************/
    public CreateExchangePostRes create(CreateExchangePostReq req, Member member) throws BaseException {
        //회원가입시 선택한 카테고리가 없을때 예외처리
        List<UserHaveCategory> userHaveCategories = userHaveCategoryRepository.findAllByMember(member);
        if(userHaveCategories.size()==0){
            throw new BaseException(CHECK_CATEGORY_MORE_THAN_ONE);
        }

        //givecategory와 내 카테고리 불일치
        UserHaveCategory userHaveCategory = userHaveCategoryRepository.findByMemberAndCategory(member, Category.builder().idx(req.getGiveCategoryIdx()).build())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.GIVE_CATEGORY_NOT_IN_LIST));

        //회원이 없으면 예외처리
        if(member == null){
            throw new BaseException(BaseResponseStatus.MEMBER_NOT_LOGIN);
        }

        ExchangePost exchangePost = ExchangePost.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .postType("exchange")
                .giveCategory(Category.builder().idx(req.getGiveCategoryIdx()).build())
                .takeCategory(Category.builder().idx(req.getTakeCategoryIdx()).build())
                .giveBtmCategory(req.getGiveBtmCategory())
                .takeBtmCategory(req.getTakeBtmCategory())
                .timeStamp(LocalDateTime.now())
                .modifyTime(LocalDateTime.now())
                .member(member)
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
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "idx"));
        Slice<ExchangePost> posts = exchangePostRepository.findAllPostWithMemberAndGiveCategoryAndTakeCategory(pageable);

//        List<ExchangePost> result = exchangePostRepository.findAll();
//        List<ExchangePost> posts = exchangePostRepository.findAllPostWithMemberAndGiveCategoryAndTakeCategory();

        List<ReadExchangePostRes> getExchangePostReadList = new ArrayList<>();

        for (ExchangePost post: posts) {
            ReadExchangePostRes getReadRes = ReadExchangePostRes.builder()
                    .postIdx(post.getIdx())
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
                .postIdx(getExchangePost.getIdx())
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
