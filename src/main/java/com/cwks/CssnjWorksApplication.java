package com.cwks;

import com.cwks.bizcore.tycx.config.CwksProperies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement //启用事务
@MapperScan({"com.cwks.**.dao","com.cwks.bizcore.tycx.core.mapping.mapper","com.cwks.bizcore.yyfb.mapping.mapper","com.cwks.common.dao"}) //扫描mybites dao扫描
@EnableConfigurationProperties({CwksProperies.class}) //启动配置文件加载器
@EnableCaching //redies 启动
@EnableSwagger2 //让swagger生成接口文档
@EnableAsync //开启异步调用
public class CssnjWorksApplication {
    public static void main(String[] args) {
        SpringApplication.run(CssnjWorksApplication.class, args);
        StringBuffer sbf = new StringBuffer("CSSNJ 通用开发平台-v2 is runing success! \n");
        sbf.append("．．．南京中软中台构建-通用查询 ．．．\n");
        sbf.append("．．．．．★ CSSNJ DEV ★．．．．．．\n");
        sbf.append("．．．．★ Version 2.0.1 ★．．．．．\n");
        System.out.println(sbf.toString());
    }

}
