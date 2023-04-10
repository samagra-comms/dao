package com.uci.dao.service;

import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uci.dao.repository.XMessageRepository;
import com.uci.utils.UtilHealthService;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService extends UtilHealthService {
	@Autowired
    private XMessageRepository xMessageRepository;

	/**
	 * Returns health node
	 * 
	 * @return Mono<JsonNode>
	 */
	public Mono<JsonNode> getCassandraHealthNode() {
		return getIsCassandraHealthy().map(healthy -> new ObjectMapper().createObjectNode().put("healthy", healthy));
	}

	/**
	 * Returns the combined health of kafka, campaign and cassandra.
	 *
	 * @return Returns details of each service in `checks` key and
	 * the overall health status in `healthy` key.
	 */
	@Override
	public Mono<JsonNode> getAllHealthNode() {
		ObjectNode resultNode = new ObjectMapper().createObjectNode();
		Mono<JsonNode> externalComponentHealth = super.getAllHealthNode();
		Mono<JsonNode> cassandraHealth = getCassandraHealthNode().map(result -> {
			ObjectNode objectNode = new ObjectMapper().createObjectNode();
			objectNode.put("name", "cassandra");
			objectNode.set("healthy", result.get("healthy"));
			return  objectNode;
		});
		return Mono.zip(externalComponentHealth, cassandraHealth).map(healths -> {
			resultNode.putArray("checks")
					.add(healths.getT1().get("checks"))
					.add(healths.getT2());
			resultNode.put("healthy",
					healths.getT1().get("healthy").booleanValue() &&
					healths.getT2().get("healthy").booleanValue()
			);
			return resultNode;
		});
	}

	/**
	 * Check if Cassandra connecting or not
	 * 
	 * @return Mono<Boolean>
	 */
	private Mono<Boolean> getIsCassandraHealthy() {
		return Mono.fromCallable(() -> {
			try {
				xMessageRepository.existsByUserId("Test");
				return true;
			} catch(Exception e) {
				return false;
			}
		});
	}
}
