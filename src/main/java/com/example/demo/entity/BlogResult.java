package com.example.demo.entity;

import java.util.List;

public class BlogResult extends result<Blog>{

    public BlogResult(String status, String msg, Blog data) {
        super(status, msg, data);
    }
    public static BlogResult success(String status,String msg, Blog blog) {
        return new BlogResult(status, msg, blog);
    }

}
