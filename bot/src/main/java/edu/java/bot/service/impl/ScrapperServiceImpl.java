package edu.java.bot.service.impl;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.exception.request.CustomRequestException;
import edu.java.bot.exception.service.ScrapperException;
import edu.java.bot.service.ScrapperService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScrapperServiceImpl implements ScrapperService {

    private final ScrapperClient scrapperClient;

    private static final ScrapperException SERVER_NOT_AVAILABLE_EXCEPTION =
        new ScrapperException("0", "Не удалось получить информацию", "Сервер не доступен");

    @Override
    public List<String> getUserLinks(long chatId) {
        try {
            var listLinksResponse = scrapperClient.getLinks(chatId);

            List<String> links = new ArrayList<>();

            for (var link : listLinksResponse.links()) {
                links.add(link.url().toString());
            }

            return links;
        } catch (CustomRequestException e) {
            var statusCode = e.getApiErrorResponse().code();
            var description = e.getApiErrorResponse().description();
            var exceptionMessage = e.getApiErrorResponse().exceptionMessage();
            throw new ScrapperException(statusCode, description, exceptionMessage);
        } catch (WebClientRequestException e) {
            throw SERVER_NOT_AVAILABLE_EXCEPTION;
        }

    }

    @Override
    public void registerChat(long chatId) {
        try {
            scrapperClient.registerChat(chatId);
        } catch (CustomRequestException e) {
            var statusCode = e.getApiErrorResponse().code();
            var description = e.getApiErrorResponse().description();
            var exceptionMessage = e.getApiErrorResponse().exceptionMessage();
            log.info("Не удалось зарегистрировать пользователя. Code: {} Описание: {} Сообщение ошибки: {}",
                statusCode, description, exceptionMessage);
        } catch (WebClientRequestException e) {
            throw SERVER_NOT_AVAILABLE_EXCEPTION;
        }
    }

    @Override
    public void addLink(long chatId, URI link) {
        try {
            scrapperClient.addLink(chatId, link);
        } catch (CustomRequestException e) {
            var statusCode = e.getApiErrorResponse().code();
            var description = e.getApiErrorResponse().description();
            var exceptionMessage = e.getApiErrorResponse().exceptionMessage();
            throw new ScrapperException(statusCode, description, exceptionMessage);
        } catch (WebClientRequestException e) {
            throw SERVER_NOT_AVAILABLE_EXCEPTION;
        }
    }

    @Override
    public void removeLink(long chatId, URI link) {
        try {
            scrapperClient.removeLink(chatId, link);
        } catch (CustomRequestException e) {
            var statusCode = e.getApiErrorResponse().code();
            var description = e.getApiErrorResponse().description();
            var exceptionMessage = e.getApiErrorResponse().exceptionMessage();
            throw new ScrapperException(statusCode, description, exceptionMessage);
        } catch (WebClientRequestException e) {
            throw SERVER_NOT_AVAILABLE_EXCEPTION;
        }
    }
}
