package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.LinkUpdateRequest;

public interface BotClient {
    void sendUpdates(LinkUpdateRequest linkUpdateRequest);

}
