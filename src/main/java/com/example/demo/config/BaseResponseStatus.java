package com.example.demo.config;

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
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_CODE(false, 2004, "유효하지 않은 코드입니다."),

    // [GET] /messages
    GET_MESSAGES_EMPTY_PHONENUM(false, 2010, "수신번호를 입력해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EMPTY_ID(false, 2018, "아이디를 입력해주세요."),
    POST_USERS_INVALID_ID(false, 2019, "아이디 규칙에 맞지 않습니다. 영문/숫자를 포함하여 5자~11자로 구성해주세요."),
    POST_USERS_EXISTS_ID(false,2020,"중복된 아이디입니다."),
    POST_USERS_EMPTY_NAME(false, 2021, "이름을 입력해주세요."),
    POST_USERS_EMPTY_NICKNAME(false, 2022, "닉네임을 입력해주세요."),
    POST_USERS_EXISTS_NICKNAME(false,2023,"중복된 닉네임입니다."),
    POST_USERS_EMPTY_PASSWORD(false, 2024, "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD(false, 2025, "비밀번호 규칙에 맞지 않습니다. 영문/숫자/특수문자를 포함하여 8자~20자로 구성해주세요."),
    POST_USERS_EMPTY_PHONENUM(false, 2026, "핸드폰번호를 입력해주세요."),
    POST_USERS_EXISTS_PHONENUM(false,2027,"중복된 핸드폰번호입니다."),
    POST_USERS_INVALID_NICKNAME(false, 2028, "닉네임 규칙에 맞지 않습니다. 3자~8자로 구성해주세요."),
    ONLY_BUY_SELL(false,2030,"buy나 sell만 입력해주세요"),

    // [GET] /products
    NOT_PRODUCTS_EXISTS_CATEGORY(false, 2100, "없는 카테고리 입니다"),
    NOT_PRODUCTS_EXISTS_BRANDNAME(false, 2101, "없는 브랜드 입니다"),
    NOT_PRODUCTS_EXISTS_PRODUTID(false, 2102, "올바르지 않는 productId 입니다"),
    NOT_PRODUCTS_EXISTS_TYPES(false, 2103, "올바르지 않는 거래방법 입니다"),

    // [GET] /orderhistory
    NOT_ORDERHISTORY_EXISTS_ORDERID(false, 2200, "올바르지 않는 orderId 입니다"),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    //[GET] /users
    USERS_EMPTY_USERID(false, 3010, "존재하지 않는 회원입니다."),

    //[GET] /megazines
    MAGAZINES_EMPTY_TYPE(false, 3011, "존재하지 않는 타입입니다."),
    MAGAZINES_EMPTY_ID(false, 3012, "존재하지 않는 magazine_id 입니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    USERS_STATUS_DELETE(false,3015,"존재하지 않는 회원입니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    FAIL_CHANGE_USER(false,4009,"회원 정보 변경 실패"),
    FAIL_DELETE_USER(false,4010,"회원 탈퇴 실패"),


    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    FAIL_DELETE_ADDRESS(false,4015,"주소 삭제 실패"),
    FAIL_CHANGE_PASSWORD(false,4016,"비밀번호 변경 실패");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
