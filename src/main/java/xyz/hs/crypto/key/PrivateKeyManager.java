package xyz.hs.crypto.key;

import com.google.common.io.BaseEncoding;
import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.cert.PfxCert;
import xyz.hs.crypto.util.StringUtil;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Enumeration;

/**
 * @Author: huangsui
 * @Date: 2019/8/22 14:44
 * @Description:
 */
public class PrivateKeyManager {

    private final PfxCert pfxCert;
    private final String keyPass;

    private PrivateKey privateKey = null;
    private String certAlias;

    public PrivateKeyManager(PfxCert pfxCert, String keyPass) {
        this.pfxCert = pfxCert;
        this.keyPass = keyPass;
    }

    public PrivateKeyManager(String resPath, String keyPass) {
        if(StringUtil.isNullOrEmpty(resPath)){
            throw new CryptoException("私钥证书路径不能为空");
        }
        PfxCert pfxCert = new PfxCert();
        pfxCert.setResPath(resPath);
        pfxCert.setStorePass(keyPass);
        this.pfxCert = pfxCert;
        this.keyPass = keyPass;
    }

    public PrivateKey getPrivateKey(){
        if(privateKey==null){
            synchronized (this){
                if(privateKey==null){
                    try{
                        KeyStore keyStore = pfxCert.getKeyStore();
                        if(certAlias==null){
                            Enumeration<String> enums = null;
                            enums = keyStore.aliases();
                            certAlias = enums.nextElement();
                        }
                        privateKey = (PrivateKey)keyStore.getKey(certAlias, keyPass.toCharArray());
                    }catch (Exception e){
                        throw new CryptoException(e);
                    }
                }
            }
        }
        return privateKey;
    }

    public String getPrivateKeyString()throws CryptoException {
        return BaseEncoding.base64().encode(this.getPrivateKey().getEncoded());
    }

    public String getCertAlias() {
        return certAlias;
    }

    public void setCertAlias(String certAlias) {
        this.certAlias = certAlias;
    }

}
