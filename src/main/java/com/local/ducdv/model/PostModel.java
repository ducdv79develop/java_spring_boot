package com.local.ducdv.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostModel {
    public Integer id;
    public String title;
    public String content;
    public String author;

    public PostModel(Integer id, String title, String content, String author) {
        setId(id);
        setTitle(title);
        setContent(content);
        setAuthor(author);
    }
}
