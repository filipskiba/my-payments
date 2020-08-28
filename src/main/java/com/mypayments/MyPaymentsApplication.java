package com.mypayments;

import com.mypayments.domain.ContractorType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyPaymentsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MyPaymentsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MyPaymentsApplication.class);
    }

}
