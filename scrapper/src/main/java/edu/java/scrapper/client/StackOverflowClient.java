package edu.java.scrapper.client;

import edu.java.scrapper.response_dto.StackOverflowQuestionsResponse;

public interface StackOverflowClient {
    StackOverflowQuestionsResponse.StackOverflowQuestion getQuestion(Long id);
}
