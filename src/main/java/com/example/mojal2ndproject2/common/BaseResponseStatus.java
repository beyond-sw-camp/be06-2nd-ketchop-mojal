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
     * 2200 : Login에러
     */
    MEMBER_NOT_LOGIN(false,2204,"로그인해주세요."),

    /**2100 : Login에러**/
    CHECK_CATEGORY_MORE_THAN_ONE(false,2101,"내 재능을 하나 이상 체크 해 주세요."),


    /**
     * 3000 : MEMBER 에러
     */
    POST_USERS_EXISTS_EMAIL(false,3003,"중복된 이메일입니다."),
    POST_USERS_UNAUTH_EMAIL(false,3004,"이메일 인증 실패."),

    /**
     * 4000 : 게시글 에러
     */

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