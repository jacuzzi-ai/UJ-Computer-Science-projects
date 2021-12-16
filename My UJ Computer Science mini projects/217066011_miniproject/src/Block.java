import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javafx.stage.Stage;

public class Block implements Serializable{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String currenHashCode;
    private int nonce;
   
    private long timeStamp;
	private String previoushashCode;
	
	private SmartContractRecord smartContractRecord;
   
	public Block() {
		// TODO Auto-generated constructor stub
	}
	public Block(SmartContractRecord smartContractRecord, String previoushashCode) {
		this.smartContractRecord = smartContractRecord;
		this.previoushashCode = previoushashCode;
		this.nonce = 0;
		this.timeStamp = System.currentTimeMillis();
		this.currenHashCode = calculateBlockHash();
		
	}
	public String calculateBlockHash() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(previoushashCode);
		sb.append(nonce);
		sb.append(timeStamp);
		sb.append(smartContractRecord); 
		return hash(sb.toString().getBytes());
	}
	
	public String getCurrenHashCode() {
		return currenHashCode;
	}
	public String getPrevioushashCode() {
		return previoushashCode;
	}

	public SmartContractRecord getSmartContractRecord() {
		return smartContractRecord;
	}
	//https://cryptocurrencyhub.io/implementing-a-simple-proof-of-work-algorithm-for-the-blockchain-bdcd50faac18
	//https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
    public void  mineBlock(int difficulty) {
    	String target = new String(new char[difficulty]).replace('\0', '0'); //Creats a string object with difficulty * "0" 
		while(!currenHashCode.substring( 0, difficulty).equals(target)) {
			this.nonce ++;
			currenHashCode = calculateBlockHash();
		}
		System.out.println("Block Successfully Mined!!! : " + currenHashCode);
    }
    

	/**
	 * converts byte array to hex string representation
	 * @param arrayBytes
	 * @return hex string
	 */
	private static String byteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
	public static byte[] serialize(Block b) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(b);
	    return out.toByteArray();
	}
	public static Block deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    Object object = is.readObject();
	    Block block = null;
	    if(object instanceof Block)
	    {
	     block =(Block)object;
	    }
	    return block;
	}
    /**
	 * hashes a file using the hashing algorithm ALGORITHM
	 * @param file a byte array
	 * @return hex 
	 */
	public static String hash(byte[] myFile) {
		
	    final String ALGORITHM = "SHA-256";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(myFile);
		byte[] digest = md.digest();
		return byteArrayToHexString(digest);
	}
	public void setCurrenHashCode(String currenHashCode) {
		this.currenHashCode = currenHashCode;
	}
	public void setPrevioushashCode(String previoushashCode) {
		this.previoushashCode = previoushashCode;
	}


	public void setSmartContractRecord(SmartContractRecord smartContractRecord) {
		this.smartContractRecord = smartContractRecord;
	}
	    
}
