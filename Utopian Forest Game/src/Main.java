import csc2a.ui.GamePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import sun.misc.GC;

/**
 * Entry Point for the JavaFX Application
 * @author <YOUR DETAILS HERE>
 *
 */
public class Main extends Application{
	
	/**
	 * Main entry point for the program
	 * @param args Command line arguments
	 */
	GamePane gp = null;
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Default start method provided by the JavaFX Application
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

        gp = new GamePane();
        Scene scene = new Scene(gp,Color.LIGHTBLUE);
        gp.setPrefWidth(scene.getWidth());
        gp.setPrefHeight(scene.getHeight());
       
        primaryStage.setTitle("MyGame");
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
