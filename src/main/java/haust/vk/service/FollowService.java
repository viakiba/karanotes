package haust.vk.service;

import java.util.List;
import java.util.Map;

public interface FollowService {

	public String insertFollow(Map jsoninfo) throws Exception;

	public void deleteFollow(Map jsoninfo) throws Exception;

	public List<Map> selectFollowListByUserid(String tokenid) throws Exception;

	public List<Map> getFollowNotifyByUserid(String tokenid) throws Exception;

	public List<Map> getFollowList(Map tokenid) throws Exception;

}
