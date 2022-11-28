package com.sxicode.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sxicode.article.dao.ArticleDao;
import com.sxicode.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    public List<Article> findAll() {

        return articleDao.selectList(null);
    }

    public Article findById(String articleId) {
        return articleDao.selectById(articleId);
    }

    public Integer save(Article article) {
        article.setId(idWorker.nextId()+"");

        //初始化数据 --由于浏览量、点赞量、评论数都不是由文章作者决定的
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);

        return articleDao.insert(article);
    }

    public Integer updateById(Article article) {
        //根据主键修改
//        return articleDao.updateById(article);
        //根据条件修改
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("id",article.getId());
        return articleDao.update(article,wrapper);
    }

    public Integer deleteById(String articleId) {
        return articleDao.deleteById(articleId);
    }

    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            wrapper.eq(map.get(key) != null,key,map.get(key));
        }
        Page<Article> pageData = new Page<>(page, size);
        List<Article> articleList = articleDao.selectPage(pageData, wrapper);

        pageData.setRecords(articleList);
        return pageData;
    }
}
