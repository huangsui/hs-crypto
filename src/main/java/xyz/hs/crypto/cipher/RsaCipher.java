package xyz.hs.crypto.cipher;

import com.google.common.io.BaseEncoding;
import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.util.StringUtil;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author huangsui
 * Created on 2019/8/22
 */
public class RsaCipher {

    /** RSA最大加密明文大小  */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** RSA最大解密密文大小   */
    private static final int MAX_DECRYPT_BLOCK = 256;//RSA1024=128. MAX_DECRYPT_BLOCK应等于密钥长度/8, 所以RSA2048最大解密长度应为256.
    private final String ALGORITHM_RSA = "RSA";

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RsaCipher(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String encrypt(String content, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] data = StringUtil.isNullOrEmpty(charset) ? content.getBytes(): content.getBytes(charset);
            String result = BaseEncoding.base64().encode(this.doFinalByBlock(cipher, data, MAX_ENCRYPT_BLOCK));
            return result;
        }catch (Exception e){
            throw new CryptoException(e);
        }
    }

    public String decrypt(String content, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedData = StringUtil.isNullOrEmpty(charset)? BaseEncoding.base64().decode(content)
                    : BaseEncoding.base64().decode(content);
            byte[] decryptedData = this.doFinalByBlock(cipher, encryptedData, MAX_DECRYPT_BLOCK);
            return StringUtil.isNullOrEmpty(charset) ? new String(decryptedData)
                    : new String(decryptedData, charset);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    private byte[] doFinalByBlock(Cipher cipher, byte[] data, int blockSize) throws Exception {
        int inputLen = data.length;
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();){
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > blockSize) {
                    cache = cipher.doFinal(data, offSet, blockSize);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * blockSize;
            }
            return out.toByteArray();
        }
    }

}
