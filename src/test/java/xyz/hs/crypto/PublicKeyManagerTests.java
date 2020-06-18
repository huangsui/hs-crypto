package xyz.hs.crypto;

import com.google.common.io.BaseEncoding;
import org.junit.Test;
import xyz.hs.crypto.cert.CerCert;
import xyz.hs.crypto.key.PublicKeyManager;

import java.security.PublicKey;

/**
 * @author huangsui@yunrong.cn
 * Created on 2019/8/22
 */
public class PublicKeyManagerTests {

    @Test
    public void test(){
        CerCert cerCert = new CerCert();
        cerCert.setResPath("/test.cer");
        PublicKeyManager publicKeyProvider = new PublicKeyManager(cerCert);
        PublicKey publicKey = publicKeyProvider.getPublicKey();
        String publicKeyBase64 = BaseEncoding.base64().encode(publicKey.getEncoded());
        System.out.println(publicKeyBase64);
    }
}
