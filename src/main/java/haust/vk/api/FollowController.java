package haust.vk.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.FollowService;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class FollowController {
	private static Logger logger = Logger.getLogger(FollowController.class);
	
	@Resource
	private FollowService followServiceImpl;
	
	@RequestMapping(value="/follow/insert")
	public ResultBody insertFollow(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		Map jsoninfo = (Map) req.getAttribute("jsoninfo");
		String tokenid = (String) jsoninfo.get("token_id");
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		Integer is_eachother = null;
		try{	
			is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother") );
		}catch( NumberFormatException e){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		if("null".equals(tokenid) || "".equals(tokenid) || tokenid == null ||  "null".equals(is_eachother) || "".equals(is_eachother) || is_eachother == null || "null".equals(follow_userid) || "".equals(follow_userid) || follow_userid == null  ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		jsoninfo.put("user_id", userinfo.getUser_id());
		try {
			followServiceImpl.insertFollow(jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(new HashMap());
	}
	
	@RequestMapping(value="/follow/delete")
	public ResultBody deleteFollow(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		Map jsoninfo = (Map) req.getAttribute("jsoninfo");
		
		String tokenid = (String) jsoninfo.get("token_id");
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		String follow_id = (String) jsoninfo.get("follow_id");
		Integer is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother"));
		
		if("null".equals(tokenid) || "".equals(tokenid) || tokenid == null || "null".equals(follow_id) || "".equals(follow_id) || follow_id == null ||  "null".equals(is_eachother) || "".equals(is_eachother) || is_eachother == null || "null".equals(follow_userid) || "".equals(follow_userid) || follow_userid == null ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		jsoninfo.put("user_id", userinfo.getUser_id());
		try {
			followServiceImpl.deleteFollow(jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(new HashMap());
		
	}
	
	@RequestMapping(value="/extra/followlist/{tokenid}")
	public ResultBody selectFollowList(@PathVariable String tokenid) throws GlobalErrorInfoException{
		
		if("".equals(tokenid) || "null".equals(tokenid) || tokenid == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		List<Map> list = null;
		try {
			list = followServiceImpl.selectFollowListByUserid(tokenid);
		}catch (GlobalErrorInfoException e) {
			e.printStackTrace();
			logger.info(e);
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		
		return new ResultBody(list);
	}
	
	@RequestMapping(value="/extra/follownotify/{tokenid}")
	public ResultBody selectFollowNotify(@PathVariable String tokenid) throws GlobalErrorInfoException{
		if("".equals(tokenid) || "null".equals(tokenid) || tokenid == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		List<Map> list = null;
		try {
			list = followServiceImpl.getFollowNotifyByUserid(tokenid);
		} catch (GlobalErrorInfoException e) {
			e.printStackTrace();
			logger.info(e);
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
}
