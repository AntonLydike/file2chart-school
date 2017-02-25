package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
		String css = getClass().getResource("css/style.css").toExternalForm(); 

		Font.loadFont(getClass().getResource("fonts/OpenSans-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("fonts/OpenSans-Light.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
		
        Scene scene = new Scene(root, 1000, 800);
		scene.getStylesheets().add(css);
		
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("File2Graph");
        stage.setScene(scene);
        stage.show();
        
        AppController.setStage(stage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
