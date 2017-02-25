package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

public class AdvancedListView<T> {
	public ObservableList<ListItem<T>> list = FXCollections.observableArrayList();
	public ListView<ListItem<T>> view = new ListView<ListItem<T>>(list);
	
	public AdvancedListView () {		
		view.setCellFactory(new Callback<ListView<ListItem<T>>, ListCell<ListItem<T>>>(){
            @Override
            public ListCell<ListItem<T>> call(ListView<ListItem<T>> p) {
                
                ListCell<ListItem<T>> cell = new ListCell<ListItem<T>>(){

                    @Override
                    protected void updateItem(ListItem<T> t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getContent().toString());
                        } else {
                        	setText("");
                        }
                    }
                };
                
                return cell;
            }
        });
	}
	
	public void add (String id, T content) {
		list.add(new ListItem<T>(id, content));
	}
	
	public void add (String[] ids, T[] stuff) {
		if (stuff.length != ids.length) {
			// shit
		}
		
		for (int i = 0; i < stuff.length; i++) {
			add(ids[i], stuff[i]);
		}
	}
}
