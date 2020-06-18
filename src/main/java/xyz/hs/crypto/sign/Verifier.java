package xyz.hs.crypto.sign;

/**
 * @Author: huangsui
 * @Date: 2019/8/22 11:09
 * @Description:
 */
public interface Verifier {
    /**
     * 对签名进行验签
     *
     * @param sourceContent 加签内容
     * @param signature 签名值
     * @param charset 字符集
     * @return true 验签通过，false 验签不通过
     */
    boolean verify(String sourceContent, String signature, String charset);
}
