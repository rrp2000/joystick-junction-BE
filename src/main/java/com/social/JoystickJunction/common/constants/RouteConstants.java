package com.social.JoystickJunction.common.constants;

import static com.social.JoystickJunction.common.constants.RoutePathVariables.*;

public class RouteConstants {
    //Authenticated routes
    public static final String API = "/api";
    public static final String USER_ID_PATH_VARIABLE = "/" + USER_ID_PARAM;
    public static final String FRIEND_USER_ID_PATH_VARIABLE = "/" + FRIEND_USER_ID_PARAM;
    public static final String POST_PATH_VARIABLE = "/" + POST_ID_PARAM;


    //User routes
    public static final String USER_BASE_ROUTE = "/user";
    public static final String SIGNUP = "/signup";
    public static final String LOGIN = "/login";
    public static final String ADD = "/add";
    public static final String REMOVE = "/remove";


    //Post routes
    public static final String POST_BASE_ROUTE = "/post";
    public static final String CREATE_POST = "/create";
    public static final String UPDATE_POST = "/update";
    public static final String ADD_LIKE = "/add/like";
    public static final String REMOVE_LIKE = "/remove/like";
}
