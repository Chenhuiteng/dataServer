package com.cht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * application启动类一定要在最外层的包
 * @author cht
 */
@SpringBootApplication // 代表为SpringBoot应用的运行主类
public class DataServerApplication {

    public  static void main(String[] args){

    	/** 创建SpringApplication应用对象 */
		SpringApplication springApplication = new SpringApplication(DataServerApplication.class);
		/** 设置横幅模式(可设置关闭) */
		springApplication.setBannerMode(Banner.Mode.LOG);
		/** 运行 */
		springApplication.run(args);
    }
}
