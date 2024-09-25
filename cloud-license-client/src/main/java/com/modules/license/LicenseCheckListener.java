package com.modules.license;

import com.modules.entity.LicenseVerifyParam;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 在项目启动时安装证书
 */
@Slf4j
@Component
public class LicenseCheckListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 证书subject
     */
    @Value("${license.subject}")
    private String subject;

    /**
     * 公钥别称
     */
    @Value("${license.publicAlias}")
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    @Value("${license.storePass}")
    private String storePass;

    /**
     * 证书生成路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    @Value("${license.publicKeysStorePath}")
    private String publicKeysStorePath;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	String jarFilePath = null;
    	try {
    		 // 获取当前类的URL
            URL url = LicenseCheckListener.class.getProtectionDomain().getCodeSource().getLocation();
            log.info("url 路径：" + url);
            File jarFile = null;
            String jarUrl = url.toString();
            if (jarUrl.startsWith("jar:file:")) {
                // 提取 JAR 文件路径
                String jarPath = jarUrl.substring(10, jarUrl.indexOf("!"));
                log.info("jar:file:  jarPath 路径：" + jarPath);
                jarFile = new File(jarPath);
            } else {
            	// 处理为文件路径
                jarFile = new File(url.toURI());
            }
            // 获取JAR文件的绝对路径
            jarFilePath = jarFile.getParent();
            log.info("JAR 文件的绝对路径：" + jarFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            jarFilePath = licensePath;
        }
    	
    	
        //root application context 没有parent
        ApplicationContext context = event.getApplicationContext().getParent();
        if(context == null){
            if(StringUtils.isNotBlank(licensePath)){
                log.info("++++++++ 开始安装证书 ++++++++");

                LicenseVerifyParam param = new LicenseVerifyParam();
                param.setSubject(subject);
                param.setPublicAlias(publicAlias);
                param.setStorePass(storePass);
                param.setLicensePath(jarFilePath + File.separator + licensePath);
                param.setPublicKeysStorePath(jarFilePath + File.separator + publicKeysStorePath);

                LicenseVerify licenseVerify = new LicenseVerify();
                //安装证书
                licenseVerify.install(param);

                log.info("++++++++ 证书安装结束 ++++++++");
            }
        }
    }
}
