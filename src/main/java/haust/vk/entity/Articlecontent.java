package haust.vk.entity;

public class Articlecontent {
	private String article_id;
	private String article_content;
	
	public Articlecontent() {}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getArticle_content() {
		return article_content;
	}

	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}

	@Override
	public String toString() {
		return "Articlecontent [article_id=" + article_id
				+ ", article_content=" + article_content + "]";
	}
	
}
