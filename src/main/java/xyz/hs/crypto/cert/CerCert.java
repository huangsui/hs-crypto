package xyz.hs.crypto.cert;

import xyz.hs.crypto.CryptoException;
import xyz.hs.crypto.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author huangsui
 * Created on 2019/8/22
 */
public class CerCert {
    private static final Logger logger = LoggerFactory.getLogger(CerCert.class);

    private static final String CERT_TYPE_X509 = "X.509";
    private String resPath;
    private String filePath;

    public Certificate getCertificate(){
        if(StringUtil.isNullOrEmpty(filePath)){
            try(InputStream is = this.getClass().getResourceAsStream(resPath);){
                CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE_X509);
                return certificateFactory.generateCertificate(is);
            }catch (Exception e){
                throw new CryptoException("获取cer证书异常", e);
            }
        }else {
            try(FileInputStream fis = new FileInputStream(filePath);) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE_X509);
                return certificateFactory.generateCertificate(fis);
            }catch (Exception e){
                throw new CryptoException("获取cer证书异常", e);
            }
        }
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
