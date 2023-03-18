package com.uci.dao;

import com.datastax.driver.core.utils.UUIDs;
import com.uci.dao.models.XMessageDAO;
import com.uci.dao.repository.XMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import reactor.core.publisher.SignalType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author chakshu
 */
@Slf4j
@PropertySources({
        @PropertySource("classpath:dao-application.properties")
})
@SpringBootApplication(scanBasePackages = "com.uci.dao")
public class Application implements CommandLineRunner {

    int counter = 0;

    @Autowired
    private XMessageRepository xMessageRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("DAO Called");
    }
}