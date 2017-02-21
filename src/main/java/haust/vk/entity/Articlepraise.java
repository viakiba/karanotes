package haust.vk.entity;

import java.util.Date;

public class Articlepraise {
	private String praise_id;
	private String article_id;
	private String user_id;
	private String praise_user_id;
	private Date praise_create_time;
	
	public Articlepraise(){}

	public String getPraise_id() {
		return praise_id;
	}

	public void setPraise_id(String praise_id) {
		this.praise_id = praise_id;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPraise_user_id() {
		return praise_user_id;
	}

	public void setPraise_user_id(String praise_user_id) {
		this.praise_user_id = praise_user_id;
	}

	public Date getPraise_create_time() {
		return praise_create_time;
	}

	public void setPraise_create_time(Date praise_create_time) {
		this.praise_create_time = praise_create_time;
	}

	@Override
	public String toString() {
		return "Articlepraise [praise_id=" + praise_id + ", article_id="
				+ article_id + ", user_id=" + user_id + ", praise_user_id="
				+ praise_user_id + ", praise_create_time=" + praise_create_time
				+ "]";
	}
	
}
