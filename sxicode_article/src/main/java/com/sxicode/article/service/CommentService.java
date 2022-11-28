package com.sxicode.article.service;

import com.sxicode.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.sxicode.article.repository.CommentRepository;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(String commentId) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void save(Comment comment){
        String id = idWorker.nextId()+"";//不推荐用自定id，最好还是用数据库自动生成的，但是对于大数据量可能导致的重复，可以加多一列c_id来存放指定的id
        comment.set_id(id);

        //初始化点赞数据，发布时间
        comment.setThumbup(0);
        comment.setPublishdate(new Date());
        //保存数据
        commentRepository.save(comment);
    }

    public void updateById(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> findByArticleId(String articleId) {
        return commentRepository.findByArticleid(articleId);
    }

    public void thumbup(String commentId,int i) {
        //根据评论id查询评论数据 不能保证线程安全
//        Optional<Comment> optional = commentRepository.findById(commentId);
//        if(optional.isPresent()){
//            Comment comment = optional.get();
//            comment.setThumbup(comment.getThumbup()+1);
//            commentRepository.save(comment);
//        }

        //优化

        //条件、修改的数值、集合名称
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        Update update = new Update();
        //自增长
        update.inc("thumbup",i);
        mongoTemplate.updateFirst(query,update,"comment");
    }
}
