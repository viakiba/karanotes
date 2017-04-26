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
import haust.vk.service.PraiseService;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class PraiseController {
	
	@Resource
	private PraiseService praiseServiceImpl;
	
	@RequestMapping(value="/praise/insert",method=RequestMethod.POST)
	public ResultBody insertPraise(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map praiseMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		praiseMap.put("user_id", userinfo.getUser_id());
		int i = 0;
		try{
			i = praiseServiceImpl.insertPraise(praiseMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		praiseMap.clear();
		praiseMap.put("operate", i);
		return new ResultBody(praiseMap);
	}
	
	@RequestMapping(value="/praise/delete",method=RequestMethod.POST)
	public ResultBody deletePraise(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map praiseMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		praiseMap.put("user_id", userinfo.getUser_id());
		try{
			praiseServiceImpl.deletePraise(praiseMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/extra/praise/selectnum/{tokenid}",method=RequestMethod.GET)
	public ResultBody selectPraiseNotifyNum(@PathVariable String tokenid) throws GlobalErrorInfoException{
		int num = 0;
		try{
			num = praiseServiceImpl.getPraiseNotifiNum(tokenid);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		Map map = new HashMap<>();
		map.put("praisenotifinum", String.valueOf(num));
		return new ResultBody(map);
	}
	
	@RequestMapping(value="/praise/notify",method=RequestMethod.POST)
	public ResultBody selectPraiseNotify(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map praiseMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		praiseMap.put("user_id", userinfo.getUser_id());
		List<Map> list = null;
		try{
			list = praiseServiceImpl.getPraisenifify(praiseMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
}
