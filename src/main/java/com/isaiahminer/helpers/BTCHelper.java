package com.isaiahminer.helpers;

import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;

import com.isaiahminer.controllers.ui.WalletController;

public class BTCHelper {

	public static WalletController getKeyPair() {
		String[] values = new String[2];
		ECKey key = new ECKey();

		values[0] = key.getPrivateKeyEncoded(NetworkParameters.prodNet()).toString();

		byte[] pub = ECKey.publicKeyFromPrivate(key.getPrivKey(), false);

		Address add = new Address(NetworkParameters.prodNet(), Utils.sha256hash160(pub));
		values[1] = add.toBase58();
		WalletController kp = new WalletController(values[0], values[1]);
		return kp;
	}

	public static String toHex(byte[] a) {
		String re3 = Hex.encodeHexString(a);
		return re3;
	}
}

//	public static void createWallet(){
//		Wallet wallet = new Wallet(Context context);
//	}


/*
			BitCoinKeyPair kp = CryptoHelper.getKeyPair();
	      	System.out.println("Priv: "+kp.getPrivKey());
	      	System.out.println("Pub: "+kp.getPubKey());
*/
