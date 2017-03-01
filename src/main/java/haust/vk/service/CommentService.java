package haust.vk.service;

import java.util.Map;

public interface CommentService {
	
	/**
	 * 添加文章评论
	 * @param commentMap
	 * @return
	 * @throws Exception
	 */
	public Map insertComment(Map commentMap) throws Exception;
	
	/**
	 * 删除评论
	 * @param commentMap
	 * @return
	 * @throws Exception
	 */
	public Map deleteComment(Map commentMap)  throws Exception;
	
	
	
	
}
