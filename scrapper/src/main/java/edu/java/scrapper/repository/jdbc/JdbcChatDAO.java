package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repository.ChatRepository;
import java.util.List;
import java.util.Optional;
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
            new Chat(resultSet.getInt("id"), resultSet.getLong("unique_chat_id"));

    @Override
    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT id, unique_chat_id FROM chat", chatRowMapper);
    }

    @Override
    @Transactional
    public Optional<Chat> findByUniqueChatId(Long uniqueChatId) {
        var chats = jdbcTemplate.query(
            "SELECT * FROM chat WHERE unique_chat_id = ?",
            chatRowMapper,
            uniqueChatId);

        if (chats.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(chats.getFirst());
    }

    @Override
    @Transactional
    public void add(Long uniqueChatId) {
        jdbcTemplate.update(
            "INSERT INTO chat (unique_chat_id) VALUES (?)",
            uniqueChatId);
    }

    @Override
    public void remove(Integer id) {
        jdbcTemplate.update(
            "DELETE FROM chat WHERE id = ?",
            id);
    }

}
