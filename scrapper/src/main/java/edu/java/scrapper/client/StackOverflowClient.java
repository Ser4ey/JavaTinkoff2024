package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.StackOverflowQuestionsResponse;

public interface StackOverflowClient {
    StackOverflowQuestionsResponse.StackOverflowQuestion getQuestion(Long id);
}
