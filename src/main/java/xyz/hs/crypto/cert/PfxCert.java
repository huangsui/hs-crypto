package xyz.hs.crypto.cert;

import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.util.StringUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author huangsui
 * Created on 2019/8/22
 */
public class PfxCert {

    private static final String KEYSTORE_TYPE_PKCS12 = "PKCS12";

    private String resPath;
    private String filePath;
    private String storePass = null;

    public KeyStore getKeyStore(){
        if(storePass==null){
            throw new CryptoException("请配置storePass参数");
        }

        if(StringUtil.isNullOrEmpty(filePath)){
            try(InputStream is = this.getClass().getResourceAsStream(resPath);){
                KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE_PKCS12);
                ks.load(is, storePass.toCharArray());
                return ks;
            }catch (Exception e){
                throw new CryptoException("获取pfx证书异常", e);
            }
        }else {
            try(FileInputStream fis = new FileInputStream(filePath);) {
                KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE_PKCS12);
                ks.load(fis, storePass.toCharArray());
                return ks;
            }catch (Exception e){
                throw new CryptoException("获取pfx证书异常", e);
            }
        }

    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }
}
