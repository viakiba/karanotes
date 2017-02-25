package haust.vk.entity;

import java.util.Date;

public class Articleabstract {
	private String article_id;
	private String article_title;
	private String article_show_img;
	private String classify_id;
	private String abstract_content;
	private String user_id;
	private int collect_num;
	private int read_num;
	private int praise_num;
	private String article_attachment;
	private Date article_create_time;
	private Date article_update_time;
	private String extra;
	
	public Articleabstract(){}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getArticle_title() {
		return article_title;
	}

	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	
	public void setArticle_show_img(String article_show_img) {
		this.article_show_img = article_show_img;
	}
	
	public String getArticle_show_img() {
		return article_show_img;
	}
	
	public String getClassify_id() {
		return classify_id;
	}

	public void setClassify_id(String classify_id) {
		this.classify_id = classify_id;
	}

	public String getAbstract_content() {
		return abstract_content;
	}

	public void setAbstract_content(String abstract_content) {
		this.abstract_content = abstract_content;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getCollect_num() {
		return collect_num;
	}

	public void setCollect_num(int collect_num) {
		this.collect_num = collect_num;
	}

	public int getRead_num() {
		return read_num;
	}

	public void setRead_num(int read_num) {
		this.read_num = read_num;
	}

	public int getPraise_num() {
		return praise_num;
	}

	public void setPraise_num(int praise_num) {
		this.praise_num = praise_num;
	}

	public String getArticle_attachment() {
		return article_attachment;
	}

	public void setArticle_attachment(String article_attachment) {
		this.article_attachment = article_attachment;
	}

	public Date getArticle_create_time() {
		return article_create_time;
	}

	public void setArticle_create_time(Date article_create_time) {
		this.article_create_time = article_create_time;
	}

	public Date getArticle_update_time() {
		return article_update_time;
	}

	public void setArticle_update_time(Date article_update_time) {
		this.article_update_time = article_update_time;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "Articleabstract [article_id=" + article_id + ", article_title="
				+ article_title + ", article_show_img=" + article_show_img
				+ ", classify_id=" + classify_id + ", abstract_content="
				+ abstract_content + ", user_id=" + user_id + ", collect_num="
				+ collect_num + ", read_num=" + read_num + ", praise_num="
				+ praise_num + ", article_attachment=" + article_attachment
				+ ", article_create_time=" + article_create_time
				+ ", article_update_time=" + article_update_time + ", extra="
				+ extra + "]";
	}
	
}
