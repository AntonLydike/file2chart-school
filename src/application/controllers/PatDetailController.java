package application.controllers;

import application.AppController;
import application.Impfung;
import application.ImpfungPatient;
import helpers.AdvancedList;
import helpers.AdvancedListView;
import helpers.ListItem;
import helpers.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PatDetailController extends ViewController<AppController> {
	
	private AdvancedListView<ImpfungPatient> userList = new AdvancedListView<>();
	private AdvancedList<Impfung> list;
	
	@FXML 
	private ChoiceBox<String> impfungenDropDown;
	@FXML 
	private VBox listContainer;
	@FXML 
	private Label datum;
	@FXML 
	private Label impfung;
	@FXML 
	private Label impfstoff;
	@FXML 
	private Label hersteller;
	@FXML 
	private Label haltbarkeit;
	
	@FXML public void initialize () {
		AppController.register("PatDetail", this);
		
		listContainer.getChildren().add(userList.view);

		userList.view.setId("userList");
		userList.view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListItem<ImpfungPatient>>() {
			@Override public void changed (ObservableValue<? extends ListItem<ImpfungPatient>> observable, ListItem<ImpfungPatient> oldObj, ListItem<ImpfungPatient> n) {
				
				if (n != null) {
					ImpfungPatient i = n.getContent();
					
					datum.setText(i.getDatum());	
					impfung.setText(i.getImpfung_bezeichnung());	
					impfstoff.setText(i.getImpfstoff_bezeichnung());	
					hersteller.setText(i.getImpfstoff_hersteller());	
					haltbarkeit.setText(i.getGültig_jahre() + " Jahre");
				} else {
					
				}
			}
		});
		
		VBox.setVgrow(userList.view, Priority.ALWAYS);
	}
	
	@FXML public void newImpfung () {
		System.out.println(mother.getCurrPat());
		
		// Im DropDown Ausgewählte Impfung
		ListItem<Impfung> item = list.get(impfungenDropDown.getSelectionModel().getSelectedIndex());
		
		mother.db().addImpfung(mother.getCurrPat(), item.getId());
		
		beforeRender();
	}
	@Override
	public void beforeRender() {		
		AdvancedList<ImpfungPatient> res = mother.db().getImpfungen(mother.getCurrPat());
		userList.list.clear();
		userList.list.addAll(res);
		
		mother.setTitle(mother.db().getPatName(mother.getCurrPat()));
		
		ObservableList<String> items= impfungenDropDown.getItems();
		items.clear();
		
		list = mother.db().getImpfungen();
		
		for (ListItem<Impfung> i: list) {
			items.add(i.getContent().toString());
		}
	}

	@Override
	public void beforeRemove() {
		// TODO Auto-generated method stub
		
	}

}
