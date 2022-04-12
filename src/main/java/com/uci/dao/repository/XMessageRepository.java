package com.uci.dao.repository;

import com.uci.dao.models.XMessageDAO;

import messagerosa.core.model.XMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface XMessageRepository extends ReactiveCassandraRepository<XMessageDAO, Long> {

	@AllowFiltering
	Mono<Boolean> existsByUserId(String userID);
	
    Flux<List<XMessageDAO>> findAllByFromIdOrderByTimestampDesc(String fromID);

    Flux<XMessageDAO> findFirstByFromIdOrderByTimestampDesc(String fromID);

    Flux<XMessageDAO> findFirstByAppOrderByTimestampDesc(String appName);

    @AllowFiltering
    Flux<XMessageDAO> findAllByUserId(String userID);

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

    @AllowFiltering
    Flux<XMessageDAO> findById(UUID uuid);
    
    Flux<XMessageDAO> findByXMessageAndUserId(XMessage xmsg, String userID);
    
    @AllowFiltering
    Flux<XMessageDAO> findFirstByAppAndUserIdAndFromIdAndMessageStateOrderByTimestampDesc(String app, String userID, String fromId, String messageState);
    
    @AllowFiltering
    Flux<XMessageDAO> findAllByUserIdOrderByTimestamp(String userID);
    
    @AllowFiltering
    Flux<Slice<XMessageDAO>> findAllByUserIdAndFromId(Pageable paging, String userID, String fromID);
}


