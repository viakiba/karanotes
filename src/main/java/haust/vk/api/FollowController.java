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

/**
 * 关注操作相关接口
 * @author viakiba
 *
 */
@RestController
@CrossOrigin(origins="*",maxAge=3600,methods={RequestMethod.GET, RequestMethod.POST})
public class FollowController {
	private static Logger logger = Logger.getLogger(FollowController.class);
	
	@Resource
	private FollowService followServiceImpl;
	
	/**
	 * 添加关注
	 * @Author : viakiba
	 * @param req
	 * @param resp
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/follow/insert",method=RequestMethod.POST)
	public ResultBody insertFollow(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		Map jsoninfo = (Map) req.getAttribute("jsoninfo");
		String tokenid = (String) jsoninfo.get("token_id");
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		Integer is_eachother = null;
		String follow_id = null;
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
			follow_id = followServiceImpl.insertFollow(jsoninfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		jsoninfo.clear();
		jsoninfo.put("follow_id", follow_id);
		return new ResultBody(jsoninfo);
	}
	
	/**
	 * 取消关注
	 * @Author : viakiba
	 * @param req
	 * @param resp
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/follow/delete",method=RequestMethod.POST)
	public ResultBody deleteFollow(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		Map jsoninfo = (Map) req.getAttribute("jsoninfo");
		
		String tokenid = (String) jsoninfo.get("token_id");
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		Integer is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother"));
		
		if("null".equals(tokenid) || "".equals(tokenid) || tokenid == null || "null".equals(is_eachother) || "".equals(is_eachother) || is_eachother == null || "null".equals(follow_userid) || "".equals(follow_userid) || follow_userid == null ){
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
	
	/**
	 * 用户关注通知详情/粉丝列表及其数量
	 * @Author : viakiba
	 * @param tokenid
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/extra/follownotifylist/{tokenid}")
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
		Map map = new HashMap<>();
		map.put("flsize", String.valueOf(list.size()));
		map.put("fanslist", list);
		return new ResultBody(map);
	}
	
	/**
	 * 新关注消息数提醒
	 * @Author : viakiba
	 * @param tokenid
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/extra/follownotify/{tokenid}",method=RequestMethod.GET)
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
		Map map = new HashMap<>();
		if(list == null){
			map.put("fnreadnum", "0");
		}else{
			map.put("fnreadnum", String.valueOf(list.size()));
		}
		return new ResultBody(map);
	}
	
	/**
	 * 用户的关注列表
	 * @Author : viakiba
	 * @param tokenid
	 * @param pagenum
	 * @param pagesize
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/extra/followlist/{tokenid}/{pagenum}/{pagesize}",method=RequestMethod.GET)
	public ResultBody selectFollowlist(@PathVariable String tokenid,@PathVariable String pagenum,@PathVariable String pagesize) throws GlobalErrorInfoException{
		if("".equals(tokenid) || "null".equals(tokenid) || tokenid == null || "".equals(pagesize) || "null".equals(pagesize) || pagesize == null || "".equals(pagenum) || "null".equals(pagenum) || pagenum == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Map map = new HashMap<>();
		map.put("token_id", tokenid);
		map.put("pagenum", pagenum);
		map.put("pagesize", pagesize);
		List<Map> list = null;
		try {
			list = followServiceImpl.getFollowList(map);
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
