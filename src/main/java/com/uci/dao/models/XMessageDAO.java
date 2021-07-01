package com.uci.dao.models;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("XMessage")
public class XMessageDAO {
    @PrimaryKey
    private Long id;
    @Column
    private String userId;
    @Column
    private String fromId;
    @Column
    private String channel;
    @Column
    private String provider;
    @Column
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
