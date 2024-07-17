package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import org.springframework.stereotype.Service;

@Service
public class ExchangePostService {
    private ExchangePostRepository exchangePostRepository;

    public ExchangePostService(ExchangePostRepository exchangePostRepository) {
        this.exchangePostRepository = exchangePostRepository;
    }

    public CreateExchangePostRes create(CreateExchangePostReq req){
        //db에 저장하기
        //request받기
        //req-교환글작성req
        ExchangePost exchangePost = new ExchangePost();
        exchangePost.setContents(req.getContents());
        exchangePost.setPostType(req.getPostType());
        exchangePost.setTitle(req.getTitle());
        exchangePost.setGiveBtmCategory(req.getGiveBtmCategory());
        exchangePost.setTakeBtmCategory(req.getTakeBtmCategory());
        //exchangePost.setTimeStamp();//실시간


    }


}
