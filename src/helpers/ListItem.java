package helpers;

public class ListItem<T> {

	private String id;
	private T content;

	public ListItem(String id, T content) {		
		this.id = id;
		this.content = content;
	}
	public ListItem() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
}
