package com.local.ducdv.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.local.ducdv.dto.ApiResponseDto;
import com.local.ducdv.dto.ApiResponseValidationDto;
import com.local.ducdv.entity.Post;
import com.local.ducdv.entity.PostExample;
import com.local.ducdv.mapper.PostMapper;
import com.local.ducdv.util.ResponseStatusCode;
import com.local.ducdv.util.ValidationUtils;

@RestController
@RequestMapping("/api/post")
public class PostApiController {

    @Autowired
    PostMapper postMapper;
    @GetMapping("/lists")
//    @AspectTrackTime
    public ResponseEntity<?> listPost() {

        List<Post> posts = postMapper.joinPostComment(new PostExample());
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", posts.toArray());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    @PostMapping("/add-new")
    public ResponseEntity<?> addNewPost(@RequestBody Post post, BindingResult result) {
        int postNew = postMapper.insertSelective(post);
        Object[] data = {postNew};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody Post post, BindingResult result) {

        if (post.getId() == null) {
            result.addError(new ObjectError("id", "id is required"));
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        int status = postMapper.updateByPrimaryKey(post);
        Object[] data = {status};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody Post post, BindingResult result) {

        if (post.getId() == null) {
            result.addError(new ObjectError("id", "id is required"));
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        int status = postMapper.deleteByPrimaryKey(post.getId());
        Object[] data = {status};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
