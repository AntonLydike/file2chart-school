package application;

import application.controllers.FileViewController;
import helpers.ViewController;
import helpers.ViewsHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppController {

	private static ViewsHandler<AppController> views = new ViewsHandler<>();
 
	private static FileViewController fileView;
	
	private static Stage stage;
	
	@FXML private StackPane yield;
	
	@FXML private Label err;

	@FXML
	public void initialize() {
		views.init(yield, .3, this);
		
        views.switchTo("Drop");
	}
	
	public void viewFile (String path) {
		String ret = fileView.setPath(path);
		
		if (ret == null || ret.startsWith("Warn:")) {
			views.switchTo("View");	
			err.setText(ret);
			
		} else {
			err.setText(ret);
		}		
	}
		
	public static void setStage (Stage st) {
		stage = st;
	}
	
	public Stage getStage () {
		return stage;
	}

	
	public static void registerFileView (FileViewController fileView_) {
		fileView = fileView_;
	}
	
	public void go (String id) {
		err.setText("");
		
		views.switchTo(id);
	}
	
	public static void register (String id, ViewController<AppController> ctrl) {
		views.registerController(id, ctrl);
	}
}
