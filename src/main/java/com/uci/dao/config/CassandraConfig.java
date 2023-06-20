package com.uci.dao.config;

import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.DriverConfigLoaderBuilderConfigurer;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chakshu
 */
@Configuration
@EnableReactiveCassandraRepositories(basePackages = "com.uci.dao")
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.migration.count}")
    private int migrationCount;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }

    protected boolean getMetricsEnabled() { return false; }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.uci.dao"};
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification
                        .createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Collections.singletonList(specification);
    }

//    @Override
//    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
//        return new SessionBuilderConfigurer() {
//            @Override
//            public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
//                return cqlSessionBuilder
//                        .withConfigLoader(DriverConfigLoader.programmaticBuilder().withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(1200000)).build());
//            }
//        };
//    }

    @Override
    protected DriverConfigLoaderBuilderConfigurer getDriverConfigLoaderBuilderConfigurer() {
        return config ->
                config.withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "30s")
                        .withString(DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT, "10s")
                        .withString(DefaultDriverOption.REQUEST_TIMEOUT, "30s")
//                        .withString(DefaultDriverOption.REQUEST_CONSISTENCY, "ALL")
                        .build();
    }

    /**
     * Get list of scripts run on startup
     *
     * @return
     */
    @Override
    protected List<String> getStartupScripts() {
        List<String> all = getMigrationScripts();
        Integer count = 0;
        try {
            if (migrationCount > 0) {
                count = migrationCount;
            }
            if (migrationCount > all.size()) {
                count = all.size();
            }
            System.out.println("Count: " + count + ", migrationCount: " + migrationCount);
        } catch (NumberFormatException ex) {
            System.out.println("NumberFormatException: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        List<String> scripts = new ArrayList<>();
        for (int i = (count); i < all.size(); i++) {
            scripts.add(all.get(i));
        }

        return scripts;
    }

    /**
     * List of migration scripts
     *
     * @return
     */
    protected List<String> getMigrationScripts() {
        List<String> allScripts = new ArrayList<>();
        allScripts.add("CREATE TABLE IF NOT EXISTS " +
                keyspace + ".XMessage(id uuid," +
                "userId text, " +
                "fromId text, " +
                "channel text, " +
                "provider text, " +
                "timestamp timestamp, " +
                "messageState text, " +
                "xMessage text, " +
                "app text, " +
                "auxData text, " +
                "messageId text, " +
                "replyId text, " +
                "causeId text, " +
                "PRIMARY KEY ((userId,fromId), timestamp)) " +
                "WITH CLUSTERING ORDER BY (timestamp DESC)");
        allScripts.add("ALTER TABLE " + keyspace + ".XMessage ADD sessionId uuid;");
        allScripts.add("ALTER TABLE " + keyspace + ".XMessage ADD ownerOrgId text;");
        allScripts.add("ALTER TABLE " + keyspace + ".XMessage ADD ownerId text;");
        allScripts.add("ALTER TABLE " + keyspace + ".XMessage ADD botUuid uuid;");
        allScripts.add("ALTER TABLE " + keyspace + ".XMessage ADD tags list<text>;");

//        allScripts.add("CREATE INDEX IF NOT EXISTS message_state_index\n" +
//                "ON "+keyspace
//                +".XMessage ( KEYS ( messageState ) ) ");
        return allScripts;
    }

//    @Bean
//    public QueryLogger queryLogger(Cluster cluster) {
//        QueryLogger queryLogger = QueryLogger.builder()
//                .build();
//        cluster.register(queryLogger);
//        return queryLogger;
//    }
}
