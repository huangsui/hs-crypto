package xyz.hs.crypto.sign;

import com.google.common.io.BaseEncoding;
import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.common.Constant;
import xyz.hs.crypto.util.StringUtil;

import java.security.PrivateKey;
import java.security.Signature;

/**
 * @Author: huangsui
 * @Date: 2019/8/22 10:40
 * @Description:
 */
public class RsaSigner implements Signer {

    private final PrivateKey priKey;

    public RsaSigner(PrivateKey priKey) {
        this.priKey = priKey;
    }

    @Override
    public String sign(String sourceContent, String signType, String charset) {
        if (Constant.SIGN_TYPE_RSA.equals(signType)) {
            return rsaSign(sourceContent, charset);
        } else if (Constant.SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256Sign(sourceContent, charset);
        } else {
            throw new CryptoException("Sign Type is Not Support : signType=" + signType);
        }
    }

    /**
     * sha256WithRsa 加签
     *
     * @param content
     * @param charset
     * @return
     * @throws CryptoException
     */
    public String rsa256Sign(String content, String charset) throws CryptoException {
        return baseSign(content, Constant.SIGN_SHA256RSA_ALGORITHMS, charset);
    }

    /**
     * sha1WithRsa 加签
     *
     * @param content
     * @param charset
     * @return
     * @throws CryptoException
     */
    public String rsaSign(String content, String charset) throws CryptoException {
        return baseSign(content, Constant.SIGN_ALGORITHMS, charset);
    }

    protected String baseSign(String content, String algorithm, String charset){
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(priKey);

            if (StringUtil.isNullOrEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            byte[] signed = signature.sign();
            return BaseEncoding.base64().encode(signed);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

}
