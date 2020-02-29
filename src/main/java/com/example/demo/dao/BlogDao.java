package com.example.demo.dao;

import com.example.demo.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {
    private final SqlSession sqlSession;
    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pagesize, Integer userId) {
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("user_id",userId);
        parameters.put("offset",(page-1)*pagesize);
        parameters.put("limit",pagesize);
        return sqlSession.selectList("selectBlog",parameters);
    }

    public int count(Integer userId) {
        return sqlSession.selectOne("countBlog",userId);
    }
   public List<Blog> getBlogById(Integer id){
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("id",id);
        return sqlSession.selectList("selectBlogById",parameter);

   }
  public List<Blog> newBlogs(String title, String content, String description, Integer id){
        Map<String,Object> param=new HashMap<>();
        param.put("userId",id);
        param.put("title",title);
        param.put("content",content);
        param.put("description",description);
        sqlSession.insert("insertBlog",param);
        return selectBlogById(id);

  }

    public List<Blog> selectBlogById(Integer id) {
       return sqlSession.selectList("selectBlogById",id);
    }
    public Blog updateBlog(Integer bolgid){
        return sqlSession.selectOne("updateBlog",bolgid);
    }
    public List<Blog> iwanttoUpdate(Integer id,String title,String content,String description){
        Map<String,Object> param=new HashMap<>();
        param.put("title",title);
        param.put("content",content);
        param.put("description",description);
        param.put("id",id);
        sqlSession.update("iwanttoupdate",param);
      return null;
    }

    public void deleteBlog(int blogid) {
         Map<String,Object> param=new HashMap<>();
         param.put("id",blogid);
         sqlSession.delete("deleteBlog",param);
    }
}
