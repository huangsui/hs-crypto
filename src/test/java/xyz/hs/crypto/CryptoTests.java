package xyz.hs.crypto;

import org.junit.Test;

/**
 * @author huangsui@yunrong.cn
 * Created on 2019/8/22
 */
public class CryptoTests {

    @Test
    public void test(){

        String data = "Input length must be multiple of 16 when decrypting with padded cipher";
        test2(data);

        String data2 = "TRACE com.hsjry.packet.security.encrypt.AesEncrypt";
        test2(data2);

    }

    private void test2(String data){
        //初始化
        Crypto Crypto = CryptoFactory.create("/test.cer", "/a.pfx", "123456");

        //初始化
        Crypto Crypto2 = CryptoFactory.create("/a.cer", "/test.pfx", "1234");

        //加密和签名
        long start = System.currentTimeMillis();
        String targetPacket = Crypto2.encrypt(data);
        System.out.println("加密加签总时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(targetPacket);

        //验签和解密
        long start2 = System.currentTimeMillis();
        String sourcePacket = Crypto.decrypt(targetPacket);
        System.out.println("验签解密总时间："+(System.currentTimeMillis()-start2)+"ms");
        System.out.println(sourcePacket);
    }

    @Test
    public void test00(){
        String ss = "11221sfs中文";
        Crypto crypto = CryptoFactory.create("/test.cer", "/a.pfx", "123456");
        String result = crypto.encrypt(ss);
        System.out.println(result);
    }

    @Test
    public void test11(){
        String ss = "{\"data\":\"Qz35fwtw1Q2eFeABWlng3g==\",\"key\":\"Bl8/SItmAs2uPUp7jBRseQSM/DcezODfE0A21df6RQSDziHo8Rm27KacRCMgL3wdiIqSdVnXKOKRWX18tJvsGDb9TMY1u8KwRadYg1Pu3Pzwz51adEb40g+8biIWYMbkWAY8YdA2yvoeEtMn19cqwiiGEwih6jTnk/LUyUVEVEj2NCzK0yfeHrgnYpZ+Ccx4nxGZIMrqcT4SGg1kaIGiuMZEe+ArdjQ3BYAu7ksEJmbKpRcBmCr+UpzphDX4UaFQs5fL+M/0y7HC32knom8mAd1/kUJ63oUvyikobL7uG/EkQP2iX9i3Zl4/FhXAZmNuhTjPAIo9u0dMG6WH9VDxFg==\",\"salt\":\"Y9jAgimslYxZs2QX6uk6rkPUNUlZfHAMagTVJKkZBXeTXo/zRoylhuU1vX5vw6jwLPX/iQ6NTvdLNwQjezWs42Oo9KOF5heWdLqyzao0p4uubUqaKP56eR07j6aSArkWCHmu9xK+U1cpeMwBGRawnF74/ewEuTTy8s8YqxpM17m/dfEmNMdVDLb1aBkUSfk/8EBnm+KXGSU0+uvDjP1IovUZL6ncpx/Wlom5Y88FzfFOcWveC56ofdZStAQPBKjy1SSE//rDU4fmp319tRjq1Ez2jyK3i7kJaPgSnlLqR1KG74crT4F8cVh84lFXKBy+bGjskBiLec3eNgHvwbPVRQ==\",\"sign\":\"WHFyV2r56dDZI+tBkJqw3nco11tYkGD1J3QrsBPMi3dEycy1ipP7+gvYIuMpHWl8VBN3JIeD1MUGkof8P6vujb2RRzNcuGdgwAbiKgkMiiys77BfBCFESN11sdEzJBOE7+5k9va9PFT7aIgrRV+TvF2DIS2u5A+8kPTwSkSEvwritKcLJmDZuVzjWCDXX/r8NI1VOnqnMuaU3d+GvRVMGj+2vzOVbt6fvipAF/uYCHwvvJtLlCU2YX27etb2Zm2vxgvXNeomJuCJQzrp6WfPKgLa4tbJbsqxf94hX5FtgFNdJdiWbwrVyZUgE19ZE3//gJjS5WCUf9EeysybHWIiBw==\"}";
        Crypto crypto = CryptoFactory.create("/a.cer", "/test.pfx", "1234");
        String result = crypto.decrypt(ss);
        System.out.println(result);
    }

}
