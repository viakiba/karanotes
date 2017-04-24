package haust.vk.service;

import java.util.List;
import java.util.Map;

public interface CommentService {
	
	public Map insertComment(Map commentMap) throws Exception;
	
	public Map deleteComment(Map commentMap) throws Exception;

	public List<Map> selectAllComment(String articleid) throws Exception;

	public int getNotifyCommentNum(String tokenid) throws Exception;

	public List<Map> getNotifyComment(Map commentMap) throws Exception;
	
}
