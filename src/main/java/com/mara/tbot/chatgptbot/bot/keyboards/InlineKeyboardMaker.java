package com.mara.tbot.chatgptbot.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getTestInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(ButtonNameEnum.BUTTON_1.getButtonName());
        button1.setCallbackData("Callback from BUTTON_1");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(ButtonNameEnum.BUTTON_2.getButtonName());
        button2.setCallbackData("Callback from BUTTON_2");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText(ButtonNameEnum.BUTTON_3.getButtonName());
        button3.setCallbackData("Callback from BUTTON_3");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText(ButtonNameEnum.BUTTON_4.getButtonName());
        button4.setCallbackData("Callback from BUTTON_4");

        rowList.add(List.of(button1));
        rowList.add(List.of(button2));
        rowList.add(List.of(button3));
        rowList.add(List.of(button4));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getchatWithSupportKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Contact support");
        button1.setCallbackData("support");
        button1.setUrl("https://t.me/marabro");

        rowList.add(List.of(button1));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getChatHistoryKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Chat history");
        button1.setCallbackData("history");
        button1.setUrl("https://www.google.com/");

        rowList.add(List.of(button1));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getIWantMoreKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("I want more tokens!");
        button1.setCallbackData("want_more");
        button1.setUrl("https://t.me/marabro");

        rowList.add(List.of(button1));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

}
