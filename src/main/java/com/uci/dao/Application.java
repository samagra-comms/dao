package com.uci.dao;

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

        xMessageRepository.insert(new XMessageDAO(new Long(121313), "HHBJ", "hkkh", "efef", "grdgrdg", LocalDateTime.now(),
                "HHBJ", "hkkh", "efef", "grdgrdg", "HHBJ", "hkkh", "efef")).log().subscribe();
        xMessageRepository.insert(new XMessageDAO(new Long(1213134), "HHB", "hkkh", "efef", "grdgrdg", LocalDateTime.now(),
                "HHBJ", "hkkh", "efef", "grdgrdg", "HHBJ", "hkkh", "efef")).log().subscribe();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1L);
        xMessageRepository.findAllByUserId("7837833100").subscribe(new Consumer<List<XMessageDAO>>() {
            @Override
            public void accept(List<XMessageDAO> xMessageDAO) {
                log.info("XMessage List Item All :>> " + counter + "  " + xMessageDAO.get(counter).getApp());
                counter += 1;
            }
        });
        xMessageRepository.findAllByFromIdAndTimestampAfter("hkkh",yesterday).subscribe(xMessageDAO -> {
            log.info("XMessage List Item :>> " + counter + "  " + xMessageDAO.getFromId() + " " + xMessageDAO.getTimestamp());
            counter += 1;
        });
    }
}