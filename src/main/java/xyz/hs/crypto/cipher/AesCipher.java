package xyz.hs.crypto.cipher;

import com.google.common.io.BaseEncoding;
import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Author: huangsui
 * @Date: 2019/8/21 19:24
 * @Description:
 */
public class AesCipher {

    private static final Logger logger = LoggerFactory.getLogger(AesCipher.class);

    private static final String AES_CBC_PCK5_ALGORITHM = "AES/CBC/PKCS5Padding";
    private final String AES_ALGORITHM = "AES";

    public String genSalt(){
        return reborn(String.valueOf(System.currentTimeMillis()), 16);
    }

    public String genKey(){
        return reborn(String.valueOf(System.currentTimeMillis()+new Random().nextInt()), 6);
    }

    private String reborn(String str, int neededLength){
        int len = str.length();
        if(len>=neededLength){
            return str.substring(0, neededLength);
        }else {
            int count = neededLength - len;
            StringBuilder sb = new StringBuilder();
            while (count>0){
                sb.append("0");
                count--;
            }
            return str+sb.toString();
        }
    }

    public String encrypt(String content, String key, String salt, String charset) {
        try {
            byte[] contentBytes = StringUtil.isNullOrEmpty(charset)?content.getBytes():content.getBytes(charset);
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK5_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, genSecretKey(key), iv);
            byte[] encryptBytes = cipher.doFinal(contentBytes);
            return BaseEncoding.base64().encode(encryptBytes);
        }catch (Exception e){
            throw new CryptoException(e);
        }
    }

    public String decrypt(String content, String key, String salt, String charset)
            throws CryptoException {
        try {
            byte[] contentBytes = BaseEncoding.base64().decode(content);
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK5_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, genSecretKey(key), iv);
            byte[] encryptBytes = cipher.doFinal(contentBytes);
            return StringUtil.isNullOrEmpty(charset)?new String(encryptBytes):new String(encryptBytes, charset);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    private SecretKey genSecretKey(String password) throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        KeyGenerator keygen = KeyGenerator.getInstance(AES_ALGORITHM);
        keygen.init(128, random);
        return keygen.generateKey();
    }

    private SecretKey genSecretKey2(String password) throws Exception {
        byte[] raw = password.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_ALGORITHM);
        return skeySpec;
    }

}
