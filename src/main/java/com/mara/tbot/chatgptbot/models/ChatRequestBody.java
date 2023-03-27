package com.mara.tbot.chatgptbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mara.tbot.chatgptbot.properties.ChatGPTProperties;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequestBody {

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "messages")
    private List<Message> messages;

    @JsonProperty(value = "temperature")
    private Double temperature;

    @JsonProperty(value = "max_tokens")
    private Integer maxTokens;

    public ChatRequestBody(ChatGPTProperties chatGPTProperties, List<Message> messages) {
        this.messages = messages;
        setModel(chatGPTProperties.getModel());
        setTemperature(chatGPTProperties.getTemperature());
        setMaxTokens(chatGPTProperties.getMaxTokens());
    }
}
