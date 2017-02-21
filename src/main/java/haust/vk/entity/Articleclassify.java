package haust.vk.entity;

public class Articleclassify {
	private String classify_id;
	private String user_id;
	private String classify_content;
	
	public Articleclassify(){}

	public String getClassify_id() {
		return classify_id;
	}

	public void setClassify_id(String classify_id) {
		this.classify_id = classify_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getClassify_content() {
		return classify_content;
	}

	public void setClassify_content(String classify_content) {
		this.classify_content = classify_content;
	}

	@Override
	public String toString() {
		return "Articleclassify [classify_id=" + classify_id + ", user_id="
				+ user_id + ", classify_content=" + classify_content + "]";
	}
	
}
