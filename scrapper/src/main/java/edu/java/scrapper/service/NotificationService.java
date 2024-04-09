package edu.java.scrapper.service;

import edu.java.scrapper.model.dto.request.LinkUpdateRequest;

public interface NotificationService {
    void sendNotification(LinkUpdateRequest update);
}
