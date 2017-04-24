package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Collectinfo;
import haust.vk.entity.Commentinfo;

public interface CollectDao {

	public void insertCollect(Map collectMap) throws Exception;

	public void deleteCollect(Map praiseMap) throws Exception;
	
	public int selectCollectNotifyByUserid(String userid) throws Exception;
	
	public List<Map> selectCollectListByUserid(String userid) throws Exception;

	public int selectIsCollect(Map collectMap) throws Exception;

	public List<Map> selectPraiseNotifi(Map collectMap) throws Exception;
	
}
