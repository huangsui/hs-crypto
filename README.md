## OPENSSL生成证书 ##
```
1、创建一个缺省配置文件<file:ca.cnf>【可选】
[ req ]
default_bits       = 2048
default_keyfile        = privkey.pem
distinguished_name = req_distinguished_name

[ req_distinguished_name ]
countryName                 = Country Name (2 letter code)
countryName_default         = CN
stateOrProvinceName         = State or Province Name (full name)
stateOrProvinceName_default = JiangSu
localityName                = Locality Name (eg, city)
localityName_default        = NanJing
organizationName            = Organization Name (eg, company)
organizationName_default    = Sheld
commonName                  = Common Name (e.g. server FQDN or YOUR name)
commonName_max              = 64
commonName_default          = Ted CA Test
emailAddress           = test@email.address

2、生成证书

2.1 创建私钥privkey.pem

openssl genrsa -out privkey.pem 2048
或者
openssl genpkey -algorithm RSA -out privkey.pem

扩展参数：-des3

2.2 使用cnf配置创建证书申请文件req.pem
openssl req -new -key privkey.pem -out req.pem -config ca.cnf

2.3 创建cer证书
openssl req -x509 -key privkey.pem -out test.cer -config ca.cnf

扩展参数：-days 750

2.4创建pfx证书
openssl pkcs12 -export -in test.cer -inkey privkey.pem -out test.pfx
Export Password:123456

3、查看
私钥
openssl pkey -in privkey.pem -text -noout

证书申请
openssl req -in req.pem -text -verify -noout

cer证书
openssl x509 -in test.cer -noout -text

# 查看证书完整信息
openssl x509 -in test.cer -text -noout

# 查看证书包含的公钥
openssl x509 -in test.cer -pubkey

# 查看哪个 CA 机构签发了证书
openssl x509 -in test.cer -issuer

# 查看证书的有效期
openssl x509 -in test.cer -enddate

pfx
openssl pkcs12 -in test.pfx -info -noout

4、其他
生成公钥
openssl rsa -in privkey.pem -pubout -out pubkey.pem
```