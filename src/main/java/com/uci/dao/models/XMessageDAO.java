package com.uci.dao.models;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("XMessage")
public class XMessageDAO {
    @Column
    private Long id;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String fromId;
    @Column
    private String channel;
    @Column
    private String provider;
    @PrimaryKeyColumn(type = CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime timestamp;
    @Column
    private String messageState;
    @Column
    private String xMessage;
    @Column
    private String app;
    @Column
    private String auxData;
    @Column
    private String messageId;
    @Column
    private String replyId;
    @Column
    private String causeId;
}
