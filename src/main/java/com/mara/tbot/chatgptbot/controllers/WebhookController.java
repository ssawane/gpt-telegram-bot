package com.mara.tbot.chatgptbot.controllers;

import com.mara.tbot.chatgptbot.bot.ChatGPTBot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class WebhookController {
    private final ChatGPTBot bot;

    @PostMapping("/webhook")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/test")
    public String checkVPS() {
        return "good";
    }
}
