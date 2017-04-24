package haust.vk.service;

import java.util.List;
import java.util.Map;

public interface CollectService {

	Map insertCollect(Map collectMap) throws Exception;

	void deleteCollect(Map praiseMap) throws Exception;

	int getPraiseNotifiNum(String tokenid) throws Exception;

	List<Map> selectCollectList(Map collectMap) throws Exception;

	List<Map> selectCollectNotify(Map collectMap) throws Exception;

}
