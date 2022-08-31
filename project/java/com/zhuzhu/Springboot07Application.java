package com.zhuzhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.zhuzhu.store.mapper")
public class Springboot07Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot07Application.class, args);
    }

    @Bean
    public MultipartConfigElement getMultipartConfigElement()
    {
        // 创建一个配着的工厂类对象
        MultipartConfigFactory factory =new MultipartConfigFactory();

        // 设置文件最大10M，DataUnit提供5中类型B,KB,MB,GB,TB
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(10, DataUnit.MEGABYTES));


        // 工厂创建对象
        return factory.createMultipartConfig();
    }

}
