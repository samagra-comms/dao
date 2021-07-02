package com.uci.dao;

import com.uci.dao.models.XMessageDAO;
import com.uci.dao.repository.XMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chakshu
 */
@Slf4j
@ConfigurationProperties("dao-application.properties")
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

        xMessageRepository.insert(new XMessageDAO(new Long(121313), "HHBJ", "hkkh", "efef", "grdgrdg", LocalDateTime.now(),
                "HHBJ", "hkkh", "efef", "grdgrdg", "HHBJ", "hkkh", "efef")).log().subscribe();
        xMessageRepository.insert(new XMessageDAO(new Long(1213134), "HHBJ24", "hkkh", "efef", "grdgrdg", LocalDateTime.now(),
                "HHBJ", "hkkh", "efef", "grdgrdg", "HHBJ", "hkkh", "efef")).log().subscribe();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1L);
            xMessageRepository.findAllByUserIdAndTimestampAfter("HHBJ", yesterday).subscribe(xMessageDAO -> {
                log.info("XMessage List Item :>> " + counter +  "  " + xMessageDAO.getMessageId() + " " + xMessageDAO.getTimestamp());
                counter += 1;
            });
    }
}