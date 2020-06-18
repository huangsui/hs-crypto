package xyz.hs.crypto.sign;

/**
 * @Author: huangsui
 * @Date: 2019/8/22 10:39
 * @Description:
 */
public interface Signer {

    /**
     * 对内容加签
     *
     * @param sourceContent 待加签内容
     * @param signType 签名类型，如RSA、RSA2
     * @param charset 字符集
     * @return 签名值
     */
    String sign(String sourceContent, String signType, String charset);
}
