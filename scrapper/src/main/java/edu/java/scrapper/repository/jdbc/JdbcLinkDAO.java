package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Log4j2
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
                    resultSet.getTimestamp("last_check").toInstant().atOffset(ZoneOffset.UTC),
                    resultSet.getInt("count")
                );
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM link", CustomRowMapper.LINK_ROW_MAPPER);
    }

    @Override
    public List<Link> findNotCheckedForLongTime(Integer numberOfLink) {
        return jdbcTemplate.query(
            "SELECT * FROM link ORDER BY last_check LIMIT ?", CustomRowMapper.LINK_ROW_MAPPER, numberOfLink);
    }

    @Override
    public List<Link> findAllByChatId(Long chatId) {
        String sql = """
            SELECT DISTINCT link.id, link.url, link.last_update, link.last_check, link.count
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
        try {
            var link = jdbcTemplate.queryForObject(
                "SELECT * FROM link WHERE id = ?",
                CustomRowMapper.LINK_ROW_MAPPER,
                linkId);

            return Optional.ofNullable(link);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Link> findByUrl(URI url) {
        try {
            var link = jdbcTemplate.queryForObject(
                "SELECT * FROM link WHERE url = ?",
                CustomRowMapper.LINK_ROW_MAPPER,
                url.toString()
            );

            return Optional.ofNullable(link);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Link> findByChatIdAndLinkId(Long chatId, Integer linkId) {
        var chatLinkCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM chat_link WHERE chat_link.chat_id = ? AND chat_link.link_id = ?",
            Integer.class,
            chatId, linkId
        );

        if (chatLinkCount == null || chatLinkCount == 0) {
            return Optional.empty();
        }
        return findById(linkId);
    }

    @Override
    @Transactional
    public Link addLink(URI url) {
        try {
            jdbcTemplate.update(
                "INSERT INTO link (url) VALUES (?)",
                url.toString()
            );
        } catch (DuplicateKeyException e) {
            log.warn("Ссылка {} уже есть в бд! Er: {}", url, e.toString());
            var link = findByUrl(url);
            return link.get();
        }

        var link = findByUrl(url);
        return link.get();
    }

    @Override
    public Link addLink(URI url, Integer count) {
        try {
            jdbcTemplate.update(
                "INSERT INTO link (url, count) VALUES (?, ?)",
                url.toString(),
                count
            );
        } catch (DuplicateKeyException e) {
            log.warn("Ссылка {} уже есть в бд! Er: {}", url, e.toString());
            var link = findByUrl(url);
            return link.get();
        }

        var link = findByUrl(url);
        return link.get();
    }

    @Override
    @Transactional
    public void addLinkRelation(Long chatId, Integer linkId) {
        jdbcTemplate.update(
            "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)",
            chatId, linkId
        );
    }

    @Override
    @Transactional
    public void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime) {
        String sql = "UPDATE link SET last_update = ? WHERE id = ?";
        jdbcTemplate.update(sql, lastUpdateTime, id);
    }

    @Override
    @Transactional
    public void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime) {
        String sql = "UPDATE link SET last_check = ? WHERE id = ?";
        jdbcTemplate.update(sql, lastCheckTime, id);
    }

    @Override
    @Transactional
    public void updateCount(Integer id, Integer count) {
        String sql = "UPDATE link SET count = ? WHERE id = ?";
        jdbcTemplate.update(sql, count, id);
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
    }

    @Override
    @Transactional
    public void removeLinksWithNoRelations() {
        String sql = """
            DELETE FROM link WHERE id IN (
                SELECT link.id FROM link LEFT JOIN chat_link ON link.id = chat_link.link_id
                WHERE chat_link.link_id IS NULL
            )
            """;

        jdbcTemplate.update(sql);
    }
}

