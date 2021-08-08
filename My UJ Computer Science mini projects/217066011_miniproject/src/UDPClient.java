
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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


 public  class  UDPClient extends GridPane implements Comparable<UDPClient>{
	private static final String RSA_ALGORITHM = "RSA";
	
	private ArrayListQueue<Block> blockChain;
	private Button btnConnect;
	private Button btnFiilRecord;
	private Button btnHostConnection;
	private Button btnVetandUpadate;
	Button saveRecButton;
	private GraphSocket connection;
	private int port;
	private Graph networkGraph;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private int serverPort = 1234;
	private InetAddress severAddr;
	private GridPane p2pGridPane;
	private GridPane toggPane;
	private SmartContractRecord rec;
	private static TextArea tArea;
	private List<Graph.Vertex<UDPClient>> vertices;
	private List<Graph.Edge<GraphSocket>> edges;

	
  public UDPClient(GraphSocket connection,Stage arg0) {

	this.connection = connection;
	
	vertices = new ArrayList<Graph.Vertex<UDPClient>>();
	edges = new ArrayList<Graph.Edge<GraphSocket>>();
	Graph.Vertex<UDPClient>  vertex = new Graph.Vertex<>(this);
	vertices.add(vertex);
	edges.add(new Graph.Edge(0, vertex, vertex));
	
	networkGraph = new Graph(vertices, edges);
	blockChain = new ArrayListQueue<Block>();
	arg0.setTitle("P2P UDPClient");
	p2pGridPane = createPane();
	toggPane = createPane();
	setUIControls(p2pGridPane,toggPane,arg0);
    
	VBox vbox = new VBox();
	vbox.setSpacing(10);
	vbox.setPadding(new Insets(10, 10, 10,10));
	vbox.getChildren().addAll(p2pGridPane,toggPane);
	Scene scene = new Scene(vbox, 500 ,500);
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
	
	
}

public int getServerPort() {
	return serverPort;
}

public GraphSocket getConnection() {
	return connection;
}

public int getPort() {
	return port;
}
public PrivateKey getPrivateKey() {
	return privateKey;
}
public PublicKey getPublicKey() {
	return publicKey;
}
public InetAddress getSeverAddr() {
	return severAddr;
}
@SuppressWarnings("unchecked")
private void addBlockToBlockChain(Block block) throws Exception {

	@SuppressWarnings("unused")
	String newBPrevHash =block.getPrevioushashCode();
	newBPrevHash = blockChain.getPreviousElement(block).getCurrenHashCode();
	
	block.mineBlock(4);
	//Adds block to blockchain if has been succesfully mined and decrytes with key
	blockChain.enqueue(decrypteBlock(block, publicKey));


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
	 public static Block encrypteBlock(Block block, PrivateKey Key) throws Exception  {
	        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	        PublicKey publicKey = KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(Key.toString().getBytes())));
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	       
	        byte[] encryptedbytes  = cipher.doFinal(Block.serialize(block));
	      
	         return Block.deserialize(encryptedbytes);      
         	
    }
 
 
 
	 public static Block decrypteBlock(Block block, PublicKey Key) throws Exception {
	        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
	        PrivateKey pk = KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(Key.toString().getBytes())));
			
	        try {
				cipher.init(Cipher.DECRYPT_MODE, pk);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Block can't be added to the blockchain as the miner has no access to a valid key for decryption" );
				tArea.appendText("Block can't be added to the blockchain as the miner has no access to a valid key for decryption");
				
			}
	        byte[] dencryptedbytes = cipher.doFinal(Base64.getDecoder().decode(Block.serialize(block)));
	        return Block.deserialize(dencryptedbytes);
	    }
    private String generateUniquePeerReference() {
    	String reference ="";
    	Random rand = new Random();
    	rand.setSeed(System.currentTimeMillis());
    	int randInt = rand.nextInt(25);
    	 char[] chars = {'e','t','a','o','i','n','s','h','r','d',
    			'l','c','u','m','w','f','g','y','p','b','v','k','j','x','q','z'};
    	StringBuilder builder = new StringBuilder();
    	
    	builder.append(chars[randInt]+randInt+chars[randInt]+randInt+2*randInt+"xXR"+chars[randInt]);
    	reference = builder.toString();
    	
    	return reference;
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
				tArea.appendText("Current Hashes not equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getCurrenHashCode().equals(currentBlock.getPrevioushashCode()) ) {
				System.out.println("Previous Hashes not equal");
				tArea.appendText("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.getCurrenHashCode().substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				tArea.appendText("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
	
	
	private String sendTextMessage(String request,GraphSocket dSocket) {
		String response ="";
		try {
			byte[] buffer = request.getBytes();
			System.out.println("Sending request: "+request);
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
	
	private void setUIControls(GridPane p2pGridPane,GridPane togglePane ,Stage arg0) {
		
		Label theaderLabel = new Label("Start connection Hotspot for other peers to join in the network:");
		theaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		tArea = new TextArea();
		tArea.appendText("----------UDP Peer Commnd Structure-------------\r\n");
		tArea.setEditable(false);
		btnConnect = new Button("Connect to Peer(s)");
		btnFiilRecord = new Button("Fill in the Smart Contract Record and add it to the block");
		btnVetandUpadate = new Button("Vet block and Update block chain state");
		btnHostConnection = new Button("Host Connection for Peers");
		saveRecButton = new Button("Save Record History");
		
		Label lheaderLabel = new Label("UDP Peer-"+generateUniquePeerReference());
		lheaderLabel.setFont(Font.font("Ariel", FontWeight.BOLD, 40));
		p2pGridPane.add(btnHostConnection, 0, 5);
		p2pGridPane.add(lheaderLabel, 0, 1,1,1);
		p2pGridPane.add(theaderLabel, 0, 2);
		
		p2pGridPane.add(btnConnect, 0, 6);
		p2pGridPane.add(btnFiilRecord, 0, 7);
		p2pGridPane.add(btnVetandUpadate, 0, 8);
		p2pGridPane.add(tArea, 0, 9);
		togglePane.add(p2pGridPane, 0, 0);
		togglePane.add(saveRecButton, 0,15);
		
		
		  btnConnect.setOnAction((event)->{
			
			for (Graph.Vertex<UDPClient> v : vertices)
			
				for (Graph.Edge<GraphSocket> e: edges) {
					
					ListIterator<Graph.Vertex<UDPClient>> iterV = vertices.listIterator();
					ListIterator<Graph.Edge<GraphSocket>> iterE = edges.listIterator();
					while(iterV.hasNext() && iterE.hasPrevious()) {
						
					v =(Graph.Vertex<UDPClient>)iterV.previous();
					e = (Graph.Edge<GraphSocket>)iterE.previous();
					
					
			    	
					}
				}
			try { 
				
				severAddr = InetAddress.getByName("localhost");
				connection = new GraphSocket();
				tArea.appendText("Started on port: "+ connection.getLocalPort()+"\r\n");
				System.out.println("Started on port: "+ connection.getLocalPort()+"\r\n");
				tArea.appendText("Connected to "+severAddr+"\r\n");
				System.out.println("Connected to "+severAddr);
				
			}
			catch (UnknownHostException ue) {
				// TODO Auto-generated catch block
				ue.printStackTrace();
			}
			catch (SocketException e2) {
				// TODO: handle exception
			
			e2.printStackTrace();
			}
			String response = sendTextMessage("CONNECT",connection);
			tArea.appendText("\r\nResponse:"+response+"\r\n"); 
			System.out.println("\r\nResponse:"+response+"\r\n");
		});
		

		btnHostConnection.setOnAction((event)->{
             
			try {
				
				severAddr = InetAddress.getLocalHost();
				connection = new GraphSocket(serverPort);
				System.out.print("Started on port: "+connection.getLocalPort()+"\r\n");
				tArea.appendText("Started on port: "+connection.getLocalPort()+"\r\n");
				tArea.appendText("Connection has been set up.Ready for other peers to connect to the network.."+"\r\n");
				System.out.println("Connection has been set up.Ready for other peers to connect to the network.."+"\r\n");
				while (true) {
					byte[] buffer = new byte[1024];
					DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
					connection.receive(packet);
					String request = new String(buffer).trim();
					if (request.toLowerCase().startsWith("connect")) {

						String response = "HELLO";
						byte[] resBuffer = response.getBytes();
						DatagramPacket resPacket = new DatagramPacket(resBuffer, resBuffer.length, packet.getAddress(), packet.getPort());
						connection.send(resPacket);
					}
					else if (request.toLowerCase().startsWith("fillrecord")) {
						String response = "OK I WILL FILL IN THE RECORD";
						byte[] resBuffer = response.getBytes();
						DatagramPacket resPacket = new DatagramPacket(resBuffer, resBuffer.length, packet.getAddress(), packet.getPort());
						connection.send(resPacket);
					}
		
					else if (request.toLowerCase().startsWith("vetandupdate")) {
						System.out.println("Request received: "+request);
						String response ="";
						if(isChainValid(4)==true) {
							response = " BLOCKCHAIN STATE UPDATED FOR ALL";
						}
						else {
							response = "INVALID CHAIN!!!!";
						}
						
						buffer =  response.getBytes();
						DatagramPacket filePacket = new DatagramPacket(buffer, buffer.length, packet.getAddress(),packet.getPort());
						connection.send(filePacket);
						
						
					}

				}


			}
			catch (UnknownHostException e1) {
				// TODO: handle exception
				e1.printStackTrace();
			} 
			catch (SocketException se) {
				// TODO: handle exception
				se.printStackTrace();
			}catch (IOException io) {
				// TODO Auto-generated catch block
				io.printStackTrace();
			}
			
		});
		btnFiilRecord.setOnAction((event)->{
			
			String request = "FILLRECORD";
			String response= sendTextMessage(request, connection);
			System.out.println("Response :"+response);
			tArea.appendText("Responmse :"+request);
			rec = new SmartContractRecord();
		});

		btnVetandUpadate.setOnAction((event)->{
			String request = "VETANDUPDATE";
			String response = sendTextMessage(request, connection);
			System.out.println("Response :"+response);
			tArea.appendText("Response:"+response);
			
			Block block = new Block();
			block.setSmartContractRecord(rec);
			try {
				Block encypted = encrypteBlock(block, this.privateKey);
				tArea.appendText("BlockChain Vailid status:"+isChainValid(4));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(connection==null) {
					connection.close();
				}
				
			}
		});
		
		saveRecButton.setOnAction((event)->{
			File f = new File("record.txt");
			tArea.appendText("Record information stored to text file"+f.getName());
			try {
				saveToRecordHistory(f);
				rec.setVisible(false);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}
	//Stores record History in file
	private void saveToRecordHistory(File f) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(f);
		pw.write("=====================================RECORD HISTORY============================================");
		
		pw.write("Contact Type: "+rec.getContractMode());
		String names[] =rec.getNames().split("\\s");
		pw.write("Names: "+names[0]+" "+names[1]+" "+names[2]);
		pw.write("Gender: " +rec.getGender()+"\n");
		pw.write(rec.getIdNumber());
		pw.write("Date of Birth: "+rec.getDateofBirth());
		pw.write("Adress: "+rec.getAddress());
		pw.write("Contact Number: "+rec.getContactNum());
		pw.write("Email: "+rec.getEmailString());
		pw.write("Agreeement Concluded on: "+rec.getTimeConclude());
		pw.flush();
		pw.close();
		
		
	}

	@Override
	public int compareTo(UDPClient o) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
 
 class GraphSocket extends DatagramSocket implements Comparable<GraphSocket>{
	
	public GraphSocket()  throws SocketException{
		// TODO Auto-generated constructor stub
		super();
	}
	
	public GraphSocket(int port) throws SocketException{
		super(port);
	}
	@Override
	
	public final void send(DatagramPacket p) throws IOException {
		// TODO Auto-generated method stub
		super.send(p);
	}
	@Override
	public synchronized void receive(DatagramPacket p) throws IOException {
		// TODO Auto-generated method stub
		super.receive(p);
	}
	
	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return super.getLocalPort();
	}
	public InetAddress getLocalAddress() {
		// TODO Auto-generated method stub
		return super.getLocalAddress();
	}
	@Override
	public int compareTo(GraphSocket o) {
		// TODO Auto-generated method stub
		return 0;
	}
}