package com.mara.tbot.chatgptbot.chatGPT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mara.tbot.chatgptbot.models.ChatRequestBody;
import com.mara.tbot.chatgptbot.models.ChatResponseBody;
import com.mara.tbot.chatgptbot.models.Message;
import com.mara.tbot.chatgptbot.properties.ChatGPTProperties;
import com.mara.tbot.chatgptbot.models.Query;
import com.mara.tbot.chatgptbot.util.OpenAiBadResponseException;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatGPT {

    private final ChatGPTProperties chatGPTProperties;
    private static final int MAX_CONTEXT_SIZE = 5;
    private static final int MAX_LAST_MESSAGE_SIZE = 150;
    private static final int CONTEXT_TIME = 60;

    @Timed("gettingOpenAiResponse")
    public Query makeQueryToOpenai(String messageToSend, List<Query> prevQueries) {
        RestTemplate restTemplate = new RestTemplate();

        List<Message> messageList = new ArrayList<>();
        int contextWeight = addPrevMessagesToBody(messageList, prevQueries);

        ResponseEntity<ChatResponseBody> responseEntity = restTemplate.exchange(
                chatGPTProperties.getUrl(), HttpMethod.POST,
                new HttpEntity<>(buildRequestBody(messageList, messageToSend), buildHeaders()),
                ChatResponseBody.class
        );

        ChatResponseBody responseBody = responseEntity.getBody();
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseBody == null)
            throw new OpenAiBadResponseException();

        return new Query(
                messageToSend,
                responseBody.getChoices().get(0).getMessage().getContent(),
                contextWeight,
                responseBody.getUsage().getCompletionTokens() + responseBody.getUsage().getPromptTokens()
                        - contextWeight,
                responseBody.getUsage().getTotalTokens()
        );
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + chatGPTProperties.getApiKey());

        return headers;
    }

    private String buildRequestBody(List<Message> messageList, String messageToSend) {
        messageList.add(new Message("user", messageToSend));

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(new ChatRequestBody(chatGPTProperties, messageList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    private int addPrevMessagesToBody(List<Message> messageList, List<Query> prevQ) {
        if (prevQ.isEmpty())
            return 0;

        List<Query> lastQ = prevQ.stream().filter(q -> ChronoUnit.MINUTES.between(q.getReceivedAt(),
                        LocalDateTime.now()) < CONTEXT_TIME).limit(MAX_CONTEXT_SIZE).collect(Collectors.toList());

        int totalTokensLength = 0;
        int flag = 0;
        for (Query query : lastQ) {
            if (totalTokensLength + query.getWeight() > 4097 - chatGPTProperties.getMaxTokens() - MAX_LAST_MESSAGE_SIZE) {
                if (flag == 0)
                    return 0;
                lastQ = lastQ.subList(0, flag);
                break;
            }
            totalTokensLength += query.getWeight();
            flag++;
        }

        buildContext(lastQ, messageList);

        return totalTokensLength;
    }

    private void buildContext(List<Query> queries, List<Message> messageList) {
        Collections.reverse(queries);

        for (Query query : queries) {
            messageList.add(new Message("user", query.getQuestion()));
            messageList.add(new Message("assistant", query.getAnswer()));
        }
    }
}
