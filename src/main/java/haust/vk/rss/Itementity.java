package haust.vk.rss;

public class Itementity {
	private String title;
	private String link;
	private String description;
	private String source;
	private String pubDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	@Override
	public String toString() {
		return "Itementity [title=" + title + ", link=" + link
				+ ", description=" + description + ", source=" + source
				+ ", pubDate=" + pubDate + "]";
	}
	
	
}
