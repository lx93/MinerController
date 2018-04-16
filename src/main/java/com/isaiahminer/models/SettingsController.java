package com.isaiahminer.models;

import com.isaiahminer.controllers.QRScanner;

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
            System.err.println("ETHaddress file not found");
        } finally {
        		if (null != out) out.close();
        }
        return true;
    }

    public String addressRead(){
        try (BufferedReader br = new BufferedReader(new FileReader("ETHaddress"))) {
            address = br.readLine();
        } catch (FileNotFoundException e) {
        		System.err.println("ETHaddress file not found. Creating a default one.");
        		addressWrite("ethereum:0x54d0b70baa6577b1118dde00de22bdbcafb1e8aa");
        		address = "ethereum:0x54d0b70baa6577b1118dde00de22bdbcafb1e8aa";
        } catch (IOException e) {
	    		System.err.println("Failed to read ETHaddress. Trying to overide with a default one.");
	    		addressWrite("ethereum:0x54d0b70baa6577b1118dde00de22bdbcafb1e8aa");
	    		address = "ethereum:0x54d0b70baa6577b1118dde00de22bdbcafb1e8aa";
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
