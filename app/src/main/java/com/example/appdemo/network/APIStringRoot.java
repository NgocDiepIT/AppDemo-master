package com.example.appdemo.network;

public class APIStringRoot {

    static final String API_ROOT = "https://hubbook.herokuapp.com";

    static final String LOGIN = "/api/user/login";

    static final String REGISTER = "/api/user/register";

    static final String HEADER = "Content-Type: application/json; charset=UTF-8";

    static final String GET_ALL_POST = "/api/post/get-all";

    static final String CREATE_POST = "/api/post";

    static final String LIKE_POST = "/api/post/like";

    static final String GET_ALL_FRIEND = "/api/user/get-all";

    static final String UPDATE_STATUS = "/api/post/{postId}";

    static final String DELETE_STATUS = "/api/post/{postId}";

    static final String GET_ALL_COMMENT = "/api/post/get-comment";

    static final String GET_PROFILE = "/api/user/get-detail";

    static final String CREATE_COMMENT = "/api/post/comment";
}
