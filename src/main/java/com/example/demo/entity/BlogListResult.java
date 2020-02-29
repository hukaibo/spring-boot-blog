package com.example.demo.entity;

import java.util.List;

public class BlogListResult extends result<List<Blog>> {
    private int total;
    private int pag;
    private int totalPage;
    public BlogListResult(String status,String msg){
        super(status,msg);

    }
    public BlogListResult(String status, String msg, List<Blog> data,int total,int pag,int totalPage) {
        super(status, msg, data);
        this.pag=pag;
        this.total=total;
        this.totalPage=totalPage;
    }
    public BlogListResult(String status,String msg,List<Blog> data){
        super(status,msg,data);
    }

    public int getTotal() {
        return total;
    }

    public int getPag() {
        return pag;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
