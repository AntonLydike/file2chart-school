package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import examination.core.*;
import examination.reader.*;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			ReaderResult res = ExamReader.read("Langzeitblutdruck_PID34567.txt");
			
			if (res.success()) {
				System.out.println(res.getLinesRead());
				System.out.println(res.getLinesSkipped());
				System.out.println(res.getResult().getPatient().getFname());
			} else {

				System.out.println(res.getError());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
