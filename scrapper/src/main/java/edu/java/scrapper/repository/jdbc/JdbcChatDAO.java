package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("InnerTypeLast")
public class JdbcChatDAO implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final class CustomRowMapper {
        private static final RowMapper<Chat> CHAT_ROW_MAPPER =
            (resultSet, rowNum) ->
                new Chat(resultSet.getLong("chat_id"));

    }

    @Override
    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT chat_id FROM chat", CustomRowMapper.CHAT_ROW_MAPPER);
    }

    @Override
    public List<Chat> findAllByLinkId(Integer linkId) {
        String sql = """
            SELECT DISTINCT chat.chat_id FROM chat
            JOIN chat_link ON chat.chat_id = chat_link.chat_id
            WHERE link_id = ?
            """;

        return jdbcTemplate.query(sql,
            CustomRowMapper.CHAT_ROW_MAPPER,
            linkId);
    }

    @Override
    @Transactional
    public boolean isChatExist(Long chatId) {
        try {
            var chat = jdbcTemplate.queryForObject(
                "SELECT * FROM chat WHERE chat_id = ?", CustomRowMapper.CHAT_ROW_MAPPER, chatId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

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
