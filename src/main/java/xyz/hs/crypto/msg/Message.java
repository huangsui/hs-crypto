package xyz.hs.crypto.msg;

/**
 * @author huangsui
 * Created on 2019/8/23
 */
public class Message {
    private String data;
    private String key;
    private String salt;
    private String sign;

    public Message(String data, String key, String salt) {
        this.data = data;
        this.key = key;
        this.salt = salt;
    }

    public Message(String data, String key, String salt, String sign) {
        this.data = data;
        this.key = key;
        this.salt = salt;
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
