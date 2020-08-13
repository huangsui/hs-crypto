package xyz.hs.crypto;

import xyz.hs.crypto.cipher.AesCipher;
import xyz.hs.crypto.cipher.RsaCipher;
import xyz.hs.crypto.common.Constant;
import xyz.hs.crypto.msg.Message;
import xyz.hs.crypto.msg.MessageFormat;
import xyz.hs.crypto.sign.Verifier;
import xyz.hs.crypto.sign.Signer;

/**
 * @author huangsui
 * Created on 2019/8/23
 */
public class SimpleCryptor {

    private Signer signer;
    private Verifier verifier;
    private AesCipher aesCipher;
    private RsaCipher rsaCipher;
    private String charset;

    public String encryptAndSign(String data){

        String key = aesCipher.genKey();
        String salt = aesCipher.genSalt();
        String dataEncrypted = aesCipher.encrypt(data, key, salt, charset);
        String keyRsa = rsaCipher.encrypt(key, charset);
        String saltRsa = rsaCipher.encrypt(salt, charset);

        Message msg = new Message(dataEncrypted, keyRsa, saltRsa);
        String msg4Sign = MessageFormat.formatToSign(msg);

        String sign = signer.sign(msg4Sign, Constant.SIGN_TYPE_RSA2, charset);

        msg.setSign(sign);
        return MessageFormat.formatToJson(msg);
    }

    public String decryptAfterVerify(String data){
        Message msg = MessageFormat.parseFromJson(data);

        String signMaterial = MessageFormat.formatToSign(msg);
        boolean verify = verifier.verify(signMaterial, msg.getSign(), charset);

        if(!verify){
            throw new CryptoException("报文验签失败");
        }

        String key = rsaCipher.decrypt(msg.getKey(), charset);
        String salt = rsaCipher.decrypt(msg.getSalt(), charset);
        String result = aesCipher.decrypt(msg.getData(), key, salt, charset);
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
