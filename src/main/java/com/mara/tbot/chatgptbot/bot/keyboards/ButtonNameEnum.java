package com.mara.tbot.chatgptbot.bot.keyboards;

public enum ButtonNameEnum {
    BUTTON_1("Statistics"),
    BUTTON_2("Support"),
    BUTTON_3("Stop ChatGPT"),
    BUTTON_4("Button 4");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
