package helpers;

import java.util.ArrayList;

public class AdvancedList<T> extends ArrayList<ListItem<T>> {

	private static final long serialVersionUID = 1L;

	public ListItem<T> getById(String id) {
		for (ListItem<T> item: this) {
			if (item.getId().equals(id)) {
				return item;
			}
		}

		return null;
	}
	
	public boolean contains(String id) {
		return indexOf(id) > -1;
	}
	
	public int indexOf(String id) {
		int s = this.size();

		for (int i = 0; i < s; i++) {
			if (this.get(i).getId().equals(id)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void add (String id, T obj) {
		this.add(new ListItem<T>(id, obj));
	}
}
