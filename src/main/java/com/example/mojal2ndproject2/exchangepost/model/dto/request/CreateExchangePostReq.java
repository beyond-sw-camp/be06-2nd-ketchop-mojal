package com.example.mojal2ndproject2.exchangepost.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
@Getter
@Builder
@AllArgsConstructor
public class CreateExchangePostReq {
    private Long giveCategoryIdx;
    private Long takeCategoryIdx;
    private String giveBtmCategory;
    private String takeBtmCategory;
    private String title;
    private String contents;
    private String timeStamp;
    private String modifyTime;
    private Boolean status;
    private Boolean postType;
}
