import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Collection;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

	public static void main(String[] args) {
     launch(args);
}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
		GraphSocket connection = new GraphSocket();
        UDPClient client = new UDPClient(connection, arg0);
		arg0.show();
		
	}


}
