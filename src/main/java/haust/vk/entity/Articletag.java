package haust.vk.entity;

public class Articletag {
	private String article_id;
    private String user_id;
    private String tag_content;
    
    public Articletag(){}

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

	public String getTag_content() {
		return tag_content;
	}

	public void setTag_content(String tag_content) {
		this.tag_content = tag_content;
	}

	@Override
	public String toString() {
		return "Articletag [article_id=" + article_id + ", user_id=" + user_id
				+ ", tag_content=" + tag_content + "]";
	}
}
