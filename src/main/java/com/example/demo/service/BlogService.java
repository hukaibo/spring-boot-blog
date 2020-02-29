package com.example.demo.service;

import com.example.demo.dao.BlogDao;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogListResult;
import com.example.demo.entity.BlogResult;
import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;


    @Inject
    public BlogService(BlogDao blogDao,UserService userService) {
        this.blogDao = blogDao;
        this.userService=userService;
    }


    public BlogListResult getBlogs(Integer page, Integer pagesize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pagesize, userId);
            int count = blogDao.count(userId);
            int pagecount = count % pagesize == 0 ? count / pagesize : count / pagesize + 1;
            return new BlogListResult("ok", "获取成功", blogs, count, page, pagecount);
        } catch (Exception e) {
            e.printStackTrace();
            return new BlogListResult("fail", "系统异常", null, 0, 0, 0);
        }
    }

    public List<Blog> getNewBlog(String title, String content, String description, Integer id) {
        List<Blog> blogs = blogDao.newBlogs(title, content, description, id);
        return blogs;

    }

    public BlogListResult getBlogById(Integer blogId) {
        try {
            List<Blog> blogById = blogDao.getBlogById(blogId);
            return new BlogListResult("ok", "获取成功", blogById);
        } catch (Exception e) {
            e.printStackTrace();
            return new BlogListResult("fail", "系统异常");
        }
    }
    public BlogListResult updateBlog(Integer blogId,String title,String content,String description){
        Blog blogs = blogDao.updateBlog(blogId);
        if (blogs==null){
             return new BlogListResult("fail","博客不存在");
        }
        if (!Objects.equals(blogId,blogs.getId())){
           return new BlogListResult("fail","无法修改别人的博客");
        }
         List<Blog> blogs1= blogDao.iwanttoUpdate(blogId, title, content, description);
        return new BlogListResult("ok","修改成功",blogs1);

    }
    public BlogListResult deleteblog(int blogid){
         blogDao.deleteBlog(blogid);
         return new BlogListResult("ok","删除成功");
    }
}
