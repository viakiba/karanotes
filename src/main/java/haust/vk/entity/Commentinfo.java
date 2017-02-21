package haust.vk.entity;

import java.util.Date;

public class Commentinfo {
	private String comment_id;          
	private String user_id;              
	private String article_id;         
	private String comment_content;      
	private Date   comment_create_time;  
	private String reply_id;   
	
	public Commentinfo(){}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
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

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Date getComment_create_time() {
		return comment_create_time;
	}

	public void setComment_create_time(Date comment_create_time) {
		this.comment_create_time = comment_create_time;
	}

	public String getReply_id() {
		return reply_id;
	}

	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}

	@Override
	public String toString() {
		return "Commentinfo [comment_id=" + comment_id + ", user_id=" + user_id
				+ ", article_id=" + article_id + ", comment_content="
				+ comment_content + ", comment_create_time="
				+ comment_create_time + ", reply_id=" + reply_id + "]";
	}
	
}
