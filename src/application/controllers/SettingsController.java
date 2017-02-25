package application.controllers;

import application.AppController;
import application.Arzt;
import helpers.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController extends ViewController<AppController> {

	@FXML
	private Button savePass;
	@FXML
	private TextField lname;
	@FXML
	private TextField fname;
	@FXML
	private TextField gebiet;
	@FXML
	private TextField str;
	@FXML
	private TextField plz;
	@FXML
	private TextField ort;
	@FXML
	private PasswordField pass;
	@FXML
	private PasswordField pass2;

	String errClass = "error";
	String noErrClass = "fine";
	
	private Arzt arzt;
	private TextField[] nodes;
	private boolean passSave = false;
	
	
	@FXML
	public void initialize () {
		AppController.register("Settings", this);
		
		nodes = new TextField[]{null, lname, fname, gebiet, str, plz, ort};
		
		// Gleicher EventListener für beide Passwort felder
		for (PasswordField p: new PasswordField[]{pass, pass2}) {
			p.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        	if (pass2.getText().trim().equals(pass.getText().trim()) && pass.getText().trim().length() > 3) {
		            	passSave = true;
		            	savePass.getStyleClass().remove(errClass);
	            		savePass.getStyleClass().add(noErrClass);
		            } else if (passSave == true) {
	            		savePass.getStyleClass().add(errClass);
		            	savePass.getStyleClass().remove(noErrClass);
	            		passSave = false;
		            }
		        }
		    });
		}

	}
	
	
	
	@FXML 
	public void saveData () {
		arzt.setVorname(fname.getText().trim());
		arzt.setName(lname.getText().trim());
		arzt.setFachgebiet(gebiet.getText().trim());
		arzt.setStrasse(str.getText().trim());
		arzt.setPLZ(plz.getText().trim());
		arzt.setOrt(ort.getText().trim());
		
		mother.db().updateArzt(arzt);
		
		beforeRender();
	}
	
	@FXML 
	public void savePass () {
		if (passSave) {
			String encr = mother.db().sha256(pass.getText().trim());
			
			mother.db().updateArztPassword(encr);
			
			pass.setText("");
			pass2.setText("");
    		savePass.getStyleClass().add(errClass);
        	savePass.getStyleClass().remove(noErrClass);
    		passSave = false;
		}
	}
	
	
	
	
	
	@Override
	public void beforeRender() {
		arzt = mother.db().getArztData();
		
		String[] arztData = arzt.asArray();
		
		for (int i = 1; i < arztData.length; i++) {
			nodes[i].setText(arztData[i]);
		}

		pass.setText("");
		pass2.setText("");
		savePass.getStyleClass().add(errClass);
    	savePass.getStyleClass().remove(noErrClass);
		passSave = false;
		
		mother.setTitle("Einstellungen");
	}

	@Override
	public void beforeRemove() {
		// TODO Auto-generated method stub
		
	}

}
