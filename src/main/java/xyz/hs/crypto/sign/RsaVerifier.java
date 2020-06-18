package xyz.hs.crypto.sign;

import com.google.common.io.BaseEncoding;
import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.common.Constant;
import xyz.hs.crypto.util.StringUtil;

import java.security.PublicKey;
import java.security.Signature;

/**
 * @author huangsui
 * Create on 2019/8/22 11:14
 */
public class RsaVerifier implements Verifier {

    private final PublicKey pubKey;

    public RsaVerifier(PublicKey pubKey) {
        this.pubKey = pubKey;
    }

    public boolean verify(String sourceContent, String signature, String charset) {
        return rsa256Verify(sourceContent, signature, charset);
    }

    public boolean rsa256Verify(String content, String sign, String charset) throws CryptoException {
        return verify(content, sign, Constant.SIGN_SHA256RSA_ALGORITHMS, charset );
    }

    public boolean rsaVerify(String content, String sign, String charset) throws CryptoException {
        return verify(content, sign, Constant.SIGN_ALGORITHMS, charset );
    }

    private boolean verify(String content, String sign, String algorithm, String charset) throws CryptoException {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(pubKey);
            if (StringUtil.isNullOrEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            return signature.verify(BaseEncoding.base64().decode(sign));
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
