package csc2b.client;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ClientPane extends GridPane {
	
	Socket s = null;
	//inputStreams
	InputStream is = null;
	BufferedReader br = null;
	//outputStreams
	OutputStream os = null;
	BufferedOutputStream bos = null;
	DataOutputStream dos = null;
	
	
	private String imageName ="";
	private String cropURL = "/api/Crop";
	private String grayScaleURL = "/api/GrayScale";
	private String extractURL = "/api/Canny";
	private String fastURL = "/api/Fast";
	private Button btnConnect;
	private Button btnCrop;
	private Button btnAttach;
	private Button extractButton;
	private TextArea txtArea;
	private ImageView imgView1;
	private ImageView imgView2;
	private ImageView imgView3;
	private ImageView imgView4;
	private ImageView imgView5;
	private Text txtHeader;
	private TextField hostField;
	private Label hostLbl;
	private TextField portNum;
	private Label portLabel;
	private ByteArrayInputStream byteArry1;
	private ByteArrayInputStream byteArry2;
	private ByteArrayInputStream byteArry3;
	private MenuBar menuBar;

	
	
	
	
  private void setupGUI() {
	  
	 setHgap(10);
	 setVgap(15);
	 setPadding(new Insets(15, 15, 15, 15));
	 setStyle("-fx-background-color:lightblue;-fx-border-width:50; -fx-font-weight:bold;"
	 		+ "-fx-border-color:gold;");
	 setAlignment(Pos.CENTER);

		//Special FX
		//Reflection
		  Reflection ref = new Reflection();
		  ref.setFraction(5);
		  
		//DropShadow
		DropShadow ds = new DropShadow();
		ds.setColor(Color.BLUEVIOLET);
		ds.setOffsetX(3.0);
	    ds.setOffsetY(3.0);
	 txtHeader = new Text("UJ Community Covid-19 Facial Recognizer");
	 txtHeader.setFont(Font.font("Times New Roman", 50));
	 
	    menuBar = new MenuBar();
	    menuBar.setStyle("-fx-background-color:pink");
	    menuBar.setEffect(ref);
	    
		Menu menu = new Menu("APPLICATION MENU..");
		menu.setStyle("-fx-background-color:indianred;");
		menuBar.getMenus().add(menu);
		MenuItem mi = new MenuItem("Run Application..");
		mi.setStyle("-fx-background-color:orange");
		menu.getItems().add(mi);
	 
	 imgView1 = new ImageView();
	 btnConnect = new Button("Connect");
	 btnAttach = new Button("Attach Image");
	 extractButton = new Button("Extract(Canny&Fast)");
	 btnCrop = new Button("Crop&GrayScale");
	 txtArea = new TextArea();
	 txtArea.setPrefHeight(50);
	 imgView2 = new ImageView();
	 imgView3 = new ImageView();
	 imgView4 = new ImageView();
	 imgView5 = new ImageView();
	 
	 imgView1.setEffect(ref);
	 imgView1.setEffect(ds);
	 imgView2.setEffect(ref);
	 imgView2.setEffect(ds);
	 imgView3.setEffect(ref);
	 imgView3.setEffect(ds);
	 imgView4.setEffect(ref);
	 imgView4.setEffect(ds);
	 imgView5.setEffect(ref);
	 imgView5.setEffect(ds);
	
	 btnConnect.setEffect(ref);
	 btnConnect.setEffect(ds);
	 btnAttach.setEffect(ref);
	 btnAttach.setEffect(ds);
	 btnCrop.setEffect(ref);
	 btnCrop.setEffect(ds);
	 extractButton.setEffect(ref);
	 extractButton.setEffect(ds);
	 txtHeader.setEffect(ref);
	 txtHeader.setEffect(ds);
	 txtArea.setEffect(ref);
	 txtArea.setEffect(ds);
    
	 txtArea.setStyle("-fx-background-color:pink");
	 txtArea.setFont(Font.font("Times New Roman", 20));
     txtArea.setEditable(false);
	 portLabel = new Label("Port Number:");
	 hostLbl = new Label("Host:");
	 hostField = new TextField();
	 portNum = new TextField();
	 HBox hBox = new HBox(5, hostLbl,hostField,portLabel,portNum,btnConnect);
	 hBox.setEffect(ds);
	 hBox.setEffect(ref);
	 
	 menuBar.setEffect(ref);
	 menuBar.setEffect(ds);
	 
	 add(txtHeader, 0,0);
	 add(menuBar, 0,1);
	
	 
	 //Application Menu listener
	 mi.setOnAction(e->{
		 
		 add(hBox, 0, 2);
		 add(btnAttach, 0, 4);
		 add(btnCrop, 0,5);
		 add(txtArea, 0,3,3,1);
		 add(imgView2, 2,5);
		 add(imgView1, 2,4,1,1);
		 add(extractButton, 0, 6);
		 add(imgView3, 2, 6);
		 add(imgView4, 3, 5);
		 add(imgView5, 3, 6);
	 }
		 );
	 
	
	 
	
			 
	
}
  public ClientPane(Stage stage) {
	  
		setupGUI();
		//Connects to the Web API
		btnConnect.setOnAction((event)->{
		
			
			
			try
			{
			  int port = Integer.parseInt(portNum.getText());
			  String host = hostField.getText().toLowerCase();
			  s = new Socket(host,port);
			  txtArea.appendText("Client connected to the server on port "+port+"\r\n");
			  //bind streams
			  is = s.getInputStream();
			  br = new BufferedReader(new InputStreamReader(is));
			 
			  os = s.getOutputStream();
			  bos = new BufferedOutputStream(os);
			  dos = new DataOutputStream(bos);
			 
			  
			} catch(IOException e) {
				e.printStackTrace(); 
			}
				
		});
		
		//Attach image listener
		btnAttach.setOnAction(e->{
			
		 FileChooser fc = new FileChooser();
		 fc.setTitle("Choose Image File");
		 fc.setInitialDirectory(new File("./data"));
		 File selectedfile = fc.showOpenDialog(stage);
		 imageName = selectedfile.getName();
		 Image readIMG = new
				 Image("file:data/"+imageName);
		 imgView1.setImage(readIMG);
		 Text txtOrgText = new Text("Original ------>>");
		 txtOrgText.setFill(Color.SALMON);
		 txtOrgText.setFont(Font.font("Tahoma",FontWeight.NORMAL, 30));
		 add(txtOrgText, 1, 4);
		 
		 System.out.println(imageName);
			
		});
		//Pre-processing listener
		btnCrop.setOnAction((event)->
		{
			
			//Crop Method block
			 
			try {
				//DOS(BOS(OS))
				//Create a File handle
				File imageFile = new File("./data", imageName);
				//read the File into a FileInputStream
				FileInputStream fileInputStreamReader = new
						FileInputStream(imageFile);
				//Put the file contents into a byte[]
				byte[] bytes = new byte[(int)imageFile.length()];
				fileInputStreamReader.read(bytes);
				//Encode the bytes into a base64 format string
				String encodedFile  = new
						String(Base64.getEncoder().encodeToString(bytes));
				//get the bytes of this encoded string
				byte[] bytesToSend = encodedFile.getBytes();
				//Construct a POST HTTP REQUEST
				dos.write(("POST " + cropURL +" HTTP/1.1\r\n").getBytes());
				dos.write(("Content-Type: " +"application/text\r\n").getBytes());
				dos.write(("Content-Length: " + encodedFile.length()
				+"\r\n").getBytes());
				dos.write(("\r\n").getBytes());
				dos.write(bytesToSend);
				dos.write(("\r\n").getBytes());
				dos.flush();
				txtArea.appendText("POST Request Sent\r\n");
				//read text response
				String response = "";
				String line = "";
				while(!(line = br.readLine()).equals(""))
				{
					response += line +"\n";
				}
				System.out.println(response);
				String imgData = "";
				while((line = br.readLine())!=null)
				{
					imgData += line;
				}
				String base64Str =
						imgData.substring(imgData.indexOf('\'')+1,imgData.lastIndexOf('}')-1);
				base64Str.trim();
				byte[] decodedString = Base64.getDecoder().decode(base64Str);
				//Display the image
				
				byteArry1 = new ByteArrayInputStream(decodedString);
				Image cropImg = new Image(byteArry1);
				imgView2.setImage(cropImg);
				
				byteArry2 = new ByteArrayInputStream(decodedString);
				BufferedImage image = ImageIO.read(byteArry2);
				ImageIO.write(image, "JPG",new File("data","Image3.jpg"));
				Text cropText = new Text("Pre-processing(Crop&GrayScale) ------>>");
				cropText.setFill(Color.SLATEBLUE);
				cropText.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
				add(cropText, 1, 5);
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			//GrayScale Method block
			
           try {
				try
				{
				  int port = Integer.parseInt(portNum.getText());
				  String host = hostField.getText().toLowerCase();
				  s = new Socket(host,port);
				  txtArea.appendText("Client connected to the server on port "+port+"\r\n");
				  //bind streams
				  is = s.getInputStream();
				  br = new BufferedReader(new InputStreamReader(is));
				 
				  os = s.getOutputStream();
				  bos = new BufferedOutputStream(os);
				  dos = new DataOutputStream(bos);
				 
				  
				} catch(IOException e) {
					e.printStackTrace(); 
				}
					
				//DOS(BOS(OS))
				//Create a File handle
				File imageFile2 = new File("./data", "Image3.jpg");
				//read the File into a FileInputStream
				FileInputStream fileInputStreamReader2 = new
						FileInputStream(imageFile2);
				//Put the file contents into a byte[]
				byte[] bytes2 = new byte[(int)imageFile2.length()];
				fileInputStreamReader2.read(bytes2);
				//Encode the bytes into a base64 format string
				String encodedFile2 = new
						String(Base64.getEncoder().encodeToString(bytes2));
				//get the bytes of this encoded string
				
				byte[] bytesToSend2 = encodedFile2.getBytes();
				//Construct a POST HTTP REQUEST
				dos.write(("POST " + grayScaleURL +" HTTP/1.1\r\n").getBytes());
				dos.write(("Content-Type: " +"application/text\r\n").getBytes());
				dos.write(("Content-Length: " + encodedFile2.length()
				+"\r\n").getBytes());
				dos.write(("\r\n").getBytes());
				dos.write(bytesToSend2);
				dos.write(("\r\n").getBytes());
				dos.flush();
				txtArea.appendText("POST 2 Request Sent\r\n");
				//read text response
				String response2 = "";
				String line2 = "";
				
				while(!(line2 = br.readLine()).equals(""))
				{
					response2 += line2 +"\n";
				}
		         response2 += line2 +"\n";
				System.out.println(response2);
				
				
				String imgData2 = "";
				while((line2 = br.readLine())!=null)
				{
					imgData2 += line2;
				}
				String base64Str2 =
						imgData2.substring(imgData2.indexOf('\'')+1,imgData2.lastIndexOf('}')-1);
				byte[] decodedString2 = Base64.getDecoder().decode(base64Str2);
				//Display the image
				Image grayScaleImg = new Image(new ByteArrayInputStream(decodedString2));
				imgView4.setImage(grayScaleImg);
				
				
			} 
			catch (IOException io) {
				io.printStackTrace();
			}finally
			{
			try {
			   dos.close();
			   s.close();}
			 catch (IOException io) {
					io.printStackTrace();
				}
			
			}
		  
		});
		  //Feature Extraction event handler
		
		  extractButton.setOnAction(event->{
			 
			  
			  //Canny Method Block
		    
			try {
				
				
				try
				{
				  int port = Integer.parseInt(portNum.getText());
				  String host = hostField.getText().toLowerCase();
				  s = new Socket(host,port);
				  txtArea.appendText("Client connected to the server on port "+port+"\r\n");
				  //bind streams
				  is = s.getInputStream();
				  br = new BufferedReader(new InputStreamReader(is));
				 
				  os = s.getOutputStream();
				  bos = new BufferedOutputStream(os);
				  dos = new DataOutputStream(bos);
				 
				  
				} catch(IOException e) {
					e.printStackTrace(); 
				}
					
				//DOS(BOS(OS))
				//Create a File handle
				File imageFile2 = new File("./data", "Image3.jpg");
				//read the File into a FileInputStream
				FileInputStream fileInputStreamReader2 = new
						FileInputStream(imageFile2);
				//Put the file contents into a byte[]
				byte[] bytes2 = new byte[(int)imageFile2.length()];
				fileInputStreamReader2.read(bytes2);
				//Encode the bytes into a base64 format string
				String encodedFile2 = new
						String(Base64.getEncoder().encodeToString(bytes2));
				//get the bytes of this encoded string
				
				byte[] bytesToSend2 = encodedFile2.getBytes();
				//Construct a POST HTTP REQUEST
				dos.write(("POST " + extractURL +" HTTP/1.1\r\n").getBytes());
				dos.write(("Content-Type: " +"application/text\r\n").getBytes());
				dos.write(("Content-Length: " + encodedFile2.length()
				+"\r\n").getBytes());
				dos.write(("\r\n").getBytes());
				dos.write(bytesToSend2);
				dos.write(("\r\n").getBytes());
				dos.flush();
				txtArea.appendText("POST 2 Request Sent\r\n");
				//read text response
				String response2 = "";
				String line2 = "";
				
				while(!(line2 = br.readLine()).equals(""))
				{
					response2 += line2 +"\n";
				}
		         response2 += line2 +"\n";
				System.out.println(response2);
				
				
				String imgData2 = "";
				while((line2 = br.readLine())!=null)
				{
					imgData2 += line2;
				}
				String base64Str2 =
						imgData2.substring(imgData2.indexOf('\'')+1,imgData2.lastIndexOf('}')-1);
				byte[] decodedString2 = Base64.getDecoder().decode(base64Str2);
				//Display the image
				Image extractedImg = new Image(new ByteArrayInputStream(decodedString2));
				imgView3.setImage(extractedImg);
				
				Text extractText = new Text("Feature Extraction(Canny&Fast) ------>>");
				extractText.setFill(Color.DIMGRAY);
				extractText.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
				add(extractText, 1, 6);
			}
			 
			catch (IOException io) {
				io.printStackTrace();
			}finally
			{
			try {
			   dos.close();
			   s.close();}
			 catch (IOException io) {
					io.printStackTrace();
				}
			
			}
		  
  
		  
		  //Fast Method Block
		  try {
				
				
				try
				{
				  int port = Integer.parseInt(portNum.getText());
				  String host = hostField.getText().toLowerCase();
				  s = new Socket(host,port);
				  txtArea.appendText("Client connected to the server on port "+port+"\r\n");
				  //bind streams
				  is = s.getInputStream();
				  br = new BufferedReader(new InputStreamReader(is));
				 
				  os = s.getOutputStream();
				  bos = new BufferedOutputStream(os);
				  dos = new DataOutputStream(bos);
				 
				  
				} catch(IOException e) {
					e.printStackTrace(); 
				}
					
				//DOS(BOS(OS))
				//Create a File handle
				File imageFile2 = new File("./data", "Image3.jpg");
				//read the File into a FileInputStream
				FileInputStream fileInputStreamReader2 = new
						FileInputStream(imageFile2);
				//Put the file contents into a byte[]
				byte[] bytes2 = new byte[(int)imageFile2.length()];
				fileInputStreamReader2.read(bytes2);
				//Encode the bytes into a base64 format string
				String encodedFile2 = new
						String(Base64.getEncoder().encodeToString(bytes2));
				//get the bytes of this encoded string
				
				byte[] bytesToSend2 = encodedFile2.getBytes();
				//Construct a POST HTTP REQUEST
				dos.write(("POST " + fastURL +" HTTP/1.1\r\n").getBytes());
				dos.write(("Content-Type: " +"application/text\r\n").getBytes());
				dos.write(("Content-Length: " + encodedFile2.length()
				+"\r\n").getBytes());
				dos.write(("\r\n").getBytes());
				dos.write(bytesToSend2);
				dos.write(("\r\n").getBytes());
				dos.flush();
				txtArea.appendText("POST 2 Request Sent\r\n");
				//read text response
				String response2 = "";
				String line2 = "";
				
				while(!(line2 = br.readLine()).equals(""))
				{
					response2 += line2 +"\n";
				}
		         response2 += line2 +"\n";
				System.out.println(response2);
				
				
				String imgData2 = "";
				while((line2 = br.readLine())!=null)
				{
					imgData2 += line2;
				}
				String base64Str2 =
						imgData2.substring(imgData2.indexOf('\'')+1,imgData2.lastIndexOf('}')-1);
				byte[] decodedString2 = Base64.getDecoder().decode(base64Str2);
				//Display the image
				Image oRBImage = new Image(new ByteArrayInputStream(decodedString2));
				imgView5.setImage(oRBImage);
				
				
			} 
			catch (IOException io) {
				io.printStackTrace();
			}finally
			{
			try {
			   dos.close();
			   s.close();}
			 catch (IOException io) {
					io.printStackTrace();
				}
	
			} }
		);
    }
  }

