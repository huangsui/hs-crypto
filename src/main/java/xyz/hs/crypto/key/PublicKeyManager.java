package xyz.hs.crypto.key;

import com.google.common.io.BaseEncoding;
import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.cert.CerCert;
import xyz.hs.crypto.util.StringUtil;

import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * @Author: huangsui
 * @Date: 2019/8/22 14:44
 * @Description:
 */
public class PublicKeyManager {

    private final CerCert cerCert;
    private PublicKey publicKey = null;

    public PublicKeyManager(CerCert cerCert) {
        this.cerCert = cerCert;
    }

    public PublicKeyManager(String resPath) {
        if(StringUtil.isNullOrEmpty(resPath)){
            throw new CryptoException("公钥证书路径不能为空");
        }
        CerCert cerCert = new CerCert();
        cerCert.setResPath(resPath);
        this.cerCert = cerCert;
    }

    public PublicKey getPublicKey(){
        if(publicKey==null){
            synchronized (this){
                if(publicKey==null){
                    Certificate certificate = cerCert.getCertificate();
                    publicKey = certificate.getPublicKey();
                }
            }
        }
        return publicKey;
    }

    public String getPublicKeyString()throws CryptoException {
        return BaseEncoding.base64().encode(this.getPublicKey().getEncoded());
    }

}
