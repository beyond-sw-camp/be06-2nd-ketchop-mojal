package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ReadExchangePostRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchangepost")
public class ExchangePostController {
    private ExchangePostService exchangePostService;

    public ExchangePostController(ExchangePostService exchangePostService) {
        this.exchangePostService = exchangePostService;
    }

    //교환글생성
    @RequestMapping(method = RequestMethod.POST, name = "/create")
    public ResponseEntity<CreateExchangePostRes> create(@RequestBody CreateExchangePostReq req) {
        CreateExchangePostRes res = exchangePostService.create(req);
        return ResponseEntity.ok(res);
    }

    //교환글전체조회
    @RequestMapping(method = RequestMethod.GET, name = "/list")
    public ResponseEntity<List<ReadExchangePostRes>> list() {
        List<ReadExchangePostRes> res = exchangePostService.list();
        return ResponseEntity.ok(res);
    }


    //교환글해당글조회
    @RequestMapping(method = RequestMethod.GET, name = "/read")
    public ResponseEntity<ReadExchangePostRes> read(@RequestParam Long id) {
        ReadExchangePostRes res = exchangePostService.read(id);
        return ResponseEntity.ok(res);
    }

}