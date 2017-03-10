package haust.vk.rss;

import java.util.List;

public class Rssentity {
	private String title;
	private String generator;
	private String coding;
	private String link;
	private String language;
	private String description;
	private List<Itementity> items;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getCoding() {
		return coding;
	}
	public void setCoding(String coding) {
		this.coding = coding;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Itementity> getItems() {
		return items;
	}
	public void setItems(List<Itementity> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "Rssentity [title=" + title + ", generator=" + generator
				+ ", coding=" + coding + ", link=" + link + ", language="
				+ language + ", description=" + description + ", items="
				+ items + "]";
	}
}
