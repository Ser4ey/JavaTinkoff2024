package service;

import java.util.List;

public interface ScrapperService {
    List<String> getUserLinks(long chatId);

    void registerChat(long chatId);
}
