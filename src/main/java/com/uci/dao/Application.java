package com.uci.dao;

import com.uci.dao.models.Employee;
import com.uci.dao.models.EmployeeKey;
import com.uci.dao.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *  @author chakshu
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.uci.dao")
public class Application implements CommandLineRunner {

    int counter = 0;

    @Autowired
    private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        UUID id = UUID.randomUUID();
        employeeRepository.insert(new Employee(EmployeeKey.builder().dateOfBirth(LocalDateTime.now()).id(UUID.randomUUID()).fullName("Joe Doe").build(), "Engineer 1", "IT", 200000)).log().subscribe();
        employeeRepository.insert(new Employee(new EmployeeKey("Sally May", LocalDateTime.now(), id), "Senior Engineer 1", "IT", 300000)).subscribe(emp -> {
            employeeRepository.findAll().subscribe(employee -> {
                log.info("employeeList:" + counter +  "  " + employee);
                counter += 1;
            });
        });
    }
}