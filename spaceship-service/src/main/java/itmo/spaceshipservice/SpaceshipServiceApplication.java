package itmo.spaceshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableDiscoveryClient
public class SpaceshipServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceshipServiceApplication.class, args);
    }

}
