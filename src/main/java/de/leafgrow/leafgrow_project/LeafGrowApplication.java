package de.leafgrow.leafgrow_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.leafgrow.leafgrow_project.repository")
public class LeafGrowApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeafGrowApplication.class, args);
    }

}
