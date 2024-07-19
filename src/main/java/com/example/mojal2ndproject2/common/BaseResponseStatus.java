package com.example.mojal2ndproject2.common;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : COURSE 에러
     */
    POST_COURSE_EMPTY_NAME(false, 2001, "코스의 이름을 입력해주세요."),
    POST_COURSE_EMPTY_PRICE(false, 2002, "코스의 가격을 입력해주세요."),
    POST_COURSE_EMPTY_DESCRIPTION(false, 2003, "코스의 설명을 입력해주세요."),

    POST_COURSE_PRE_EXIST_NAME(false, 2004, "이미 존재하는 코스 이름입니다. 다른 이름을 입력해주세요."),
    COURSE_LIST_NULL(false,2005,"등록된 코스가 존재하지 않습니다."),
    COURSE_NULL(false,2006,"등록된 코스가 존재하지 않습니다."),


    /**
     * 2200 : Login에러
     */
    MEMBER_NOT_LOGIN(false,2204,"로그인해주세요."),

    /**2100 : Login에러**/
    CHECK_CATEGORY_MORE_THAN_ONE(false,2101,"내 재능을 하나 이상 체크 해 주세요."),


    /**
     * 3000 : MEMBER 에러
     */
    POST_USERS_EMPTY_EMAIL(false, 3001, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 3002, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,3003,"중복된 이메일입니다."),
    POST_USERS_UNAUTH_EMAIL(false,3003,"이메일 인증 실패."),

    POST_USERS_EMPTY_NAME(false,3005,"이름을 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false,3006,"비밀번호를 입력해주세요."),
    POST_USERS_INVALID_USER_INFO(false,3007,"이메일 또는 패스워드를 확인해주세요."),


    /**
     * 4000 : 게시글 에러
     */
    TITLE_NOT_ENTERED(false, 4101, "제목이 입력되지 않았습니다"),
    TITLE_CHARACTER_LIMIT_OVER(false, 4102, "제목의 글자수 제한을 초과하였습니다"),
    CATEGORY_NOT_SELECTED(false, 4103, "재능의 상위 카테고리를 선택하지 않았습니다."),
    BTM_CATEGORY_CHARACTER_LIMIT_OVER(false, 4104, "하위 카테고리의 글자수 제한을 초과하였습니다."),
    CONTETNTS_NOT_ENTERED(false, 4105, "소개글이 입력되지 않았습니다."),
    CONTETNTS_CHARACTER_LIMIT_OVER(false, 4106, "소개글의 글자수 제한을 초과하였습니다."),
    NOT_SELECTED_POST_STATUS(false, 4107, "교환/나눔을 선택하지 않았습니다."),
    TAKE_CATEGORY_NOT_SELECTED(false, 4108, "받고싶은 재능의 상위 카테고리를 선택하지 않았습니다."),
    TIME_NOT_SET(false, 4109, "시간을 설정해주세요."),
    TIME_SET_IN_24HRS(false, 4110, "0~24시간 내에서만 설정 가능합니다."),
    NUM_OF_PEOPLE_NOT_SET(false, 4111, "모집 인원을 설정하지 않았습니다."),
    THIS_POST_NOT_EXIST(false, 4112, "해당 게시글이 존재하지 않습니다"),
    ALREADY_REQUEST(false, 4113, "이미 신청한 게시글입니다."),
    GIVE_CATEGORY_NOT_IN_LIST(false, 4114, "주고싶은 재능의 상위 카테고리가 범위를 벗어났습니다."),
    UNABLE_MY_SHAREPOST(false, 4115, "글 작성자는 본인의 나눔글에 참여할 수 없습니다."),


    /**
     * 5600 : 교환 상대 확정 취소
     */
    CHAT_NOT_WRITER(false, 5601, "글 작성자가 아닙니다"),
//    CHAT_NOT_EXCHANGEPOST(false, 5602, "나눔글은 교환대상을 확정할 수 없습니다."),
//    CHAT_EMPTY_MEMBER(false, 5603, "교환 상대가 존재하지 않습니다."),
//    CHAT_ALREADY_MMEMBER(false, 5604, "이미 확정된 교환 상대가 존재합니다."),
    CHAT_NOTFIND_CATE(false, 5605, "채팅 참여자가 선택한 카테고리 안에 교환 할 카테고리가 없습니다.");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
