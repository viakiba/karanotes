package haust.vk.api;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class CommentController {
	
	@Resource
	private CommentService commentServiceImpl;
	
	@RequestMapping(value="/comment/insert",method=RequestMethod.POST)
	public ResultBody insertComment(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map commentMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		commentMap.put("user_id", userinfo.getUser_id());
		try {
			commentMap = commentServiceImpl.insertComment(commentMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return  new ResultBody(commentMap);
	}
	
	@RequestMapping(value="/comment/delete",method=RequestMethod.POST)
	public ResultBody deleteComment(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map commentMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		commentMap.put("user_id", userinfo.getUser_id());
		try {
			commentMap = commentServiceImpl.deleteComment(commentMap);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/extra/comment/select/{articleid}",method=RequestMethod.GET)
	public ResultBody selectCommentByArticleid(@PathVariable String articleid) throws GlobalErrorInfoException{
		List<Map> list = null;
		try {
			list = commentServiceImpl.selectAllComment(articleid);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
	
	@RequestMapping(value="/extra/comment/notifinum/{tokenid}",method=RequestMethod.GET)
	public ResultBody selectCommentNotifinumByTokenid(@PathVariable String tokenid) throws GlobalErrorInfoException{
		int notifinum = 0;
		try {
			notifinum = commentServiceImpl.getNotifyCommentNum(tokenid);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		Map map = new HashMap<>();
		map.put("commentnotifynum", String.valueOf(notifinum));
		return new ResultBody(map);
	}
	
	@RequestMapping(value="/comment/notifi",method=RequestMethod.POST)
	public ResultBody selectCommentByTokenid(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map commentMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		commentMap.put("user_id", userinfo.getUser_id());
		System.out.println("*********************************");
		List<Map> list = null;
		try {
			list = commentServiceImpl.getNotifyComment(commentMap);
		}catch (GlobalErrorInfoException e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
}
