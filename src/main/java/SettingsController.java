import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SettingsController {
    QRScanner scanner = new QRScanner();
    String address;

    //This section is for the change address section
    public SettingsController() {
    		super();
    }

    public boolean addressWrite(String newAddress) {
    		PrintWriter out = null;
    		try {
    			out = new PrintWriter("ETHaddress");
            out.println(newAddress);
            System.out.println("file printed");
        } catch (FileNotFoundException e) {
            System.out.println("ETHaddress file not found");
        } finally {
        		if (null != out) out.close();
        }
        return true;
    }

    public String addressRead(){
        try (BufferedReader br = new BufferedReader(new FileReader("ETHaddress"))) {
            address = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ETHaddress file not found");
            return null;
        }
        return address;
    }

    public String scanCode(){
        return scanner.scanCodeContinuously();
    }


    public String returnMiningAddress(){
        if (addressRead()!=null){
//            System.out.println(addressRead());
//            System.out.println("the supplied mining address is at:" + addressRead());
            return addressRead();
        }
        else{
//            System.out.println("address unavailable");
            return "address unavailable";
        }
    }
}
