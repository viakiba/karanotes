package haust.vk.entity;

import java.util.Date;

public class Faqinfo {
	private String faq_id;             
	private String user_id;            
	private String faq_content;          
	private Date faq_create_time;    
	
	public Faqinfo(){}

	public String getFaq_id() {
		return faq_id;
	}

	public void setFaq_id(String faq_id) {
		this.faq_id = faq_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFaq_content() {
		return faq_content;
	}

	public void setFaq_content(String faq_content) {
		this.faq_content = faq_content;
	}

	public Date getFaq_create_time() {
		return faq_create_time;
	}

	public void setFaq_create_time(Date faq_create_time) {
		this.faq_create_time = faq_create_time;
	}

	@Override
	public String toString() {
		return "Faqinfo [faq_id=" + faq_id + ", user_id=" + user_id
				+ ", faq_content=" + faq_content + ", faq_create_time="
				+ faq_create_time + "]";
	}
	
}
