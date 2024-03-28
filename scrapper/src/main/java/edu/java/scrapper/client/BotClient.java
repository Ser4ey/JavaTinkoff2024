package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.request.LinkUpdateRequest;

public interface BotClient {
    void sendUpdates(LinkUpdateRequest linkUpdateRequest);

}
