package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Articlepraise;

public interface PraiseDao {

	public void insertPraise(Map praiseinfo) throws Exception;

	public void deletePraise(Map praiseMap) throws Exception;
	
	public List<Map> selectPraiseNotifyByUserid(String userid) throws Exception;
	
	public List<Map> selectPraiseListByUserid(String userid) throws Exception;

	public int selectPraiseByUseridAndPraiseUserid(Map praiseMap) throws Exception;

	public int selectPraiseNotifyNumByUserid(String user_id) throws Exception;

	public List<Map> selectAllPraise(Map praiseMap) throws Exception;
}
