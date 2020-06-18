package xyz.hs.crypto;

import com.google.common.io.BaseEncoding;
import org.junit.Test;
import xyz.hs.crypto.cert.PfxCert;
import xyz.hs.crypto.key.PrivateKeyManager;

import java.security.PrivateKey;

/**
 * @author huangsui@yunrong.cn
 * Created on 2019/8/22
 */
public class PrivateKeyManagerTests {

    @Test
    public void test(){
        PfxCert pfxCertificate = new PfxCert();
        pfxCertificate.setResPath("/test.pfx");
        pfxCertificate.setStorePass("1234");
        PrivateKeyManager privateKeyProvider = new PrivateKeyManager(pfxCertificate, "1234");
        PrivateKey privateKey = privateKeyProvider.getPrivateKey();
        String publicKeyBase64 = BaseEncoding.base64().encode(privateKey.getEncoded());
        System.out.println(publicKeyBase64);
    }
}
