
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class UDPClient<T extends Block> extends GridPane{
	private static final String RSA_ALGORITHM = "RSA";
	
	private ArrayListQueue<Block> blockChain;
	private Button btnConnect;
	private Button btnSwitch;
	private Button btnFiilRecord;
	private Button btnHostConnection;
	private Button btnVetandUpadate;
	private DatagramSocket connection;
	private Label lblHost;
	private Label lblPort;
	private Graph networkGraph;
	
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private int serverPort = 1234;
	private InetAddress severAddr;
	private GridPane p2pGridPane;
	private TextField txtHost;
	private TextField txtPort;
	private TextArea tArea;
	
	
public UDPClient(DatagramSocket connection,Stage arg0) {
	
	Collection<UDPClient> clients = new  ArrayList<UDPClient>();
	clients.add(this);
    Collection<DatagramSocket> connections = new  ArrayList<DatagramSocket>();
    this.connection = connection;
    connections.add(this.connection);
    //networkGraph = new Graph(clients, connections);
    
	blockChain = new ArrayListQueue<>();
	arg0.setTitle("P2P UDPClient");
	p2pGridPane = createPane();
	p2pGridPane.setMaxHeight(arg0.getMaxHeight());
	p2pGridPane.setMaxWidth(arg0.getMaxWidth());
	
	
	setUIControls(connection,p2pGridPane,arg0);
	VBox vbox = new VBox();
	vbox.setMaxHeight(arg0.getMaxHeight());
	vbox.setMaxWidth(arg0.getMaxWidth());
	vbox.setSpacing(10);
	vbox.setPadding(new Insets(10, 10, 10,10));
	vbox.getChildren().addAll(p2pGridPane);
	Scene scene = new Scene(vbox, arg0.getMaxWidth() ,arg0.getMaxHeight());
	arg0.setScene(scene);
	
	KeyPair keypair;
	try {
		keypair = generateRSAKkeyPair();
		this.privateKey = keypair.getPrivate();
		this.publicKey = keypair.getPublic();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	//arg0.show();
	
	
}
	@SuppressWarnings("unchecked")
	public void addBlockToBlockChain(T t) {
        
        @SuppressWarnings("unused")
		String newBPrevHash = t.getPrevioushashCode();
        newBPrevHash = blockChain.getPreviousElement(t).getCurrenHashCode();
        t.mineBlock(5);
        blockChain.enqueue(t);
		
		
	}
//	https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/
	// Generating public and private keys
	// using RSA algorithm.
	public static KeyPair generateRSAKkeyPair()
	    throws Exception
	{
	    SecureRandom secureRandom
	        = new SecureRandom();
	
	    KeyPairGenerator keyPairGenerator
	        = KeyPairGenerator.getInstance(RSA_ALGORITHM);
	
	    keyPairGenerator.initialize(
	        2048, secureRandom);
	
	    return keyPairGenerator
	        .generateKeyPair();
	}
	//https://stackoverflow.com/questions/31915617/how-to-encrypt-string-with-public-key-and-decrypt-with-private-key
	 public static byte[] encrypteBlock(Block block, PrivateKey Key) throws Exception  {
	        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	        PublicKey publicKey = KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(Key.toString().getBytes())));
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        
	        byte[] encryptedbytes = cipher.doFinal(new StringBuilder().append(block).toString().getBytes());
	        return encryptedbytes;
	    }
	 public static byte[] decrypteBlock(Block block, PublicKey Key) throws Exception {
	        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	        PrivateKey pk = KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(Key.toString().getBytes())));
	        cipher.init(Cipher.DECRYPT_MODE, pk);
	        byte[] dencryptedbytes = cipher.doFinal(Base64.getDecoder().decode((new StringBuilder().append(block).toString().getBytes())));
	        return dencryptedbytes;
	    }
		
	private GridPane createPane() {

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(15, 15, 15, 15));
		gridPane.setStyle("-fx-background-color:lightblue;-fx-border-width:15;"
				+ "-fx-border-color:silver;-fx-font-weight:bold;-fx-font-size:15;");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		return gridPane;
		
	}
	
	public Boolean isChainValid(int difficulty) {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockChain.size(); i++) {
			currentBlock =blockChain.get(i);
			previousBlock = blockChain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getCurrenHashCode().equals(currentBlock.calculateBlockHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getCurrenHashCode().equals(currentBlock.getPrevioushashCode()) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.getCurrenHashCode().substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
	
	
	private String sendTextMessage(String request,DatagramSocket dSocket) {
		String response ="";
		try {
			byte[] buffer = request.getBytes();
			tArea.appendText("Sending request: "+request);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,severAddr,serverPort);
			dSocket.send(packet);
			buffer = new byte[1024];
			packet = new DatagramPacket(buffer, buffer.length);
			dSocket.receive(packet);
			response = new String(buffer).trim();
			
		} catch (IOException io) {
			// TODO: handle exception
			io.printStackTrace();
		}
		
		return response;
	}
	
	private void setUIControls(DatagramSocket dSocket, GridPane p2pGridPane ,Stage arg0) {
		
		Label theaderLabel = new Label("Start connection Hotspot for other peers to connect:");
		theaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		btnHostConnection = new Button("Host Connection");
		lblHost = new Label("Host:");
		lblPort = new Label("Port:");
		txtHost = new TextField();
		txtPort = new TextField();
		tArea = new TextArea();
		btnConnect = new Button("Connect to Peer(s)");
		btnFiilRecord = new Button("Fill in the Smart Contract Record and add it to the block");
		btnVetandUpadate = new Button("Vet block and Update block chain state");
		
		Label lheaderLabel = new Label(" UDP Peer");
		lheaderLabel.setFont(Font.font("Ariel", FontWeight.BOLD, 40));
		p2pGridPane.add(btnHostConnection, 0, 2);
		p2pGridPane.add(lheaderLabel, 0, 1,1,1);
		p2pGridPane.add(lblHost, 0, 3);
		p2pGridPane.add(txtHost, 1, 3);
		p2pGridPane.add(lblPort, 0, 4);
		p2pGridPane.add(txtPort, 1, 4);
		p2pGridPane.add(theaderLabel, 0, 5);
		
		p2pGridPane.add(btnConnect, 0, 6);
		p2pGridPane.add(btnFiilRecord, 0, 7);
		p2pGridPane.add(btnVetandUpadate, 0, 8);
		p2pGridPane.add(tArea, 0, 9);
		
		
		
		
		
		
	
		btnConnect.setOnAction((event)->{
			try {
				severAddr = InetAddress.getByName(txtHost.getText());
				serverPort = Integer.parseInt(txtPort.getText());
				tArea.appendText("Started on port: "+ dSocket.getLocalPort());
				tArea.appendText("Connected to "+severAddr);

				String response = sendTextMessage("CONNECT",dSocket);
				tArea.appendText("Response:"+response);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		
		
		
		btnHostConnection.setOnAction((event)->{
			
			try {
				 severAddr = InetAddress.getLocalHost();
				 DatagramSocket dSocket2 = new DatagramSocket(serverPort);
				 tArea.appendText("Started on port: "+dSocket2.getLocalPort());
				 tArea.appendText("Connection has been set up.Ready for other peers to connect to the network..");
				 while (true) {
					 byte[] buffer = new byte[1024];
					 DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
					 dSocket2.receive(packet);
					 String request = new String(buffer).trim();
					 if (request.toLowerCase().startsWith("connect")) {
						 
						String response = "HELLO";
						byte[] resBuffer = response.getBytes();
						DatagramPacket resPacket = new DatagramPacket(resBuffer, resBuffer.length, packet.getAddress(), packet.getPort());
						dSocket2.send(resPacket);
					}
					
				}
				
			} catch (UnknownHostException e) {
				// TODO: handle exception
				e.printStackTrace();
			} 
			catch (SocketException e) {
				// TODO: handle exception
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		btnFiilRecord.setOnAction((event)->{
			SmartContractRecord rec = new SmartContractRecord(arg0);
			arg0.show();
			p2pGridPane.setVisible(false);
			
		});
		/*btnCVetandUpadate.setOnAction((event)->{
			  
			
    		});*/
	}
	
}
