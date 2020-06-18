package xyz.hs.crypto;

import org.junit.Test;
import xyz.hs.crypto.key.PrivateKeyManager;
import xyz.hs.crypto.key.PublicKeyManager;

/**
 * @author huangsui@yunrong.cn
 * Created on 2019/9/29
 */
public class TestPacketFile {

    @Test
    public void test(){

        String packet = TestFileSupport.readText(TestFileSupport.getFilePath("/test.json"));
        PrivateKeyManager privateKeyManager = new PrivateKeyManager("/test.pfx", "1234");
        PublicKeyManager publicKeyManager = new PublicKeyManager("/test.cer");
        Crypto crypto = CryptoFactory.create(privateKeyManager.getPrivateKey(), publicKeyManager.getPublicKey());

        long start = System.currentTimeMillis();
        String targetPacket = crypto.encrypt(packet);
        System.out.println("加密加签总时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(targetPacket);

        long start2 = System.currentTimeMillis();
        String sourcePacket = crypto.decrypt(targetPacket);
        System.out.println("验签解密总时间："+(System.currentTimeMillis()-start2)+"ms");
        System.out.println(sourcePacket);
    }
}
