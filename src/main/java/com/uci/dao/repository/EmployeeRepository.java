package com.uci.dao.repository;

import com.uci.dao.models.Employee;
import com.uci.dao.models.EmployeeKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * @author chakshu
 */
@Repository
public interface EmployeeRepository extends ReactiveCassandraRepository<Employee, EmployeeKey> {

    Flux<Employee> findByEmployeeKey_Id(UUID employeeKey_id);

}
