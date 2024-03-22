package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"MultipleStringLiterals", "InnerTypeLast"})
public class JdbcLinkDAO implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final class CustomRowMapper {
        private static final RowMapper<Link> LINK_ROW_MAPPER =
            (resultSet, rowNum) ->
                new Link(
                    resultSet.getInt("id"),
                    URI.create(resultSet.getString("url")),
                    resultSet.getTimestamp("last_update").toInstant().atOffset(ZoneOffset.UTC),
                    resultSet.getTimestamp("last_check").toInstant().atOffset(ZoneOffset.UTC)
                );

        private static final RowMapper<String> CHAT_LINK_ROW_MAPPER =
            (resultSet, rowNum) -> resultSet.getInt("chat_id") + ":" + resultSet.getInt("link_id");

    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM link", CustomRowMapper.LINK_ROW_MAPPER);
    }

    @Override
    public List<Link> findAllByChatId(Long chatId) {
        String sql = """
            SELECT DISTINCT *
            FROM link JOIN chat_link ON link.id = chat_link.link_id
            WHERE chat_id = ?
            """;

        return jdbcTemplate.query(
            sql,
            CustomRowMapper.LINK_ROW_MAPPER,
            chatId
        );
    }

    @Override
    @Transactional
    public Optional<Link> findById(Integer linkId) {
        var links = jdbcTemplate.query(
            "SELECT * FROM link WHERE id = ?",
            CustomRowMapper.LINK_ROW_MAPPER,
            linkId
        );

        if (links.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(links.getFirst());
    }

    @Override
    @Transactional
    public Optional<Link> findByUrl(URI url) {
        var links = jdbcTemplate.query(
            "SELECT * FROM link WHERE url = ?",
            CustomRowMapper.LINK_ROW_MAPPER,
            url.toString()
        );

        if (links.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(links.getFirst());
    }

    @Override
    @Transactional
    public Optional<Link> findByChatIdAndLinkId(Long chatId, Integer linkId) {
        var chatLink = jdbcTemplate.query(
            "SELECT * FROM chat_link WHERE chat_link.chat_id = ? AND chat_link.link_id = ?",
            CustomRowMapper.CHAT_LINK_ROW_MAPPER,
            chatId, linkId
        );

        if (chatLink.isEmpty()) {
            return Optional.empty();
        }
        return findById(linkId);
    }

    @Override
    @Transactional
    public Link add(Long chatId, URI url) {
        var link = findByUrl(url);
        if (link.isEmpty()) {
            jdbcTemplate.update(
                "INSERT INTO link (url) VALUES (?)",
                url.toString()
            );

            link = findByUrl(url);
        }

        Integer linkId = link.get().id();

        jdbcTemplate.update(
            "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)",
            chatId, linkId
        );

        return link.get();
    }

    @Override
    public void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime) {
        String sql = "UPDATE link SET last_update = ? WHERE id = ?";
        jdbcTemplate.update(sql, lastUpdateTime, id);
    }

    @Override
    public void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime) {
        String sql = "UPDATE link SET last_check = ? WHERE id = ?";
        jdbcTemplate.update(sql, lastCheckTime, id);
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        jdbcTemplate.update(
            "DELETE FROM link WHERE id = ?",
            id
        );
    }

    @Override
    @Transactional
    public void removeLinkRelation(Long chatId, Integer linkId) {
        jdbcTemplate.update(
            "DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?",
            chatId, linkId
        );

        var chatLinkRelations = jdbcTemplate.query(
            "SELECT * FROM chat_link WHERE link_id = ?",
            CustomRowMapper.CHAT_LINK_ROW_MAPPER,
            linkId
        );

        if (chatLinkRelations.isEmpty()) {
            remove(linkId);
        }
    }

}

