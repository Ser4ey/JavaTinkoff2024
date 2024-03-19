package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcChatDAO implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Chat> chatRowMapper =
        (resultSet, rowNum) ->
            new Chat(resultSet.getLong("chat_id"));

    @Override
    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT chat_id FROM chat", chatRowMapper);
    }

    @Override
    public List<Chat> findAll(Integer linkId) {
        return jdbcTemplate.query(
            "SELECT DISTINCT chat.chat_id FROM chat "
                + "JOIN chat_link ON chat.chat_id = chat_link.chat_id "
                + "WHERE link_id = ?",
            chatRowMapper,
            linkId);
    }

    @Override
    @Transactional
    public boolean isChatExist(Long chatId) {
        var chats = jdbcTemplate.query(
            "SELECT * FROM chat WHERE chat_id = ?", chatRowMapper, chatId);

        return !chats.isEmpty();
    }

    @Override
    @Transactional
    public void add(Long chatId) {
        jdbcTemplate.update(
            "INSERT INTO chat (chat_id) VALUES (?)",
            chatId);
    }

    @Override
    @Transactional
    public void remove(Long chatId) {
        jdbcTemplate.update(
            "DELETE FROM chat WHERE chat_id = ?", chatId);
    }

}
