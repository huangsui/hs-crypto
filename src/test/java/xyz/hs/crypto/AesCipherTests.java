package xyz.hs.crypto;


import org.junit.Assert;
import org.junit.Test;
import xyz.hs.crypto.cipher.AesCipher;

import java.util.Random;

/**
 * @Author: huangsui
 * @Date: 2019/8/21 18:54
 * @Description:
 */
public class AesCipherTests {

    String encryptKey = "ed16b1f8a9e648d4";

    @Test
    public void genSalt() {
        AesCipher aes = new AesCipher();
        String salt = aes.genSalt();
        Assert.assertEquals(salt.length(), 16);
        System.out.println(salt);
    }

    @Test
    public void testRandom(){
        long start = System.currentTimeMillis();
        Random random = new Random();
        System.out.println(random.nextInt());
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void genKey(){
        AesCipher aes = new AesCipher();
        String key = aes.genKey();
        Assert.assertEquals(key.length(), 6);
        System.out.println(key);
    }

    @Test
    public void test() throws Exception {
        String content = "Input length must be multiple of 16 when decrypting with padded cipher";
        String content2 = "ed16b1f8a9e648d4 ed16b1f8a9e648d4 ed16b1f8a9e648d4 ed16b1f8a9e648d4";
        test2(content);
        test2(content2);
        test2(content+"3");

    }

    private void test2(String content){
        AesCipher aes = new AesCipher();

        String key = aes.genKey();
        String salt = aes.genSalt();

        long start = System.currentTimeMillis();
        String result = aes.encrypt(content, key, salt, "UTF-8");
        System.out.println("AES加密时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(result);

        long start2 = System.currentTimeMillis();
        result = aes.decrypt(result, key, salt, "UTF-8");
        System.out.println("AES解密时间："+(System.currentTimeMillis()-start2)+"ms");
        System.out.println(result);
    }

}
