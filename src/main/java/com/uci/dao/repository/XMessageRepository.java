package com.uci.dao.repository;

import com.uci.dao.models.XMessageDAO;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface XMessageRepository extends ReactiveCassandraRepository<XMessageDAO, Long> {

	@AllowFiltering
	Mono<Boolean> existsByUserId(String userID);
	
    Flux<List<XMessageDAO>> findAllByFromIdOrderByTimestampDesc(String fromID);

    Flux<XMessageDAO> findFirstByFromIdOrderByTimestampDesc(String fromID);

    Flux<XMessageDAO> findFirstByAppOrderByTimestampDesc(String appName);

    @AllowFiltering
    Flux<List<XMessageDAO>> findAllByUserId(String userID);

    Flux<XMessageDAO> findByMessageId(String messageID);

    Flux<XMessageDAO> findFirstByReplyIdOrderByTimestampDesc(String replyId);

    Flux<XMessageDAO> findFirstByCauseIdAndMessageStateOrderByTimestampDesc(String causeId, String messageState);

    @AllowFiltering
    Flux<List<XMessageDAO>> findAllByUserIdOrderByTimestampDesc(String userID);

    @AllowFiltering
    Flux<XMessageDAO> findAllByUserIdAndTimestampAfter(String userID, LocalDateTime timestamp);

    Flux<XMessageDAO> findTopByFromIdAndMessageStateOrderByTimestampDesc(String fromId, String messageState);

    Flux<XMessageDAO> findFirstByUserIdAndCauseIdAndMessageStateOrderByTimestampDesc(String userId, String causeId, String messageState);

    Flux<XMessageDAO> findTopByUserIdOrderByTimestampDesc(String userId);

    @AllowFiltering
    Flux<XMessageDAO> findAllByFromIdAndTimestampAfter(String userID, LocalDateTime timestamp);
}


