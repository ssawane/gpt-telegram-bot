package com.mara.tbot.chatgptbot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    @JsonProperty(value ="role")
    private String role;

    @JsonProperty(value = "content")
    private String content;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
