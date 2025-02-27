package itmo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.consul.discovery.ReregistrationPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableDiscoveryClient
public class SpacemarineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpacemarineServiceApplication.class, args);
    }

    @Bean
    public ReregistrationPredicate reRegistrationPredicate() {
        return e -> e.getStatusCode() >= 400;
    }

}

