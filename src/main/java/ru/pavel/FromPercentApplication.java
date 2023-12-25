package ru.pavel;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@Configuration
public class FromPercentApplication {
    @Bean
    public MultipartConfigElement configElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(500L));
        factory.setMaxRequestSize(DataSize.ofMegabytes(500L));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(FromPercentApplication.class, args);
    }

}
