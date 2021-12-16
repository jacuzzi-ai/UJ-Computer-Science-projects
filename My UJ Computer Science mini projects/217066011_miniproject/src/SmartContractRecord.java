
import java.io.File;
import java.util.Date;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class SmartContractRecord  extends GridPane{
	
    private final String companyName = "UTOPIAN PAWNSHOP PTY(LTD)";
    private String Names;
   

	
	private String idNumber;
    private String DateofBirth;
    private String address;
    private String gender;
    private String ContractMode;
    private String contactNum;
    private String emailString;
    
    private GridPane smartGridPane = null;
    private GridPane pawnGridPane = null;
    private GridPane buyGridPane = null;
    
	//Amount deposited in the smart contract,R1000
    private int customerBalance = 10000;
    private boolean couriered = false;
    private boolean releaseFundsToCustomer = false;
    private boolean releaseFundsToCompany = false;
    private boolean deliveredToCustomer = false;
    private boolean collectedFromCustomer = false;
    private TextField tfFirstName1;
    private TextField tfFirstName2;
    private TextField tfLastName;
    private TextField tfIDNum;
    private TextField txtEmail;
    private TextField tfContNum;
    private Date date;
    private String timeConclude = getTimeStamp();
    private Text txtTimeS;
 
    
    public SmartContractRecord() {
    	// TODO Auto-generated method stub
    	        Stage contractStage = new Stage();
    	        contractStage.setAlwaysOnTop(true);
    			contractStage.setTitle("UTOPIAN PAWN SHOP SMART CONTRACT");
    			smartGridPane = createGridPane();
    			smartGridPane.setMaxHeight(contractStage.getMaxHeight());
    			smartGridPane.setMaxWidth(contractStage.getMaxWidth());
    			buyGridPane = createGridPane();
    			buyGridPane.setMaxHeight(contractStage.getMaxHeight());
    			buyGridPane.setMaxWidth(contractStage.getMaxWidth());
    			pawnGridPane = createGridPane();
    			pawnGridPane.setMaxHeight(contractStage.getMaxHeight());
    			pawnGridPane.setMaxWidth(contractStage.getMaxWidth());
    			 
    			setUIControls(smartGridPane, pawnGridPane, buyGridPane, contractStage);
    			VBox vbox = new VBox(10,smartGridPane,buyGridPane,pawnGridPane);
    			vbox.setMaxWidth(contractStage.getMaxWidth());
    			vbox.setMaxHeight(contractStage.getMaxHeight());
    			vbox.setPadding(new Insets(10, 10, 10,10));
    			
    		    TitledPane tp = new TitledPane();
    			tp.setText("Launch Program");
    		
    			tp.setStyle("-fx-foreground-color:pink");
    			tp.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
    			tp.setTextFill(Color.SLATEBLUE);
    		    ScrollPane sp = new ScrollPane();
    		    sp.setFitToHeight(true);
    		    sp.setFitToWidth(true);
    		    sp.setStyle("-fx-background-color:orange");
    		    sp.setStyle("-fx-foreground-color:orange");
    		    sp.setMaxWidth(contractStage.getMaxWidth());
    	        sp.setMaxHeight(contractStage.getMaxHeight());
    		    sp.setContent(vbox);
    		    tp.setContent(sp);
    			
    	        Accordion accordion = new Accordion();
    	        accordion.setMaxWidth(contractStage.getMaxWidth());
    	        accordion.setMaxHeight(contractStage.getMaxHeight());
    	        accordion.getPanes().add(tp);
    			
    	        Scene scene = new Scene(accordion,500,500);
    			contractStage.setScene(scene);
    			contractStage.show();
    			
    			
	}
   
	private double computeLoanAmountPayableWithInterest(int originalAmount) {
		
		double owedAmount = originalAmount+originalAmount*0.40;
		return Math.round(owedAmount);
		
	}
	private boolean buySecondHandGoods(int purchaseAmount) {
		if (this.customerBalance<purchaseAmount) {
			return false;
		}
		
		return customerBalance >= purchaseAmount;
	}
	private double reduceCompensationForFaultyGoods(int actualprice) {
		
		double reducedLonanRewardAmount =  (double)actualprice*0.70;
		
		return Math.round(reducedLonanRewardAmount);
	}
	private boolean IsproductAcceptablebyCompany(boolean state) {
		if (state==false) {
			return false;
		}
		return true;
	}
	private boolean pawnSecondHandGoods(int sellPrice,boolean releaseFundsToCustomer) {
		if(releaseFundsToCustomer == false)
			return false;
		this.customerBalance = this.customerBalance + sellPrice;
		
		return true;
	}
	public String getNames() {
		return Names;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public String getDateofBirth() {
		return DateofBirth;
	}

	public String getAddress() {
		return address;
	}

	public String getGender() {
		return gender;
	}

	public String getContractMode() {
		return ContractMode;
	}

	public String getContactNum() {
		return contactNum;
	}

	public String getEmailString() {
		return emailString;
	}
	
	public String getTimeConclude() {
		return timeConclude;
	}
	private void setUIControls(GridPane smartGridPane,GridPane pawnGridPane,GridPane buyGridPane, Stage arg0) {
		
		//Special FX
		//Reflection
		Reflection ref = new Reflection();
		ref.setFraction(5);

		//DropShadow
		DropShadow ds = new DropShadow();
		ds.setColor(Color.BLUEVIOLET);
		ds.setOffsetX(3.0);
		ds.setOffsetY(3.0);
		
	    final Text txtHeader = new Text("UTOPIAN PAWN SHOP SMART CONTRACT");
		txtHeader.setEffect(ds);
		txtHeader.setEffect(ref);
		txtHeader.setFont(Font.font("Times New Roman", 30));

		final MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color:pink;-fx-border-width:15;-fx-border-magenta:silver;-fx-font-weight:bold;-fx-font-size:15;");
		menuBar.setEffect(ref);
		menuBar.setTranslateX(150);
		menuBar.setTranslateX(45);
		
		final Menu menu = new Menu("Select Mode..");
		menu.setStyle("-fx-background-color:indianred;;-fx-border-width:15;-fx-border-magenta:silver;-fx-font-weight:bold;-fx-font-size:15;");
		menuBar.getMenus().add(menu);
		final Image image = new Image("file:gui_aids/front.jpg");
		
		final Image image1 = new Image("file:gui_aids/loan.png");
		final Image image2 = new Image("file:gui_aids/buy.jpg");
		final ImageView  imgV1 = new  ImageView(image1);
		imgV1.setFitHeight(4);
		imgV1.setFitWidth(4);
		final ImageView  imgV2 = new  ImageView(image2);
		imgV1.setFitHeight(4);
		imgV1.setFitWidth(4);
	    ImageView imgView = new ImageView();
		imgView.setImage(image);
	    final  MenuItem mi1 = new MenuItem("Pawn Goods",imgV1);
		final MenuItem mi2 = new MenuItem("Buy goods",imgV2);
		mi1.setStyle("-fx-background-color:orange");
		mi2.setStyle("-fx-background-color:yellow");
		menu.getItems().addAll(mi1,mi2);

		menuBar.setEffect(ref);
		menuBar.setEffect(ds);
		
	    final Button addID = new Button("Attach ID copy");
		final Button exitButton = new Button("Exit Mode");
		exitButton.setEffect(ds);
		exitButton.setEffect(ref);
		exitButton.setVisible(false);
		exitButton.setStyle("-fx-background-color:green");
		addID.setEffect(ds);
		addID.setEffect(ref);
		addID.setStyle("-fx-background-color:gold");

		final Tooltip tp = new Tooltip("Select an option to loan cash or buy our products");
		menuBar.setTooltip(tp);
		

		smartGridPane.add(menuBar, 0,5);
		smartGridPane.add(txtHeader,0,0);
		smartGridPane.add(imgView, 0, 8);
		smartGridPane.add(exitButton, 0, 16);
		buyGridPane.setVisible(false);
		pawnGridPane.setVisible(true);

		 final	ObservableList<String> fileList = FXCollections.observableArrayList();
		 final ListView<String> listView = new ListView<String>(fileList);
		 listView.setPrefHeight(250);
		 listView.setVisible(false);
		 final Label itemBuy = new Label("Select the item that you want to purchase:");
		 final Label itemPawn = new Label("We Pawn the following second-hand products:");
		 itemBuy.setWrapText(true);
		 final Label transBank = new Label("Select Transactional Bank and make payment:");
		 itemBuy.setWrapText(true);
		 final Label transBank2 = new Label("Select Transactional Bank and enter banking details for your \r\nloan(repayable with 40% interest) to be payed to:");
		 transBank.setWrapText(true);
		 transBank2.setWrapText(true);
		 String[] purchaseItems = new String[] {
				 "Laptop-@R4500","Mobile Phone-@R8000","Hi-fi-@R3000","HD Plasma TV-@R7000","Wireless Speaker-@R350"};
		
		 String[] banks = new String[] {"ABSA","CAPITEC","STANDARDBANK","NEDBANK","FNB","AFRICANBANK"};
		 String[] pawnItems = new String[] {
				 "Pawn Laptop-@R3500","Pawn Mobile Phone-@R5000","Pawn Hi-fi-@R1500","Pawn HD Plasma TV-@R5000","Pawn Wireless Speaker-@R100"};
		 final ChoiceBox<String> choose = new ChoiceBox<>(FXCollections.observableArrayList(purchaseItems));
		 final ChoiceBox<String> choosePawn = new ChoiceBox<>(FXCollections.observableArrayList(pawnItems));
		 //Add controls to the smartGridPane
		 final ChoiceBox<String> choose2 = new ChoiceBox<>(FXCollections.observableArrayList(banks));
		 final ChoiceBox<String> choose2p = new ChoiceBox<>(FXCollections.observableArrayList(banks));
		 final  HBox hbp1 = new HBox(15,itemPawn,choosePawn);
         hbp1.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
					+ "-fx-border-color:maroon;-fx-font-weight:bold;");
         
         final  HBox hbp2 = new HBox(15,transBank2,choose2p);
         hbp2.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
					+ "-fx-border-color:maroon;-fx-font-weight:bold;");
         
         final  HBox hbb1 = new HBox(15,itemBuy,choose);
         hbb1.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
					+ "-fx-border-color:maroon;-fx-font-weight:bold;");
         final  HBox hbb2 = new HBox(15,transBank,choose2);
        hbb2.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
		+ "-fx-border-color:maroon;-fx-font-weight:bold;");
      
        final  HBox idBox = new HBox(15,addID,listView);
      idBox.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
		+ "-fx-border-color:maroon;-fx-font-weight:bold;");
		
		 //Add controls to the pawnGridPane
		  mi1.setOnAction((event)->{
			  
			     ContractMode = new String("Pawn Mode");
			     imgView.setVisible(false);
				 tfFirstName1 = new TextField();
				 tfFirstName1.setPromptText("First Name");
				 tfFirstName1.setEffect(ds);
				 tfFirstName1.setEffect(ref);
				 tfFirstName2 = new TextField();
				 tfFirstName2.setPromptText("Second Name");
				 tfFirstName2.setEffect(ds);
				 tfFirstName2.setEffect(ref);
				 tfLastName = new TextField();
				 tfLastName.setEffect(ds);
				 tfLastName.setEffect(ref);
				 tfLastName.setPromptText("Last Name\\Surname");
				 this.Names = tfFirstName1.getText()+" "+tfFirstName2.getText()+" "+tfLastName.getText();
				 tfIDNum = new TextField();
				 tfIDNum.setPromptText("SA Valid ID Number");
				 tfIDNum.setEffect(ds);
				 tfIDNum.setEffect(ref);
				 this.idNumber = tfIDNum.getText();
				 txtEmail = new TextField();
				 txtEmail.setPromptText("Email Adress");
				 tfIDNum.setEffect(ds);
				 txtEmail.setEffect(ref);
				 this.emailString = txtEmail.getText();
				 tfContNum = new TextField();
				 tfContNum.setPromptText("Contact number");
				 tfContNum.setEffect(ds);
				 tfContNum.setEffect(ref);
				 this.contactNum = tfContNum.getText();
				 final HBox contacts = new HBox(15, tfIDNum,tfContNum,txtEmail);
	             contacts.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
							+ "-fx-border-color:maroon;-fx-font-weight:bold;");
	            
				
	             final TextArea  taAdress = new TextArea();
	             this.address = taAdress.getText();
				 taAdress.setPromptText("Street Number:\n\rStreet Name:\n\rTown\\Surburb:\n\rCity:\n\rProvince:\n\rPostal Code:");
				 ScrollBar scV = new ScrollBar();
				 scV.setOrientation(Orientation.VERTICAL);
				 
				 scV.valueProperty().addListener(ov->{
					 taAdress.setLayoutY(scV.getValue());
				 });
				
				 
				final HBox addressBox = new HBox(15, new Label("Physical Address(Needs to be the same address\r\n were delivery"
				 		+ "/dropoff services will rendered):"),taAdress,scV);
				 addressBox.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
							+ "-fx-border-color:maroon;-fx-font-weight:bold;");
				 
	             final Label label = new Label("Pick your Gender:");
	             final CheckBox c1 = new CheckBox("Male");
	             final CheckBox c2 = new CheckBox("Female");
	             final CheckBox c3 = new CheckBox("other");
	             final DatePicker dPicker = new DatePicker();
	             final Label dL = new Label("Select Date of\r\nBirth:");
	             
	             c1.setWrapText(true);
	             c2.setWrapText(true);
	             c3.setWrapText(true);
	             
	            final HBox gBox = new HBox(15, label,c1,c2,c3,dL, dPicker);
	             gBox.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
							+ "-fx-border-color:maroon;-fx-font-weight:bold;");
	             
				 taAdress.setEditable(true);
				 taAdress.setStyle("-fx-background-color:pink");
				 taAdress.setFont(Font.font("Cambria", 25));
				 taAdress.setEffect(ref);
				 taAdress.setEffect(ds);
				 final Text  txtpawn = new Text("PAWN MODE");
				 txtpawn.setEffect(ds);
				 txtpawn.setEffect(ref);
				 exitButton.setVisible(true);
				 menuBar.setVisible(false);
				 buyGridPane.setVisible(false);
				 pawnGridPane.setVisible(true);
				 pawnGridPane.add(txtpawn, 0, 0);
				 pawnGridPane.add(tfFirstName1, 0, 1);
				 pawnGridPane.add(tfFirstName2, 0, 2);
				 pawnGridPane.add(gBox,0, 4);
				 pawnGridPane.add(tfLastName, 0, 3);
				 pawnGridPane.add(contacts, 0, 5);
				 pawnGridPane.add(addressBox, 0, 6);
				 pawnGridPane.add(idBox, 0,8);
				 pawnGridPane.add(hbp1, 0, 9);
				 pawnGridPane.add(hbp2, 0,10);
				 
		  });
		  choosePawn.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				 @Override
				 public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					 // TODO Auto-generated method stub
					     choose2p.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
						 @Override
						 public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue2) {
							 GridPane paymentGrid = new GridPane();
								paymentGrid.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
											+ "-fx-border-color:maroon;-fx-font-weight:bold;");
							 pawnGridPane.add(paymentGrid,0 ,13);
							 final Text text = new Text(banks[newValue2.intValue()]+"\nPayment Account Details:");
							 paymentGrid.add(text, 0, 0);
							 paymentGrid.add(new Label("Account Holder:"), 0, 1);
							 paymentGrid.add(new TextField(), 1, 1);
							 paymentGrid.add(new Label("Account Number"), 0, 2);
							 paymentGrid.add(new TextField(), 1 ,2);
	                         paymentGrid.add(new Label("Branch Code:"), 0, 3);
	                         paymentGrid.add(new TextField(), 1, 3);
							 paymentGrid.add(new Label("Pawn item:"), 0, 4);
							 TextField pawnField = new TextField(pawnItems[newValue.intValue()]);
							 pawnField.setEditable(false);
							 paymentGrid.add(pawnField, 1, 4);
							 
							 
							 final Label label = new Label("Company Feedback from \r\nproduct evaluation\r\nField to be\r\n completed\r\nby company");
							 final RadioButton rb1 = new  RadioButton("Product Eligible for 100 % loan");
							 final RadioButton rb2 = new  RadioButton("Product Faulty and Eligible for 70 % loan");
							 final RadioButton rb3 = new  RadioButton("Product Faulty and not eligible for loan!");
							 ToggleGroup rbGroup = new ToggleGroup();
				             rb1.setWrapText(true);
				             rb2.setWrapText(true);
				             rb3.setWrapText(true);
				             rb1.setToggleGroup(rbGroup);
				             rb2.setToggleGroup(rbGroup);
				             rb3.setToggleGroup(rbGroup);
				             
				             HBox dBox = new HBox(15, label,rb1,rb2,rb3);
				             dBox.setStyle("-fx-background-color:rosybrown;-fx-border-width:15;"
										+ "-fx-border-color:tomato;-fx-font-weight:bold;");
				             pawnGridPane.add(dBox,0 ,18,1,1);
				             dBox.setVisible(false);
							 final Button pawn =  new Button("$Pawn$");
							 paymentGrid.add(pawn, 0, 5);
							
									// TODO Auto-generated method stub
									int pawnPrice = Integer.parseInt(pawnItems[newValue.intValue()].split("@")[1].substring(1));
									 pawn.setOnAction((e2)->{
										 
										     dBox.setVisible(true);
											paymentGrid.setVisible(false);
					                        Alert alert1 = new Alert(AlertType.CONFIRMATION);
											alert1.setTitle("Transaction status notification");
											alert1.setContentText(pawnItems[newValue.intValue()]+" request is under consideration our courier"
													+ " will collect the item by 12h00pm tommorrow,ensure that you entered the correct address.");
											alert1.show();
											rb1.setOnAction((event)->{
												if (rb1.isSelected()==true) {
												     dBox.setVisible(false);
													 releaseFundsToCustomer= IsproductAcceptablebyCompany(true);
												boolean successfullyLoaned = pawnSecondHandGoods(pawnPrice, releaseFundsToCustomer);
												if (successfullyLoaned) {
													Alert alertS = new Alert(AlertType.CONFIRMATION);
													alertS.setTitle("Transaction status notification");
													alertS.setContentText("A  loan of "+pawnItems[newValue2.intValue()].split("@")[1]+" has"
															+ " been succesfully paid to your "
													+banks[newValue2.intValue()]+" account."+"The loaned amount is payable within 30 days of reception interest is charged at 40% which "
															+ "includes a small deliver/courier service "
															+ "rate.You have an outstanding balance of R"+computeLoanAmountPayableWithInterest(pawnPrice)+" due to "+companyName+"."
															+"Disclaimer:Failure to repay the loan in the stipulated period,results in "+companyName+" claiming full posession "
																	+ "of your goods with privileges to sell your goods.You hereby surrender your ownership of the goods if "
																	+ "you don't meet the payment deadline. ");
													alertS.show();
													 txtTimeS = new Text("Successful agreement concluded on:".concat(getTimeStamp())+" with "+companyName);
													 txtTimeS.setEffect(ds);
													 txtTimeS.setEffect(ref);
													 txtTimeS.setStyle("-fx-background-color:lightblue");
													 txtTimeS.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
													 pawnGridPane.add(txtTimeS,0 ,20);
												}
											   }
											});

											rb2.setOnAction((event)->{
												if(rb2.isSelected()==true) {
													dBox.setVisible(false);
													releaseFundsToCustomer= IsproductAcceptablebyCompany(true);
													boolean successfullyLoaned = pawnSecondHandGoods((int)(reduceCompensationForFaultyGoods(pawnPrice)),
															releaseFundsToCustomer);
													if (successfullyLoaned) {
														Alert alertS = new Alert(AlertType.CONFIRMATION);
														alertS.setTitle("Transaction status notification");
														alertS.setContentText("A  loan of "+pawnItems[newValue2.intValue()].split("@")[1]+" has"
																+ " been succesfully paid to your "
																+banks[newValue2.intValue()]+" account.\"+\"The loaned amount is payable within 30 days of reception interest is charged "
																+ "at 40% which includes a small deliver/courier service rate.You have an outstanding balance of R"+computeLoanAmountPayableWithInterest(pawnPrice)+" due to "+companyName+"Disclaimer:Failure"
																+ " to repay the loan in the stipulated period,results in "+companyName+" claiming full posession of your goods with privileges to sell your goods.You hereby surrender"
																+ " your ownership of the goods if you don't meet the payment deadline.");
														alertS.show();
														txtTimeS = new Text("Successful agreement concluded on:".concat(getTimeStamp())+" with "+companyName);
														txtTimeS.setEffect(ds);
														txtTimeS.setEffect(ref);
														txtTimeS.setStyle("-fx-background-color:lightblue");
														txtTimeS.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
														pawnGridPane.add(txtTimeS,0 ,20);
													}
												}
											});
											rb3.setOnAction((event)->{
												if(rb3.isSelected()==true) {
													dBox.setVisible(false);
													Alert alertS = new Alert(AlertType.ERROR);
													alertS.setTitle("Transaction status notification");
													alertS.setContentText("We regret to notify you that your loan request was declined,since d your product was found to be faulty or somehow unfuctional");
													alertS.show();
													 txtTimeS = new Text(" Unsuccesfull agreement concluded on:".concat(getTimeStamp())+" with "+companyName);
													 txtTimeS.setEffect(ds);
													 txtTimeS.setEffect(ref);
													 txtTimeS.setStyle("-fx-background-color:lightblue");
													 txtTimeS.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
													 pawnGridPane.add(txtTimeS,0 ,20);
												}


											});

									 });

								 }
							 });
						 }
					 });
		  
		 
		//Add controls to the buyGridPane
		 mi2.setOnAction((event)->{
			 ContractMode = "Sell Mode";
			 smartGridPane.add(buyGridPane, 0, 0,10,10);
             imgView.setVisible(false);
			 tfFirstName1 = new TextField();
			 tfFirstName1.setPromptText("First Name");
			 tfFirstName1.setEffect(ds);
			 tfFirstName1.setEffect(ref);
			 tfFirstName2 = new TextField();
			 tfFirstName2.setPromptText("Second Name");
			 tfFirstName2.setEffect(ds);
			 tfFirstName2.setEffect(ref);
			 tfLastName = new TextField();
			 tfLastName.setEffect(ds);
			 tfLastName.setEffect(ref);
			 tfLastName.setPromptText("Last Name\\Surname");
			 this.Names = tfFirstName1.getText()+" "+tfFirstName2.getText()+" "+tfLastName.getText();
			 tfIDNum = new TextField();
			 tfIDNum.setPromptText("SA Valid ID Number");
			 tfIDNum.setEffect(ds);
			 tfIDNum.setEffect(ref);
			 this.idNumber = tfIDNum.getText();
			 
			 
			 
			 txtEmail = new TextField();
			 txtEmail.setPromptText("Email Adress");
			 tfIDNum.setEffect(ds);
			 txtEmail.setEffect(ref);
			 this.emailString = txtEmail.getText();

			 tfContNum = new TextField();
			 tfContNum.setPromptText("Contact number");
			 tfContNum.setEffect(ds);
			 tfContNum.setEffect(ref);
			 this.contactNum = tfContNum.getText();
			 final HBox contacts = new HBox(15, tfIDNum,tfContNum,txtEmail);
             contacts.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
						+ "-fx-border-color:maroon;-fx-font-weight:bold;");
			 
			 final TextArea  taAdress = new TextArea();
			 taAdress.setPromptText("Street Number:\n\rStreet Name:\n\rTown\\Surburb:\n\rCity:\n\rProvince:\n\rPostal Code:");
			 this.address = taAdress.getText();
			 ScrollBar scV = new ScrollBar();
			 scV.setOrientation(Orientation.VERTICAL);
			 
			 scV.valueProperty().addListener(ov->{
				 taAdress.setLayoutY(scV.getValue());
			 });
			
			 
			final HBox addressBox = new HBox(15, new Label("Physical Address(Needs to be the same address\r\n were delivery"
			 		+ "/dropoff services will rendered):"),taAdress,scV);
			 addressBox.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
						+ "-fx-border-color:maroon;-fx-font-weight:bold;");
			 
             final Label label = new Label("Pick your Gender:");
             final CheckBox c1 = new CheckBox("Male");
             final CheckBox c2 = new CheckBox("Female");
             final CheckBox c3 = new CheckBox("other");
             c1.setWrapText(true);
             c2.setWrapText(true);
             c3.setWrapText(true);
             
            final DatePicker dPicker = new DatePicker();
            final Label dL = new Label("Select Date of\r\nBirth:");
            final HBox gBox = new HBox(15, label,c1,c2,c3,dL,dPicker);
             gBox.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
						+ "-fx-border-color:maroon;-fx-font-weight:bold;");
             
			 taAdress.setEditable(true);
			 taAdress.setStyle("-fx-background-color:pink");
			 taAdress.setFont(Font.font("Cambria", 25));
			 taAdress.setEffect(ref);
			 taAdress.setEffect(ds);
			 final Text  txtbuy = new Text("BUY MODE");
			 txtbuy.setEffect(ds);
			 txtbuy.setEffect(ref);
			 exitButton.setVisible(true);
			 menuBar.setVisible(false);
			 pawnGridPane.setVisible(false);
			 buyGridPane.setVisible(true);
			 buyGridPane.add(txtbuy, 0, 0);
			 buyGridPane.add(tfFirstName1, 0, 1);
			 buyGridPane.add(tfFirstName2, 0, 2);
			 buyGridPane.add(gBox,0, 4);
			 buyGridPane.add(tfLastName, 0, 3);
			 buyGridPane.add(contacts, 0, 5);
			 buyGridPane.add(addressBox, 0, 6);
			 buyGridPane.add(idBox, 0,8);
			 buyGridPane.add(hbb1, 0, 9);
			 buyGridPane.add(hbb2, 0,10);

		 });
		 choose.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			 @Override
			 public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				 // TODO Auto-generated method stub
				     choose2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					 @Override
					 public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue2) {
						 GridPane paymentGrid = new GridPane();
							paymentGrid.setStyle("-fx-background-color:magenta;-fx-border-width:15;"
										+ "-fx-border-color:maroon;-fx-font-weight:bold;");
						 buyGridPane.add(paymentGrid,0 ,13);
						 final Text text = new Text(banks[newValue2.intValue()]+"\nPayment Account Details:");
						 paymentGrid.add(text, 0, 0);
						 paymentGrid.add(new Label("Account Holder:"), 0, 1);
						 paymentGrid.add(new TextField(), 1, 1);
						 paymentGrid.add(new Label("Account Number"), 0, 2);
						 paymentGrid.add(new TextField(), 1 ,2);
                         paymentGrid.add(new Label("Branch Code:"), 0, 3);
						 paymentGrid.add(new Label("Payment Amount:"), 0, 4);
						 paymentGrid.add(new TextField(), 1, 3);
                         String currentamount = purchaseItems[newValue.intValue()].split("@")[1];
						 TextField pt = new TextField(currentamount+",00");
						
						 pt.setEditable(false);
						 paymentGrid.add(pt,1, 4);
						 final Button pay =  new Button("Pay");
						 paymentGrid.add(pay, 0, 5);
						 
						  final Label label = new Label("Company claims to have delivered the \r\nitem you want to purchase to your location:");
						  label.setWrapText(true);
						  final RadioButton rb1 = new  RadioButton("Correct goods delivered");
						  final RadioButton rb2 = new  RadioButton("No goods have been deliverd to my location at this stage");
						  final RadioButton rb3 = new  RadioButton("Wrong delivery(This will alert the company\r\n to reprocess your delivery");
						  ToggleGroup rbGroup = new ToggleGroup();
						  rb1.setWrapText(true);
						  rb2.setWrapText(true);
						  rb3.setWrapText(true);
						  rb1.setToggleGroup(rbGroup);
						  rb2.setToggleGroup(rbGroup);
						  rb3.setToggleGroup(rbGroup);

						  HBox delBox = new HBox(15, label,rb1,rb2,rb3);
						  delBox.setStyle("-fx-background-color:rosybrown;-fx-border-width:15;"
								  + "-fx-border-color:tomato;-fx-font-weight:bold;");
						  buyGridPane.add(delBox,0 ,18,1,1);
						  delBox.setVisible(false);
						 
						 int itemPrice = Integer.parseInt(purchaseItems[newValue.intValue()].split("@")[1].substring(1));
						 pay.setOnAction((e2)->{

						    delBox.setVisible(true);
							paymentGrid.setVisible(false);
	                        Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Transaction status notification");
							alert.setContentText(purchaseItems[newValue.intValue()]+" request is under consideration our courier"
									+ " will collect the item by 12h00pm tommorrow,ensure that you entered the correct address.");
							alert.show();
							   rb1.setOnAction((event)->{
								      deliveredToCustomer = rb1.isSelected();
									 if (buySecondHandGoods(itemPrice) && (deliveredToCustomer==true)){
										 
										 releaseFundsToCompany = true;
										 customerBalance = customerBalance - itemPrice;
										 paymentGrid.setVisible(false);
										 
										 txtTimeS = new Text("Concluded agreement on:".concat(getTimeStamp())+" with "+companyName);
										 txtTimeS.setEffect(ds);
										 txtTimeS.setEffect(ref);
										 txtTimeS.setStyle("-fx-background-color:lightblue");
										 txtTimeS.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
										 buyGridPane.add(txtTimeS,0 ,19);
				                        Alert alert1 = new Alert(AlertType.CONFIRMATION);
										alert1.setTitle("Transaction status notification");
										alert1.setContentText(purchaseItems[newValue.intValue()].split("@")[0]+" was successfully purchased at an amount of "
												 +purchaseItems[newValue.intValue()].split("@")[1]);
										alert1.show();
										
									 }
									 
								});
							   rb2.setOnAction((event)->{
								         deliveredToCustomer = rb2.isSelected();
									     releaseFundsToCompany = false;
				                        Alert alert2 = new Alert(AlertType.ERROR);
										alert2.setTitle("Transaction status notification");
										alert2.setContentText(purchaseItems[newValue.intValue()]+" delivery hasn't arrived to your location"
										+ " within the anticipated time frame,we will urgently  evaluate the glitch.A new delivery slot will"
										+ " follow and you will be contacted telephonically or through email.Once the product has been deliverd to your "
										+ "desired destination,the transaction and agreement thus will conclude.");
										alert2.show();
								});
							   rb3.setOnAction((event)->{
								      deliveredToCustomer = rb3.isSelected();
									  releaseFundsToCompany = false;
				                        Alert alert3 = new Alert(AlertType.ERROR);
										alert3.setTitle("Transaction status notification");
										alert3.setContentText("We apologise for the discrepany and muddling your delivery.We will urgently fix the dlitch."
										+ "A new delivery slot will follow and you will be contacted telephonically or through email."
										+ "Once the product has been deliverd to your desired destination at current day service no additional charges,the transaction and agreement thus will conclude.");
										alert3.show();
							   });
						   });
						 }
					 });
				 }
			 });
				     
		 addID.setOnAction((event)->{
			 listView.setVisible(true);
			 final FileChooser fc = new FileChooser();
			 File selectedFile = fc.showOpenDialog(arg0);
			 String path = selectedFile.getAbsolutePath();
			 fileList.add(path);
			 listView.refresh();
		 });
		 exitButton.setOnAction((event)->{
             imgView.setVisible(true);
			 exitButton.setVisible(false);
			 menuBar.setVisible(true);
			 buyGridPane.setVisible(false);
			 pawnGridPane.setVisible(false);

		 });

	}
	
			 
	private String getTimeStamp() {
		Date date = new Date();
		String strDate = date.toString();

		return strDate;
	}
	private GridPane createGridPane() {

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(15, 15, 15, 15));
		gridPane.setStyle("-fx-background-color:lightblue;-fx-border-width:15;"
				+ "-fx-border-color:silver;-fx-font-weight:bold;-fx-font-size:15;");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		return gridPane;

	}

}
