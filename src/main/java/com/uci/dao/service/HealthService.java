package com.uci.dao.service;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uci.dao.repository.XMessageRepository;
import com.uci.utils.UtilHealthService;

import reactor.core.publisher.Mono;

@Service
public class HealthService extends UtilHealthService{
	@Autowired
    private XMessageRepository xMessageRepository;
	
	/**
	 * Returns health json node for kafka, campaign url and cassandra
	 * 
	 * @return JsonNode
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode getAllHealthNode() throws JsonMappingException, JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"checks\":[{\"name\":\"kafka\",\"healthy\":false},{\"name\":\"campaign\",\"healthy\":false}],\"healthy\":true}");
        
        /* Cassandra health info */
        JsonNode cassandraHealthNode = getCassandraHealthNode();
        JsonNode cassandraNode = mapper.createObjectNode();
        ((ObjectNode) cassandraNode).put("name", "Cassandra");
        ((ObjectNode) cassandraNode).put("healthy", cassandraHealthNode.get("healthy").asBoolean());
        
        /* Kafka health info */
        JsonNode kafkaHealthNode = getKafkaHealthNode();
        JsonNode kafkaNode = mapper.createObjectNode();
        ((ObjectNode) kafkaNode).put("name", "Kafka");
        ((ObjectNode) kafkaNode).put("healthy", kafkaHealthNode.get("healthy").asBoolean());
        ((ObjectNode) kafkaNode).put("details", kafkaHealthNode.get("details"));
        
        /* create `ArrayNode` object */
        ArrayNode arrayNode = mapper.createArrayNode();
        
        /* add JSON users to array */
        arrayNode.addAll(Arrays.asList(cassandraNode, kafkaNode));
        
        ((ObjectNode) jsonNode).putArray("checks").addAll(arrayNode);
        
        /* System overall health */
        if(cassandraNode.get("healthy").booleanValue() && kafkaHealthNode.get("healthy").booleanValue()) {
        	((ObjectNode) jsonNode).put("healthy", true);
        } else {
        	((ObjectNode) jsonNode).put("healthy", false);
        }
        
        return jsonNode;
	}
	
	
	/**
	 * Returns health node
	 * 
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("deprecation")
	public JsonNode getCassandraHealthNode() throws IOException, JsonMappingException, JsonProcessingException {
		Boolean cassandraHealth = getIsCassandraHealthy();
		
		/* Result node */
		ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"healthy\":false}");
        /* Add data in result node */
        ((ObjectNode) jsonNode).put("healthy", cassandraHealth);
        
        return jsonNode;
	}
	
	/**
	 * Check if Cassandra connecting or not
	 * 
	 * @return Boolean
	 */
	public Boolean getIsCassandraHealthy() {
		Boolean isHealthy = false;
		try {
			Mono<Boolean> exists = xMessageRepository.existsByUserId("Test");
	    	exists.subscribe(System.out::println);
	    	isHealthy = true;
		} catch(Exception e) {
			isHealthy = false;
		}
		
		return isHealthy;
	}
}
