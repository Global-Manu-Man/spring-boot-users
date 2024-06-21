package spring_security_shield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "spring_security_shield.repository")
@ComponentScan(basePackages = {
        "spring_security_shield.controller",
        "spring_security_shield.services",
        "spring_security_shield.services.impl",
        "spring_security_shield.entity",
        "spring_security_shield.repository",
        "spring_security_shield.config"

})
public class SpringSecurityShieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityShieldApplication.class, args);
    }

}
