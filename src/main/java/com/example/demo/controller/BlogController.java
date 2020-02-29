package com.example.demo.controller;


import com.example.demo.dao.BlogDao;
import com.example.demo.entity.*;
import com.example.demo.service.BlogService;
import com.example.demo.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {


    private BlogService blogService;

    private UserService userService;

    @Inject
    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @GetMapping(value = "/blog", produces = "application/json;charset=utf-8")
    @ResponseBody
    public BlogListResult getBlogs(@RequestParam("page") Integer page,
                                   @RequestParam(value = "userId", required = false) Integer userid) {
        if (page == null || page < 0) {
            page = 1;
        }
        return blogService.getBlogs(page, 10, userid);
    }

    @GetMapping(value = "/blog/{blogId}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public BlogListResult getBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/blog")
    @ResponseBody
    public BlogListResult newBlog(@RequestBody Map<String, String> param) {
        String title = param.get("title");
        String content = param.get("content");
        String description = param.get("description");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new BlogListResult("fail", "登录后才能操作");
        }
        User loguer = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        Integer id = loguer.getId();
        List<Blog> newBlog = blogService.getNewBlog(title, content, description, id);

        return new BlogListResult("ok", "创建成功", newBlog);
    }

    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public BlogListResult updateBlog(@PathVariable("blogId") int blogId, @RequestBody Map<String, String> param) {
        String title = param.get("title");
        String content = param.get("content");
        String description = param.get("description");

        return blogService.updateBlog(blogId,title,content,description);


    }
    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public BlogListResult deleteBlog(@PathVariable("blogId") int blogid){
        return blogService.deleteblog(blogid);
    }

}
