package com.example.mojal2ndproject2.search.dto.response;

import com.example.mojal2ndproject2.exchangepost.model.dto.response.ExchangePostListRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostListRes;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchRes {
    private List<SharePostListRes> sharePosts = new ArrayList<>();
    private List<ExchangePostListRes> exchangePosts = new ArrayList<>();

    @Builder
    public SearchRes(List<SharePostListRes> sharePosts, List<ExchangePostListRes> exchangePosts){
        this.sharePosts = sharePosts;
        this.exchangePosts = exchangePosts;
    }
}
