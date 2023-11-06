package com.cqyc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author cqyc
 * @create 2023-11-02-15:56
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class SkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillApplication.class);
    }


}
