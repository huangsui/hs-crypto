package xyz.hs.crypto;

import xyz.hs.crypto.cipher.AesCipher;
import xyz.hs.crypto.cipher.RsaCipher;
import xyz.hs.crypto.common.Constant;
import xyz.hs.crypto.msg.Packet;
import xyz.hs.crypto.msg.PacketSerializer;
import xyz.hs.crypto.sign.Verifier;
import xyz.hs.crypto.sign.Signer;

/**
 * @author huangsui
 * Created on 2019/8/23
 */
public class Crypto {

    private Signer signer;
    private Verifier verifier;
    private AesCipher aesCipher;
    private RsaCipher rsaCipher;
    private String charset;

    public String encrypt(String data){

        String key = aesCipher.genKey();
        String salt = aesCipher.genSalt();
        String dataEncrypted = aesCipher.encrypt(data, key, salt, charset);
        String keyRsa = rsaCipher.encrypt(key, charset);
        String saltRsa = rsaCipher.encrypt(salt, charset);

        Packet spec = new Packet(dataEncrypted, keyRsa, saltRsa);
        String msg = PacketSerializer.serializeForSign(spec);

        String sign = signer.sign(msg, Constant.SIGN_TYPE_RSA2, charset);

        spec.setSign(sign);
        return PacketSerializer.serialize(spec);
    }

    public String decrypt(String data){
        Packet spec = PacketSerializer.deserialize(data);

        String signMaterial = PacketSerializer.serializeForSign(spec);
        boolean verify = verifier.verify(signMaterial, spec.getSign(), charset);

        if(!verify){
            throw new CryptoException("报文验签失败");
        }

        String key = rsaCipher.decrypt(spec.getKey(), charset);
        String salt = rsaCipher.decrypt(spec.getSalt(), charset);
        String result = aesCipher.decrypt(spec.getData(), key, salt, charset);
        return result;
    }

    public Signer getSigner() {
        return signer;
    }

    public void setSigner(Signer signer) {
        this.signer = signer;
    }

    public Verifier getVerifier() {
        return verifier;
    }

    public void setVerifier(Verifier verifier) {
        this.verifier = verifier;
    }

    public AesCipher getAesCipher() {
        return aesCipher;
    }

    public void setAesCipher(AesCipher aesCipher) {
        this.aesCipher = aesCipher;
    }

    public RsaCipher getRsaCipher() {
        return rsaCipher;
    }

    public void setRsaCipher(RsaCipher rsaCipher) {
        this.rsaCipher = rsaCipher;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
