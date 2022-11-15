package com.example.backend221;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Backend221Application {

    public static void main(String[] args) {
        SpringApplication.run(Backend221Application.class, args);
    }

   // public static interface EventRepository extends JpaRepository<Event, Integer> {
   // }
}
