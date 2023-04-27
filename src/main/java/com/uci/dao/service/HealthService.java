package com.uci.dao.service;

import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uci.dao.repository.XMessageRepository;
import com.uci.utils.UtilHealthService;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

@Service
public class HealthService extends UtilHealthService {
	@Autowired
    private XMessageRepository xMessageRepository;

	@Autowired
	ObjectMapper mapper;

	/**
	 * Returns health node
	 * 
	 * @return Mono<JsonNode>
	 */
	public Mono<JsonNode> getCassandraHealthNode() {
		return Mono.fromCallable(() -> {
			ObjectNode result = mapper.createObjectNode();
			try {
				xMessageRepository.existsByUserId("Test");
				result.put("status", Status.UP.getCode());
				return result;
			} catch(Exception e) {
				result.put("status", Status.DOWN.getCode());
				result.put("message", e.getMessage());
				return result;
			}
		});
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
		Mono<JsonNode> cassandraHealth = getCassandraHealthNode();
		return Mono.zip(externalComponentHealth, cassandraHealth).map(results -> {
			ObjectNode detailsNode = mapper.createObjectNode();
			detailsNode.set("cassandra", results.getT2());
			results.getT1().get("details").fields().forEachRemaining(detail -> detailsNode.set(detail.getKey(), detail.getValue()));
			resultNode.set("details", detailsNode);
			resultNode.put("status",
					results.getT1().get("status").textValue().equals(Status.UP.getCode()) &&
							results.getT2().get("status").textValue().equals(Status.UP.getCode())
							? Status.UP.getCode() : Status.DOWN.getCode()
			);
			return resultNode;
		});
	}
}
