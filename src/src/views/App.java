package views;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class App extends Application {
	
	//Metodo para correr o programa
	public static void main(String[] args) {
		launch();
	}

	// Metodo para que a pagina aberta seja a do login
	@Override
	public void start(Stage stage) throws Exception {
		URL fxml = getClass().getResource("./login.fxml");
		Parent parent = FXMLLoader.load(fxml);
		stage.setTitle("IT Talents");
		stage.setScene(new Scene(parent));
		stage.show();
	}

}