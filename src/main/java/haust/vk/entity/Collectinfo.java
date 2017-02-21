package haust.vk.entity;

import java.util.Date;

public class Collectinfo {
	private String collect_id;          
	private String user_id;
	private String article_id;
	private Date collect_create_time;
	
	public Collectinfo(){}

	public String getCollect_id() {
		return collect_id;
	}

	public void setCollect_id(String collect_id) {
		this.collect_id = collect_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public Date getCollect_create_time() {
		return collect_create_time;
	}

	public void setCollect_create_time(Date collect_create_time) {
		this.collect_create_time = collect_create_time;
	}

	@Override
	public String toString() {
		return "Collectinfo [collect_id=" + collect_id + ", user_id=" + user_id
				+ ", article_id=" + article_id + ", collect_create_time="
				+ collect_create_time + "]";
	}
	
}
