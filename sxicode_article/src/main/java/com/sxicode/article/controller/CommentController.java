package com.sxicode.article.controller;

import com.sxicode.article.pojo.Comment;
import com.sxicode.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    //GET /comment 查询所有评论
    @RequestMapping(method = RequestMethod.GET)
    private Result findAll(){
        List<Comment> list = commentService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    //GET /comment/{commentId} 根据评论id查询评论数据
    @RequestMapping(value = "{commentId}",method = RequestMethod.GET)
    private Result findById(@PathVariable String commentId){
        Comment comment = commentService.findById(commentId);
        return new Result(true, StatusCode.OK, "查询成功", comment);
    }

    //GET /comment/article/{articleId} 根据文章id查询评论数据
    @RequestMapping(value = "article/{articleId}",method = RequestMethod.GET)
    private Result findByArticleId(@PathVariable String articleId){
        List<Comment> list = commentService.findByArticleId(articleId);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(method = RequestMethod.POST)
    private Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true,StatusCode.OK,"新增成功");
    }

    @RequestMapping(value = "{commentId}",method = RequestMethod.PUT)
    private Result updateById(@PathVariable String commentId,@RequestBody Comment comment){
        comment.set_id(commentId);
        commentService.updateById(comment);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "thumbup/{commentId}",method = RequestMethod.PUT)
    private Result updateById(@PathVariable String commentId){
        //从redis查询是否已经点赞
        //模拟用户id
        String userId = "111";
        Object flag = redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);
        if(flag == null){
            commentService.thumbup(commentId,1);
            redisTemplate.opsForValue().set("thumbup_" + userId + "_" + commentId,"true");
            return new Result(true,StatusCode.OK,"点赞成功");
        }else{
            commentService.thumbup(commentId,-1);
            redisTemplate.delete("thumbup_" + userId + "_" + commentId);
            return new Result(true,StatusCode.OK,"取消点赞成功");
        }

    }

    @RequestMapping(value = "{commentId}",method = RequestMethod.DELETE)
    private Result deleteById(@PathVariable String commentId){
        commentService.deleteById(commentId);
        return new Result(true, StatusCode.OK, "删除成功");
    }


}
