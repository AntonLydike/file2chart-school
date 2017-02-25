package application.controllers;

import java.io.File;
import java.util.List;

import application.AppController;
import helpers.ViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class DropController extends ViewController<AppController> {
	
	@FXML
	protected BorderPane Drop;
	
	private FileChooser fileChooser = new FileChooser();

	@FXML
	public void initialize () {	
		AppController.register("Drop", this);
		
		Drop.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != Drop
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
		
		Drop.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    
                	List<File> files = db.getFiles();
                	
                	String path = files.get(0).getAbsolutePath();
                	
                	mother.viewFile(path);
                	
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
		
	}
	
	@FXML
	public void fileDialog () {		
	    File file = fileChooser.showOpenDialog(mother.getStage());
	    if (file != null) {
	        mother.viewFile(file.getAbsolutePath());
	    }

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
