package org.sss.gradletest;

import com.dianrong.common.util.feign.FeignClientBuilder;
import com.dianrong.common.util.feign.FeignClientConfig;
import com.dianrong.common.util.feign.RemoteAppConfig;
import com.dianrong.ftc.loanapp.spi.client.LoanAppClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ImportResource(value = {
        "classpath*:collection_amque.xml"})
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    @Bean
    public LoanAppClient loanAppClient() {
        FeignClientBuilder feignClientBuilder = new FeignClientBuilder("ftc-saas-collection-job");
        RemoteAppConfig remoteAppConfig = RemoteAppConfig.newBuilder()
                .remoteAppId("ftc-loanapp")
                .url("http://ftc-saas-user-demo.aliyun-cn-shanghai-b.ftc.dianrong.io/api").build();
        FeignClientConfig feignClientConfig = new FeignClientConfig();
        return feignClientBuilder.buildClient(LoanAppClient.class, remoteAppConfig, feignClientConfig);
    }
}