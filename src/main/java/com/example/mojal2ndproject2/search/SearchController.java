package com.example.mojal2ndproject2.search;

import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.exchangepost.ExchangePostService;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ExchangePostListRes;
import com.example.mojal2ndproject2.search.dto.response.SearchRes;
import com.example.mojal2ndproject2.sharePost.SharePostService;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostListRes;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SharePostService sharePostService;
    private final ExchangePostService exchangePostService;

    @Operation(summary = "키워드 검색")
    @RequestMapping(method = RequestMethod.GET, value = "/keyword")
    public BaseResponse<SearchRes> searchByKeyword(String keyword) {
        List<SharePostListRes> sharePosts = sharePostService.searchByKeyword(keyword);
        List<ExchangePostListRes> exchangePosts = exchangePostService.searchByKeyword(keyword);
        SearchRes searchRes = SearchRes.builder()
                .sharePosts(sharePosts).exchangePosts(exchangePosts).build();
        return new BaseResponse<>(searchRes);
    }

    @Operation(summary = "카테고리 검색")
    @RequestMapping(method = RequestMethod.GET, value = "/category")
    public BaseResponse<SearchRes> search(Long idx){
        List<SharePostListRes> sharePosts = sharePostService.searchByCategory(idx);
        List<ExchangePostListRes> exchangePosts = exchangePostService.searchByCategory(idx);
        SearchRes searchRes = SearchRes.builder()
                .sharePosts(sharePosts).exchangePosts(exchangePosts).build();
        return new BaseResponse<>(searchRes);
    }
}
