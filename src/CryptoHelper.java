
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;

public class CryptoHelper {
	
	public static WalletController getKeyPair(){
		String[] values = new String[2];;
		ECKey key = new ECKey();

		values[0] = key.getPrivateKeyEncoded(NetworkParameters.prodNet()).toString();

		byte[] pub = ECKey.publicKeyFromPrivate(key.getPrivKey(), false);

		Address add = new Address(NetworkParameters.prodNet(), Utils.sha256hash160(pub));
		values[1] =  add.toBase58();
		WalletController kp = new WalletController(values[0], values[1]);
		return kp;
	}
	 
	 public static String toHex(byte[] a){
		 String re3 = Hex.encodeHexString(a);
		 return re3;
	 }

}


/*
			BitCoinKeyPair kp = CryptoHelper.getKeyPair();
	      	System.out.println("Priv: "+kp.getPrivKey());
	      	System.out.println("Pub: "+kp.getPubKey());
*/
