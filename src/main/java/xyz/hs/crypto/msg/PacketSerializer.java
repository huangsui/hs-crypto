package xyz.hs.crypto.msg;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import xyz.hs.crypto.CryptoException;

/**
 * @author huangsui
 * Created on 2019/9/4
 */
public class PacketSerializer {

    public static String serializeForSign(Packet packet){
        StringBuffer keyValues = new StringBuffer();
        keyValues.append("data=");
        keyValues.append(packet.getData());
        keyValues.append("&key=");
        keyValues.append(packet.getKey());
        keyValues.append("&salt=");
        keyValues.append(packet.getSalt());
        return keyValues.toString();
    }

    public static String serialize(Packet packet){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", new JsonPrimitive(packet.getData()));
        jsonObject.add("key", new JsonPrimitive(packet.getKey()));
        jsonObject.add("salt", new JsonPrimitive(packet.getSalt()));
        jsonObject.add("sign", new JsonPrimitive(packet.getSign()));
        return jsonObject.toString();
    }

    public static Packet deserialize(String message){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();
        JsonElement data = jsonObject.get("data");
        JsonElement key = jsonObject.get("key");
        JsonElement sign = jsonObject.get("sign");
        JsonElement salt = jsonObject.get("salt");
        if(data==null || key==null || sign==null || salt==null){
            throw new CryptoException("报文解析：加密数据拆解异常，请确认发送的加密数据格式是否正确");
        }
        return new Packet(data.getAsString(), key.getAsString(), salt.getAsString(), sign.getAsString());
    }


}
