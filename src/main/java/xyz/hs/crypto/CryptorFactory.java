package xyz.hs.crypto;

import xyz.hs.crypto.cipher.AesCipher;
import xyz.hs.crypto.cipher.RsaCipher;
import xyz.hs.crypto.key.PrivateKeyManager;
import xyz.hs.crypto.key.PublicKeyManager;
import xyz.hs.crypto.sign.RsaSigner;
import xyz.hs.crypto.sign.RsaVerifier;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author huangsui
 * Created on 2019/9/4
 */
public class CryptorFactory {

    public static SimpleCryptor create(String cerResPath, String pfxResPath, String pfxPass){
        PrivateKeyManager privateKeyManager = new PrivateKeyManager(pfxResPath, pfxPass);
        PublicKeyManager publicKeyManager = new PublicKeyManager(cerResPath);
        return CryptorFactory.create(privateKeyManager.getPrivateKey(), publicKeyManager.getPublicKey());
    }

    public static SimpleCryptor create(PrivateKey privateKey, PublicKey publicKey){
        SimpleCryptor crypto = new SimpleCryptor();

        //aes加密
        AesCipher aes = new AesCipher();
        crypto.setAesCipher(aes);

        //rsa加密
        RsaCipher rsaEncrypt = new RsaCipher(privateKey, publicKey);
        crypto.setRsaCipher(rsaEncrypt);

        //签名
        RsaSigner rsaSigner = new RsaSigner(privateKey);
        crypto.setSigner(rsaSigner);

        //验签
        RsaVerifier rsaSignChecker = new RsaVerifier(publicKey);
        crypto.setVerifier(rsaSignChecker);

        return crypto;
    }

}
