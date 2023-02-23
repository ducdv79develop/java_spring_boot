package com.local.ducdv.api;

import com.local.ducdv.dto.ApiResponseDto;
import com.local.ducdv.dto.ApiResponseValidationDto;
import com.local.ducdv.entity.Comment;
import com.local.ducdv.entity.CommentExample;
import com.local.ducdv.mapper.CommentMapper;
import com.local.ducdv.util.ResponseStatusCode;
import com.local.ducdv.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentApiController {

    @Autowired
    CommentMapper commentMapper;
    @GetMapping("/lists")
    public ResponseEntity<?> listComment() {

        List<Comment> comments = commentMapper.selectByExample(new CommentExample());
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", comments.toArray());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    @PostMapping("/add-new")
    public ResponseEntity<?> addNewComment(@RequestBody Comment comment, BindingResult result) {
        int commentNew = commentMapper.insertSelective(comment);
        Object[] data = {commentNew};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment, BindingResult result) {

        if (comment.getId() == null) {
            result.addError(new ObjectError("id", "id is required"));
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        int status = commentMapper.updateByPrimaryKey(comment);
        Object[] data = {status};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody Comment comment, BindingResult result) {

        if (comment.getId() == null) {
            result.addError(new ObjectError("id", "id is required"));
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        int status = commentMapper.deleteByPrimaryKey(comment.getId());
        Object[] data = {status};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
