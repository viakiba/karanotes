package haust.vk.service;

import java.util.Map;

public interface ClassifyService {
	
	/**
	 * 插入
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map insertClassify(Map map) throws Exception;
	
	/**
	 * 删除
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map deleteClassify(Map map) throws Exception;
	
	/**
	 * 更新
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map updateClassify(Map map) throws Exception;
	
	/**
	 * 获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map getallClassify(Map map) throws Exception;
}
