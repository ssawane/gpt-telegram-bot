package com.mara.tbot.chatgptbot.bot;

import com.mara.tbot.chatgptbot.bot.handlers.CallbackQueryHandler;
import com.mara.tbot.chatgptbot.bot.handlers.MessageHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Setter
@Slf4j
public class ChatGPTBot extends SpringWebhookBot {

    private String webhookPath;
    private String botName;
    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public ChatGPTBot(SetWebhook setWebhook, String botToken, MessageHandler messageHandler,
                      CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook, botToken);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (Exception e) {
            log.error("Received an error while handling update from user. Update message text: "
                    + update.getMessage().getText());
            e.printStackTrace();
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Something went wrong!");
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.process(update.getCallbackQuery());
        } else if (update.getMessage() != null) {
            return messageHandler.process(update.getMessage());
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return webhookPath;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
