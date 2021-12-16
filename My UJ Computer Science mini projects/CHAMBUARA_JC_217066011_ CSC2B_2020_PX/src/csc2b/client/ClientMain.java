package csc2b.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientMain extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ClientPane root = new ClientPane(primaryStage);
		
		Scene scene = new Scene(root,800,700);
		primaryStage.setTitle("UJ Facial Recognizer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}