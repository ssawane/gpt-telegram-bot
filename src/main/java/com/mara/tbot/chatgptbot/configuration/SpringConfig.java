package com.mara.tbot.chatgptbot.configuration;

import com.mara.tbot.chatgptbot.bot.ChatGPTBot;
import com.mara.tbot.chatgptbot.bot.handlers.CallbackQueryHandler;
import com.mara.tbot.chatgptbot.bot.handlers.MessageHandler;
import com.mara.tbot.chatgptbot.properties.TelegramProperties;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {
    private final TelegramProperties telegramProperties;
    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramProperties.getWebhookPath()).build();
    }

    @Bean
    public ChatGPTBot simpleBot(SetWebhook setWebhook) {
        ChatGPTBot chatGPTBot = new ChatGPTBot(setWebhook, telegramProperties.getBotToken(), messageHandler, callbackQueryHandler);
        chatGPTBot.setWebhookPath(telegramProperties.getWebhookPath());
        chatGPTBot.setBotName(telegramProperties.getBotName());
        return chatGPTBot;
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return  new TimedAspect(meterRegistry);
    }
}
