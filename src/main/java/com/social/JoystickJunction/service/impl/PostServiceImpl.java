package com.social.JoystickJunction.service.impl;

import com.social.JoystickJunction.common.dto.request.PostUpdateRequest;
import com.social.JoystickJunction.common.dto.response.BaseResponse;
import com.social.JoystickJunction.exception.PostServiceException;
import com.social.JoystickJunction.models.Post;
import com.social.JoystickJunction.models.User;
import com.social.JoystickJunction.repository.PostRepository;
import com.social.JoystickJunction.repository.UserRepository;
import com.social.JoystickJunction.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BaseResponse createPost(Post postDetails, String userId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if (userDetails.isEmpty()) {
            throw new PostServiceException("User not found with user Id: " + userId, HttpStatus.NOT_FOUND);
        }

        Post newPost = new Post(userDetails.get());
        newPost.setDescription(postDetails.getDescription());
        newPost.setPicturePath(postDetails.getPicturePath());

        Post savedPost = postRepository.save(newPost);

        baseResponse.setMessage("Post created successfully");
        baseResponse.setPayload(savedPost);
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse fetchFeedPosts() {
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setMessage("Post fetched successfully");
        baseResponse.setPayload(postRepository.findAll());
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse fetchUserPosts(String userId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if (userDetails.isEmpty()) {
            throw new PostServiceException("User not found with user Id: " + userId, HttpStatus.NOT_FOUND);
        }

        baseResponse.setMessage("Post fetched successfully");
        baseResponse.setPayload(postRepository.findByUserId(userId));
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse updatePost(String userId, String postId, PostUpdateRequest postUpdateRequest) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if (userDetails.isEmpty()) {
            throw new PostServiceException("User not found with user Id: " + userId, HttpStatus.NOT_FOUND);
        }

        Optional<Post> postDetailsOpt = postRepository.findById(postId);
        if (postDetailsOpt.isEmpty()) {
            throw new PostServiceException("Post not found with post Id: " + postId, HttpStatus.NOT_FOUND);
        }

        Post postDetails = postDetailsOpt.get();
        if (!postDetails.getUserId().equals(userId)) {
            throw new PostServiceException("you are not authorized to update another user's post", HttpStatus.FORBIDDEN);
        }

        postDetails.setDescription(postUpdateRequest.getDescription());
        if (!Objects.isNull(postUpdateRequest.getPicturePath()) && !postUpdateRequest.getPicturePath().isEmpty()) {
            postDetails.setPicturePath(postUpdateRequest.getPicturePath());
        }

        postRepository.save(postDetails);

        baseResponse.setMessage("Post fetched successfully");
        baseResponse.setPayload(postDetails);
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse likePost(String userId, String postId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if (userDetails.isEmpty()) {
            throw new PostServiceException("User not found with user Id: " + userId, HttpStatus.NOT_FOUND);
        }

        Optional<Post> postDetailsOpt = postRepository.findById(postId);
        if (postDetailsOpt.isEmpty()) {
            throw new PostServiceException("Post not found with post Id: " + postId, HttpStatus.NOT_FOUND);
        }

        Post postDetails = postDetailsOpt.get();

        postDetails.getLikes().put(userId, true);

        postRepository.save(postDetails);

        baseResponse.setMessage("Post Liked successfully");
        baseResponse.setPayload(postDetails);
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse unlikePost(String userId, String postId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if (userDetails.isEmpty()) {
            throw new PostServiceException("User not found with user Id: " + userId, HttpStatus.NOT_FOUND);
        }

        Optional<Post> postDetailsOpt = postRepository.findById(postId);
        if (postDetailsOpt.isEmpty()) {
            throw new PostServiceException("Post not found with post Id: " + postId, HttpStatus.NOT_FOUND);
        }

        Post postDetails = postDetailsOpt.get();

        if (postDetails.getLikes().containsKey(userId)) {
            postDetails.getLikes().put(userId, false);
        }

        postRepository.save(postDetails);

        baseResponse.setMessage("Post Liked successfully");
        baseResponse.setPayload(postDetails);
        baseResponse.setSuccess(true);

        return baseResponse;
    }


}
