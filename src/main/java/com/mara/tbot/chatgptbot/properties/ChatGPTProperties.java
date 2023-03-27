package com.mara.tbot.chatgptbot.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ChatGPTProperties {

    @Value("${openai.api_key}")
    private String apiKey;

    @Value("${openai.max_tokens}")
    private Integer maxTokens;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.temperature}")
    private Double temperature;

    @Value("${openai.url}")
    private String url;
}
