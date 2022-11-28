package com.sxicode.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.sxicode.article.pojo.Article;
import com.sxicode.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //GET /article/{articleId}
    @RequestMapping(value = "{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId){
        Article article = articleService.findById(articleId);
        return new Result(true,StatusCode.OK,"查询成功",article);
    }

    //GET /article 文章全部列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    //POST /article 增加文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        Integer i = articleService.save(article);
        if(i > 0){
            return new Result(true,StatusCode.OK,"保存成功");
        }
        return new Result(false,StatusCode.ERROR,"保存失败");
    }

    //PUT /article/{articleId} 修改文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String articleId,@RequestBody Article article){
        article.setId(articleId);
        Integer i = articleService.updateById(article);
        if(i > 0){
            return new Result(true,StatusCode.OK,"修改成功");
        }
        return new Result(false,StatusCode.ERROR,"修改失败");
    }

    //DELETE /article/{articleId} 根据ID删除文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId){
        int i = articleService.deleteById(articleId);
        if(i > 0){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    //POST /article/search/{page}/{size} 文章分页
    //之前接受文章数据采用的是pojo，但是现在根据条件查询，需要遍历pojo中的所有属性（需要用反射，成本高，性能低）
    //直接使用map来接收数据会更方便
    @RequestMapping(value = "search/{page}/{size}",method = RequestMethod.POST)
    public Result findByPage(@PathVariable Integer page,@PathVariable Integer size,@RequestBody Map<String,Object> map){
        Page<Article> pageData = articleService.findByPage(map,page,size);

        PageResult<Article> pageResult = new PageResult<>(pageData.getTotal(),pageData.getRecords());

        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
