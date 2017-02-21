package haust.vk.entity;

import java.util.Date;

public class Followinfo {
	private String follow_id;            
	private String user_id;              
	private String follow_user_id;       
	private String is_eachother;        
	private Date follow_create_time;
	
	public Followinfo(){}

	public String getFollow_id() {
		return follow_id;
	}

	public void setFollow_id(String follow_id) {
		this.follow_id = follow_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFollow_user_id() {
		return follow_user_id;
	}

	public void setFollow_user_id(String follow_user_id) {
		this.follow_user_id = follow_user_id;
	}

	public String getIs_eachother() {
		return is_eachother;
	}

	public void setIs_eachother(String is_eachother) {
		this.is_eachother = is_eachother;
	}

	public Date getFollow_create_time() {
		return follow_create_time;
	}

	public void setFollow_create_time(Date follow_create_time) {
		this.follow_create_time = follow_create_time;
	}

	@Override
	public String toString() {
		return "Followinfo [follow_id=" + follow_id + ", user_id=" + user_id
				+ ", follow_user_id=" + follow_user_id + ", is_eachother="
				+ is_eachother + ", follow_create_time=" + follow_create_time
				+ "]";
	}
}
