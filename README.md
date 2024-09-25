
#1. 生成
keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.keystore" -storepass "public_777" -keypass "private_777" -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"


#2. 导出（公钥部署在客户方，私钥自己保留，用于生成license）
keytool -exportcert -alias "privateKey" -keystore "privateKeys.keystore" -storepass "public_777" -file "certfile.cer"


#3. 导入
keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.keystore" -storepass "public_777"


#4. 生成证书license.lic
http://localhost:8081/license/generateLicense
{
"subject": "license_lawtrust",
"privateAlias": "privateKey",
"keyPass": "private_777",
"storePass": "public_777",
"licensePath": "D:/tfzk/Java_workspace/Lisence/license.lic",
"privateKeysStorePath": "D:/tfzk/Java_workspace/Lisence/privateKeys.keystore",
"issuedTime": "2024-07-03 00:00:01",
"expiryTime": "2024-08-14 20:30:19",
"consumerType": "User",
"consumerAmount": 1,
"description": "lawtrust证书"
}

可选：
"licenseCheckModel": {
    "ipAddress": [
        "172.16.1.126",
        "192.168.56.1"
        ],
        "macAddress": [
        "00-E1-4C-68-07-B5",
        "0A-00-27-00-00-10"
        ],
        "cpuSerial": "178BFBFF00860F01",
        "mainBoardSerial": "BBDW0820AG001635"
   }



#5. 修改代码 ---> 监听器、拦截器、校验license
	①：添加License校验
		<!-- License -->
		<dependency>
			<groupId>de.schlichtherle.truelicense</groupId>
			<artifactId>truelicense-core</artifactId>
			<version>1.33</version>
		</dependency>
		
	②：配置文件application.properties
		#License相关配置
		license:
		  subject: license_lawtrust  //对应生成证书主题
		  publicAlias: publicCert	//对应命令行
		  storePass: public_777		//对应命令行
			licensePath=src/main/resources/license/license.lic  //校验的lisence文件地址
			publicKeysStorePath=src/main/resources/license/publicCerts.keystore  //公钥地址

	③：新增code


#6. 客户方部署业务jar包。
	
