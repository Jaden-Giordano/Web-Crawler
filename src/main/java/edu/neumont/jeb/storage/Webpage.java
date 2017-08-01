package edu.neumont.jeb.storage;

public class Webpage implements IStorable {

	private String url;
	private String title;

	public Webpage() {

	}

	public Webpage(String url, String title) {
		this.setUrl(url);
		this.setTitle(title);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String serialize() {
		return String.format("%255s%255s", url, title);
	}

	@Override
	public void deserialize(String data) {
		String url = data.substring(0, 255).trim();
		String title = data.substring(255, 255 + 255).trim();

		this.setUrl(url);
		this.setTitle(title);
	}

	@Override
	public int sizeOf() {
		return 255 + 255;
	}

	@Override
	public String getKey() {
		return null;
	}
}
