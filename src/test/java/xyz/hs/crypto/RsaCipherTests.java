package xyz.hs.crypto;

import org.junit.Test;
import xyz.hs.crypto.cert.CerCert;
import xyz.hs.crypto.cert.PfxCert;
import xyz.hs.crypto.cipher.RsaCipher;
import xyz.hs.crypto.key.PrivateKeyManager;
import xyz.hs.crypto.key.PublicKeyManager;

/**
 * @author huangsui@yunrong.cn
 * Created on 2019/8/26
 */
public class RsaCipherTests {

    @Test
    public void test() throws Exception {

        test2();
        test2();
        test2();

    }

    private void test2(){
        String content = "Input length must be multiple of 16 when decrypting with padded cipher";

        CerCert cerCert = new CerCert();
        cerCert.setResPath("/test.cer");

        PfxCert pfxCert = new PfxCert();
        pfxCert.setResPath("/test.pfx");
        pfxCert.setStorePass("1234");

        PrivateKeyManager privateKeyManager = new PrivateKeyManager(pfxCert, "1234");
        PublicKeyManager publicKeyManager = new PublicKeyManager(cerCert);

        RsaCipher rsaEncrypt = new RsaCipher(privateKeyManager.getPrivateKey(), publicKeyManager.getPublicKey());

        long start = System.currentTimeMillis();
        String result = rsaEncrypt.encrypt(content, "UTF-8");
        System.out.println("RSA加密时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(result);

        long start2 = System.currentTimeMillis();
        result = rsaEncrypt.decrypt(result, "UTF-8");
        System.out.println("RSA解密时间："+(System.currentTimeMillis()-start2)+"ms");
        System.out.println(result);
    }
}
