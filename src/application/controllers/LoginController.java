package application.controllers;

import application.AppController;
import helpers.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LoginController extends ViewController<AppController> {
	
	private boolean failed = false;
	
	@FXML private PasswordField pass;
	
	@FXML private TextField user;
	
	@FXML private Label error;
	
	private HBox buttonBox;

	@FXML
	public void initialize () {	
		AppController.register("Login", this);
		
		user.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                user.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	}
	
	@FXML protected void login (ActionEvent e) {		
		if (user.getText().length() == 0 || pass.getText().trim().length() == 0) {
			failed = true;
		} else {
			String uid = user.getText();
			String pw = mother.db().sha256(pass.getText().trim());
			failed = !mother.db().login(uid, pw);	
		}	
		
		if (!failed) {
			mother.go("Home");
			buttonBox.setVisible(true);
			pass.setText("");
			user.setText("");
		}

		error.setVisible(failed);
	}
	
	@FXML public void logout (ActionEvent e) {
		mother.go("Login");
		buttonBox.setVisible(false);
	}
	
	public void setButtonBox (HBox box) {
		buttonBox = box;
	}
	
	
	@Override
	public void beforeRender() {
		// TODO Auto-generated method stub
	}
	@Override
	public void beforeRemove() {
		// TODO Auto-generated method stub
	}
}
