package haust.vk.entity;

import java.util.Date;

public class Notifiinfo {
	private String user_id;              
	private Date comment_read_time;  
	private Date praise_read_time;     
	private Date collect_read_time;   
	private Date follow_read_time;
	
	public Notifiinfo(){}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getComment_read_time() {
		return comment_read_time;
	}

	public void setComment_read_time(Date comment_read_time) {
		this.comment_read_time = comment_read_time;
	}

	public Date getPraise_read_time() {
		return praise_read_time;
	}

	public void setPraise_read_time(Date praise_read_time) {
		this.praise_read_time = praise_read_time;
	}

	public Date getCollect_read_time() {
		return collect_read_time;
	}

	public void setCollect_read_time(Date collect_read_time) {
		this.collect_read_time = collect_read_time;
	}

	public Date getFollow_read_time() {
		return follow_read_time;
	}

	public void setFollow_read_time(Date follow_read_time) {
		this.follow_read_time = follow_read_time;
	}

	@Override
	public String toString() {
		return "Notifiinfo [user_id=" + user_id + ", comment_read_time="
				+ comment_read_time + ", praise_read_time=" + praise_read_time
				+ ", collect_read_time=" + collect_read_time
				+ ", follow_read_time=" + follow_read_time + "]";
	}
	
}
