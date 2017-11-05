
public class BitCoinKeyPair {
	private String pubKey;
	private String privKey;
	
	public BitCoinKeyPair(String privKey, String pubKey){
		this.privKey=privKey;
		this.pubKey = pubKey;
	}

	public String getPubKey() {
		return pubKey;
	}

	public String getPrivKey() {
		return privKey;
	}
	
	
}
