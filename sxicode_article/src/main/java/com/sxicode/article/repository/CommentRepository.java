package com.sxicode.article.repository;

import com.sxicode.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {
    //SpringDataMongoDB 支持通过查询方法名进行自定义查询

    //根据发布时间和点赞数进行查询
//    List<Comment> findByPublishdateAndThumbup(Date date,Integer thumbup);

    //根据用户id查询
//    List<Comment> findByUseridOrderByPublishdateDesc(String userid);

    //根据文章id查询文章评论数据
    List<Comment> findByArticleid(String articleId);
}
