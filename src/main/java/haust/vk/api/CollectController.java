package haust.vk.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.CollectService;

@RestController
@CrossOrigin(origins="*",maxAge=3600,methods={RequestMethod.GET, RequestMethod.POST})
public class CollectController {
	
	@Resource
	private CollectService collectServiceImpl;
	/**
	 * 添加收藏
	 * @Author : viakiba
	 * @param req
	 * @param resp
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/collect/insert",method=RequestMethod.POST)
	public ResultBody insertCollect(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map collectMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		collectMap.put("user_id", userinfo.getUser_id());
		try{
			collectMap = collectServiceImpl.insertCollect(collectMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(collectMap);
	}
	
	/**
	 * 取消收藏
	 * @Author : viakiba
	 * @param req
	 * @param resp
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/collect/delete",method=RequestMethod.POST)
	public ResultBody deleteCollect(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map collectMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		collectMap.put("user_id", userinfo.getUser_id());
		try{
			collectServiceImpl.deleteCollect(collectMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	/**
	 * 用户收藏文章 作者通知 新 数量
	 * @Author : viakiba
	 * @param tokenid
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/extra/collect/selectnum/{tokenid}",method=RequestMethod.GET)
	public ResultBody selectCollectNotifyNum(@PathVariable String tokenid) throws GlobalErrorInfoException{
		int num = 0;
		try{
			num = collectServiceImpl.getPraiseNotifiNum(tokenid);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		Map map = new HashMap<>();
		map.put("collectnum", String.valueOf(num));
		return new ResultBody(map);
	}
	
	/**
	 * 收藏通知  分页
	 * @Author : viakiba
	 * @param req
	 * @param resp
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/collect/notify",method=RequestMethod.POST)
	public ResultBody selectNotify(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map collectMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		collectMap.put("user_id", userinfo.getUser_id());
		List<Map> list = null;
		try{
			list = collectServiceImpl.getCollectNotify(collectMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
	
	/**
	 * 收藏列表  分页
	 * @Author : viakiba
	 * @param req
	 * @param resp
	 * @return
	 * @throws GlobalErrorInfoException
	 * 2017-04-27
	 */
	@RequestMapping(value="/collect/notifyuser",method=RequestMethod.POST)
	public ResultBody selectByTokenid(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map collectMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		collectMap.put("user_id", userinfo.getUser_id());
		List<Map> list = null;
		try{
			list = collectServiceImpl.selectCollectList(collectMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
	
}
