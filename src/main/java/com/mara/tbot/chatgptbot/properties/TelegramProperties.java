package com.mara.tbot.chatgptbot.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@Slf4j
public class TelegramProperties {
    @Value("${telegram.webhook-path}")
    private String webhookPath;

    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String botToken;

    @PostConstruct
    private void setWebhook() {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> vars = new HashMap<>();
        vars.put("TOKEN", botToken);
        vars.put("URL", webhookPath);
        String webhookMessage = restTemplate.getForObject(
                "https://api.telegram.org/bot{TOKEN}/setWebhook?url={URL}", String.class, vars
        );
        log.info(webhookMessage);
    }
}
