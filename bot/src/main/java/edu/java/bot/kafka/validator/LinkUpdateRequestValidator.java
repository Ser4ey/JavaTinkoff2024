package edu.java.bot.kafka.validator;

import edu.java.bot.exception.kafka.MessageValueValidationException;
import edu.java.bot.model.dto.request.LinkUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdateRequestValidator {
    public void validate(LinkUpdateRequest request) {
        if (request.id() == null) {
            throw new MessageValueValidationException("Can't validate LinkUpdateRequest. 'id' == null");
        }

        if (request.description() == null) {
            throw new MessageValueValidationException("Can't validate LinkUpdateRequest. 'description' == null");
        }

        if (request.url() == null) {
            throw new MessageValueValidationException("Can't validate LinkUpdateRequest. 'url' == null");
        }

        if (request.tgChatIds() == null) {
            throw new MessageValueValidationException("Can't validate LinkUpdateRequest. 'tgChatIds' == null");
        }

        if (request.tgChatIds().isEmpty()) {
            throw new MessageValueValidationException("Can't validate LinkUpdateRequest. 'tgChatIds' is empty");
        }
    }

}
