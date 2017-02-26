package haust.vk.controller;

import haust.vk.service.ArticleService;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value="/article")
public class ArticleController {
	
	@Resource
	private ArticleService articleServiceImpl;
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public Map insertArticle(@RequestBody String article){
		System.out.println(article);
		Map articleMap = articleServiceImpl.insertArticle(article,"m");
		return articleMap;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public Map deleteArticle(@RequestBody String deleteinfo){
		System.out.println(deleteinfo);
		Map articleMap = articleServiceImpl.deleteArticle(deleteinfo);
		return articleMap;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map updateArticle(@RequestBody String deleteinfo){
		System.out.println(deleteinfo);
		Map articleMap = articleServiceImpl.updateArticle(deleteinfo,"m");
		return articleMap;
	}
}
