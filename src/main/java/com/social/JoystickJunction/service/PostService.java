package com.social.JoystickJunction.service;

import com.social.JoystickJunction.common.dto.request.PostDto;
import com.social.JoystickJunction.common.dto.request.PostUpdateRequest;
import com.social.JoystickJunction.common.dto.response.BaseResponse;
import com.social.JoystickJunction.models.Post;

public interface PostService {
    BaseResponse createPost(PostDto postDetails);

    BaseResponse fetchFeedPosts();

    BaseResponse fetchUserPosts(String userId);

    BaseResponse updatePost(String userId, String postId, PostUpdateRequest postUpdateRequest);

    BaseResponse likePost(String userId, String postId);

    BaseResponse unlikePost(String userId, String postId);

}
