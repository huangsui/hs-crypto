package xyz.hs.crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: huangsui@yunrong.cn
 * @Date: 2019/6/21 20:32
 * @Description:
 */
public class FileSupportTest {

    public static String getFilePath(String path){
        URL url = FileSupportTest.class.getResource(path);
        return url.getFile();
    }

    public static String readText(String filePath) {
        StringBuffer stringBuffer = new StringBuffer();
        try (FileReader reader = new FileReader(filePath);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
