package haust.vk.service;

import java.util.List;
import java.util.Map;

public interface FollowService {

	public void insertFollow(Map jsoninfo) throws Exception;

	public void deleteFollow(Map jsoninfo) throws Exception;

	public List<Map> selectFollowListByUserid(String tokenid) throws Exception;

	public List<Map> selectFollowNotifyByUserid(String tokenid) throws Exception;

}
