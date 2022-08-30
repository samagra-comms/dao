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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface XMessageRepository extends ReactiveCassandraRepository<XMessageDAO, Long> {

	@AllowFiltering
	Mono<Boolean> existsByUserId(String userID);

    @AllowFiltering
    Flux<XMessageDAO> findAllByUserIdAndTimestampAfter(String userID, LocalDateTime timestamp);

    Flux<XMessageDAO> findFirstByUserIdAndCauseIdAndMessageStateOrderByTimestampDesc(String userId, String causeId, String messageState);

    @AllowFiltering
    Flux<XMessageDAO> findAllByFromIdAndTimestampAfter(String userID, LocalDateTime timestamp);

    @AllowFiltering
    Flux<XMessageDAO> findById(UUID uuid);
    
    @AllowFiltering
    Flux<XMessageDAO> findFirstByAppAndUserIdAndFromIdAndMessageStateOrderByTimestampDesc(String app, String userID, String fromId, String messageState);
    
    @AllowFiltering
    Flux<Slice<XMessageDAO>> findAllByUserIdAndFromId(Pageable paging, String userID, String fromID);

    @AllowFiltering
    Mono<Slice<XMessageDAO>> findAllByAppAndTimestampAfterAndTimestampBeforeAndProvider(Pageable paging, String name, Timestamp startDate, Timestamp endDate, String provider);

    @AllowFiltering
    Mono<Slice<XMessageDAO>> findAllByUserIdInAndFromIdInAndTimestampAfterAndTimestampBeforeAndProvider(Pageable paging, List<String>  listUserId, List<String> listFromId, Timestamp startDate, Timestamp endDate, String provider);

    @AllowFiltering
    Mono<Slice<XMessageDAO>> findAllByAppAndTimestampAfterAndTimestampBeforeAndProviderAndTagsContains(Pageable paging, String name, Timestamp startDate, Timestamp endDate, String provider, String tag);

    @AllowFiltering
    Mono<Slice<XMessageDAO>> findAllByUserIdInAndFromIdInAndTimestampAfterAndTimestampBeforeAndProviderAndTagsContains(Pageable paging, List<String>  listUserId, List<String> listFromId, Timestamp startDate, Timestamp endDate, String provider, String tag);

    @AllowFiltering
    Flux<XMessageDAO> findAllByMessageIdAndUserIdInAndFromIdIn(String messageId, List<String> listUserId, List<String> listFromId);
}


