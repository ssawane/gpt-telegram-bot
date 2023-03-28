package com.mara.tbot.chatgptbot.bot.handlers;

import com.mara.tbot.chatgptbot.bot.keyboards.InlineKeyboardMaker;
import com.mara.tbot.chatgptbot.bot.keyboards.ReplyKeyboardMaker;
import com.mara.tbot.chatgptbot.chatGPT.ChatGPT;
import com.mara.tbot.chatgptbot.models.Query;
import com.mara.tbot.chatgptbot.models.User;
import com.mara.tbot.chatgptbot.services.QueryService;
import com.mara.tbot.chatgptbot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final QueryService queryService;
    private final UserService userService;
    private final ChatGPT chatGPT;

    public BotApiMethod<?> process(Message message) {
        Long tgUserId = message.getFrom().getId();
        String receivedMessage = message.getText();
        Optional<User> user = userService.findByTgUserId(tgUserId);

        if (!message.hasText())
            return new SendMessage(message.getChatId().toString(), "The bot only accepts messages in text format.");

        if (user.isPresent()) {
            if (user.get().getActiveSession()) {
                if (receivedMessage.equals("Stop ChatGPT") || receivedMessage.equals("/stopgpt"))
                    return stopChatGPT(message,user.get());
                return sendToOpenAi(message, user.get());
            } else {
                return switch (receivedMessage) {
                    case "/start" -> greetingNewUser(message, user);
                    case "/help" -> helpMenu(message);
                    case "/support", "Support" -> getSupport(message);
                    case "Start ChatGPT", "/startgpt" -> startChatGPT(message, user.get());
                    case "Statistics" -> getStatistics(message, user.get());
                    default -> defaultCatcher(message);
                };
            }
        } else
            return greetingNewUser(message, user);
    }

    private SendMessage getStatistics(Message message, User user) {
        Long chatId = message.getChatId();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<b>Statistics:</b>\n\nName: <i>")
                .append(user.getFirstname())
                .append("</i>");
        if (user.getUsername() != null) {
            stringBuilder
                    .append("\nUsername: @")
                    .append(user.getUsername());
        }
        stringBuilder
                .append("\n\n<b>Tokens left: ")
                .append(user.getTokensLeft())
                .append("</b>");

        SendMessage sendMessage = new SendMessage(chatId.toString(), stringBuilder.toString());
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getIWantMoreKeyboard());
        return sendMessage;
    }

    private BotApiMethod<?> sendToOpenAi(Message message, User user) {
        Long chatId = message.getChatId();

        if (user.getTokensLeft() <= 0) {
            userService.setInactiveSession(user);
            SendMessage sendMessage = new SendMessage(chatId.toString(),
                    "ChatGPT stopped. You have no tokens left. Contact support for more info.");
            sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

            return sendMessage;
        }

        if (message.getText().length() > 150)
            return new SendMessage(chatId.toString(), "The maximum message length is 150 characters");

        if (message.getText().equals("/startgpt"))
            return new SendMessage(chatId.toString(), "The bot is already running.");

        List<Query> allQueries = queryService.findByUserIdOrderByReceivedAtDesc(user.getId());
        Query query = chatGPT.makeQueryToOpenai(message.getText(), allQueries);
        queryService.setUserAndSave(query, user);
        userService.subtractUsedTokens(user, query.getTotalSpent());

        return new SendMessage(chatId.toString(), query.getAnswer());
    }

    private SendMessage defaultCatcher(Message message) {
        Long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "Unknown command. Start chatting with ChatGPT by clicking on the button from the menu or contact support.");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        return sendMessage;
    }

    private SendMessage chatHistory(Message message) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "You can see your ChatGPT history. Push the button below.");
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getChatHistoryKeyboard());

        return sendMessage;
    }


    private SendMessage startChatGPT(Message message, User user) {
        Long chatId = message.getChatId();

        if (user.getTokensLeft() <= 0)
            return new SendMessage(chatId.toString(),
                    "You have no tokens left. Contact support for more info.");

        userService.setActiveSession(user);
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "ChatGPT launched. The button to stop the bot is in the main menu.");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getLaunchedChatKeyboard());

        return sendMessage;
    }

    private SendMessage stopChatGPT(Message message, User user) {
        Long chatId = message.getChatId();

        userService.setInactiveSession(user);
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "ChatGPT stopped.");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        return sendMessage;
    }

    private SendMessage greetingNewUser(Message message, Optional<User> user) {
        Long tgUserId = message.getFrom().getId();
        Long chatId = message.getChatId();

        if (user.isEmpty())
            userService.save(new User(tgUserId, message.getFrom().getUserName(),
                    message.getFrom().getFirstName(), message.getFrom().getLastName(),
                    message.getChatId()));
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "Hello! I am a bot to use ChatGPT.\n" +
                        "The bot understands the context. OpenAi model version: \"gpt-3.5-turbo\".");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        return sendMessage;
    }

    private SendMessage helpMenu(Message message) {
        Long chatId = message.getChatId();
        return new SendMessage(chatId.toString(),
                "/start - restart the bot\n/support - get support");
    }

    private SendMessage getSupport(Message message) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "You can ask any question you want. Push the button below.");
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getchatWithSupportKeyboard());

        return sendMessage;
    }
}
