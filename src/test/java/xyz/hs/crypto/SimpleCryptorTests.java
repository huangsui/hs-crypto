package xyz.hs.crypto;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangsui
 * Created on 2019/8/22
 */
public class SimpleCryptorTests {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCryptorTests.class);

    @Test
    public void test(){
        //请求方
        String data = "如何写一个使用证书加密签名的工具包";
        SimpleCryptor cryptor4Req = CryptorFactory.create("/cert/b.cer", "/cert/a.pfx", "123456");
        String msg = cryptor4Req.encryptAndSign(data);
        logger.info("加密签名结果：{}", msg);

        //接收方
        SimpleCryptor cryptor4Recv = CryptorFactory.create("/cert/a.cer", "/cert/b.pfx", "1234");
        data = cryptor4Recv.decryptAfterVerify(msg);
        logger.info("验签解密结果：{}", data);
    }



    @Test
    public void test1(){

        //请求方A
        SimpleCryptor cryptoA = CryptorFactory.create("/cert/b.cer", "/cert/a.pfx", "123456");
        //接收方B
        SimpleCryptor cryptoB = CryptorFactory.create("/cert/a.cer", "/cert/b.pfx", "1234");

        for (int i = 0; i < 10; i++) {
            //加密签名
            String data = "如何写一个使用证书加密签名的工具包";
            long start = System.currentTimeMillis();
            String msg = cryptoB.encryptAndSign(data);
            logger.info("加密签名总耗时：{} ms", (System.currentTimeMillis()-start));
            logger.info("加密签名结果：{}", msg);

            //验签解密
            start = System.currentTimeMillis();
            data = cryptoA.decryptAfterVerify(msg);
            logger.info("验签解密总耗时：{} ms", (System.currentTimeMillis()-start));
            logger.info("验签解密结果：{}", data);
        }


    }

}
