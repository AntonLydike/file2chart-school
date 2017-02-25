package application.controllers;


import application.AppController;
import application.Patient;
import helpers.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddPatController extends ViewController<AppController> {
	
	@FXML private TextField name;
	@FXML private TextField vorname;
	@FXML private DatePicker gebdat;
	@FXML private TextField straße;
	@FXML private TextField plz;
	@FXML private TextField ort; 
	
	@FXML 
	public void initialize () {
		AppController.register("AddPat", this);
		
		
	}
	
	@FXML public void addPat (ActionEvent e) {		
		boolean pass = true;
		
		for (TextField n: new TextField[]{name, vorname, straße, plz, ort}) {
			if (n.getText().trim().equals("")) {
				n.getStyleClass().add("error");
				pass = false;
			}
		}
		
		if (gebdat.getValue() == null) {
			return;
		}
		
		if (!pass) return;
		
		Patient p = new Patient(
				name.getText().trim(), 
				vorname.getText().trim(), 
				gebdat.getValue().toString(), 
				straße.getText().trim(), 
				plz.getText().trim(), 
				ort.getText().trim());
		
		String patID = String.valueOf(mother.db().addPat(p));
		
		mother.setCurrPat(patID);
		
		mother.go("PatDetail");
	}
	
	@Override
	public void beforeRender() {
		// TODO Auto-generated method stub

		
		mother.setTitle("Patient hinzufügen");
		
	}

	@Override
	public void beforeRemove() {
		// TODO Auto-generated method stub
		
	}

}
