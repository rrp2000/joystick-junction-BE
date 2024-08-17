package com.social.JoystickJunction.controller;

import com.social.JoystickJunction.common.dto.request.PostUpdateRequest;
import com.social.JoystickJunction.common.dto.response.BaseResponse;
import com.social.JoystickJunction.models.Post;
import com.social.JoystickJunction.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.social.JoystickJunction.common.constants.RouteConstants.*;
import static com.social.JoystickJunction.common.constants.RoutePathVariables.POST_ID;
import static com.social.JoystickJunction.common.constants.RoutePathVariables.USER_ID;

@RestController
@RequestMapping(POST_BASE_ROUTE)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(API + CREATE_POST + USER_ID_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> createPost(
            @RequestBody Post postDetails,
            @PathVariable(USER_ID) String userId
    ) {
        BaseResponse baseResponse = postService.createPost(postDetails, userId);
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @GetMapping(API)
    public ResponseEntity<BaseResponse> fetchFeedPosts() {
        BaseResponse baseResponse = postService.fetchFeedPosts();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping(API + USER_ID_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> fetchUserPosts(@PathVariable(USER_ID) String userId) {
        BaseResponse baseResponse = postService.fetchUserPosts(userId);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(API + UPDATE_POST + USER_ID_PATH_VARIABLE + POST_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> updatePost(
            @PathVariable(USER_ID) String userId,
            @PathVariable(POST_ID) String postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        BaseResponse baseResponse = postService.updatePost(userId, postId, postUpdateRequest);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(API + ADD_LIKE + USER_ID_PATH_VARIABLE + POST_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> likePost(
            @PathVariable(USER_ID) String userId,
            @PathVariable(POST_ID) String postId
    ) {
        BaseResponse baseResponse = postService.likePost(userId, postId);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(API + REMOVE_LIKE + USER_ID_PATH_VARIABLE + POST_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> unlikePost(
            @PathVariable(USER_ID) String userId,
            @PathVariable(POST_ID) String postId
    ) {
        BaseResponse baseResponse = postService.unlikePost(userId, postId);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
