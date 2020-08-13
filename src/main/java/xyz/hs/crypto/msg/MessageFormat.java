package xyz.hs.crypto.msg;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javafx.scene.input.DataFormat;
import xyz.hs.crypto.CryptoException;

/**
 * @author huangsui
 * Created on 2019/9/4
 */
public class MessageFormat {

    public static String formatToSign(Message msg){
        StringBuffer keyValues = new StringBuffer();
        keyValues.append("data=");
        keyValues.append(msg.getData());
        keyValues.append("&key=");
        keyValues.append(msg.getKey());
        keyValues.append("&salt=");
        keyValues.append(msg.getSalt());
        return keyValues.toString();
    }

    public static String formatToJson(Message msg){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", new JsonPrimitive(msg.getData()));
        jsonObject.add("key", new JsonPrimitive(msg.getKey()));
        jsonObject.add("salt", new JsonPrimitive(msg.getSalt()));
        jsonObject.add("sign", new JsonPrimitive(msg.getSign()));
        return jsonObject.toString();
    }

    public static Message parseFromJson(String json){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        JsonElement data = jsonObject.get("data");
        JsonElement key = jsonObject.get("key");
        JsonElement sign = jsonObject.get("sign");
        JsonElement salt = jsonObject.get("salt");
        if(data==null || key==null || sign==null || salt==null){
            throw new CryptoException("报文解析：加密数据拆解异常，请确认发送的加密数据格式是否正确");
        }
        return new Message(data.getAsString(), key.getAsString(), salt.getAsString(), sign.getAsString());
    }


}
