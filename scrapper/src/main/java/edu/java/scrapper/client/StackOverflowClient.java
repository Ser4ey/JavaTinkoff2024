package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.response.StackOverflowQuestionsResponse;

public interface StackOverflowClient {
    StackOverflowQuestionsResponse.StackOverflowQuestion getQuestion(Long id);
}
