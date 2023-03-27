package com.mara.tbot.chatgptbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mara.tbot.chatgptbot.models.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponseBody {
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "object")
    private String object;

    @JsonProperty(value = "created")
    private Long created;

    @JsonProperty(value = "choices")
    private List<Choice> choices;

    @JsonProperty(value = "usage")
    private Usage usage;

    @Data
    public static class Choice {
        @JsonProperty(value = "index")
        private Integer index;

        @JsonProperty(value = "message")
        private Message message;

        @JsonProperty(value = "finish_reason")
        private String finishReason;
    }

    @Data
    public static class Usage {
        @JsonProperty(value = "prompt_tokens")
        private Integer promptTokens;

        @JsonProperty(value = "completion_tokens")
        private Integer completionTokens;

        @JsonProperty(value = "total_tokens")
        private Integer totalTokens;
    }
}
