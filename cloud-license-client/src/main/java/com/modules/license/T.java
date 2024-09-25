package com.modules.license;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class T {
	public static void main(String[] args) {
		try {
            // 获取当前类的URL
            URL url = T.class.getProtectionDomain().getCodeSource().getLocation();
            // 转换为File对象
            File file = new File(url.toURI());
            // 获取绝对路径
            String absolutePath = file.getAbsolutePath();
            System.out.println("JAR 包的绝对路径：" + absolutePath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
	}
}
