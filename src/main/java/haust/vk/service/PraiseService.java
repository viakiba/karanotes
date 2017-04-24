package haust.vk.service;

import java.util.List;
import java.util.Map;

public interface PraiseService {

	int insertPraise(Map praiseMap) throws Exception;

	void deletePraise(Map praiseMap) throws Exception;

	int getPraiseNotifiNum(String tokenid) throws Exception;

	List<Map> getPraisenifify(Map praiseMap) throws Exception;

}
