package com.example.composertestapi.publisher;

import com.example.composertestapi.dto.GroupDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupIndexPublisher {
    private Logger log = LoggerFactory.getLogger(getClass());
    private RabbitTemplate template;
    private Queue addQueue;
    private Queue deleteQueue;

    @Autowired
    public GroupIndexPublisher(RabbitTemplate aTemplate, Queue aAddQueue, Queue aDeleteQueue) {
        this.template = aTemplate;
        this.addQueue = aAddQueue;
        this.deleteQueue = aDeleteQueue;
    }

    public void indexGroup(GroupDTO group) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String groupStr = mapper.writeValueAsString(group);
            this.template.convertAndSend(addQueue.getName(), groupStr);
        } catch(JsonProcessingException je) {
            log.error("Error serializing Group {}", group.getGroupName(), je);
        }
    }

    public void deIndexGroup(GroupDTO group) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String groupStr = mapper.writeValueAsString(group);
            this.template.convertAndSend(deleteQueue.getName(), groupStr);
        } catch(JsonProcessingException je) {
            log.error("Error serializing Group {}", group.getGroupName(), je);
        }
    }
}
