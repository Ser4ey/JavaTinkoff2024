package edu.java.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CommandAnswer {
    @NonNull
    private final String answerText;

    private final boolean withPagePreview;

}
