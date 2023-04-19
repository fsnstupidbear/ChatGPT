package com.fsnteam.fsnweb;


import com.unfbx.chatgpt.OpenAiStreamClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan({"com.fsnteam.fsnweb.dao","com.fsnteam.fsnweb.mapper"})
@EnableSwagger2
public class FsnwebApplication {
    @Value("${chatgpt.apiKey}")
    private String apiKey;
    @Value("${chatgpt.apiHost}")
    private String apiHost;

    public static void main(String[] args) {
        SpringApplication.run(FsnwebApplication.class, args);
    }

    @Bean
    public OpenAiStreamClient openAiStreamClient() {
        return OpenAiStreamClient.builder().apiHost(apiHost).apiKey(apiKey).build();
    }
}
