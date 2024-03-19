package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
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
@SuppressWarnings("MultipleStringLiterals")
public class JdbcLinkDAO implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Chat> chatRowMapper =
        (resultSet, rowNum) ->
            new Chat(resultSet.getLong("chat_id"));

    private final RowMapper<Link> linkRowMapper =
        (resultSet, rowNum) ->
            new Link(
                resultSet.getInt("id"),
                URI.create(resultSet.getString("url")),
                resultSet.getTimestamp("last_update").toInstant().atOffset(ZoneOffset.UTC)
            );

    private final RowMapper<String> chatLinkRowMapper =
        (resultSet, rowNum) -> resultSet.getInt("chat_id") + ":" + resultSet.getInt("link_id");

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM link", linkRowMapper);
    }

    @Override
    public List<Link> findAll(Long chatId) {
        return jdbcTemplate.query(
            "SELECT DISTINCT id, url, last_update "
                + "FROM link JOIN chat_link ON link.id = chat_link.link_id "
                + "WHERE chat_id = ?",
            linkRowMapper,
            chatId);
    }

    @Override
    @Transactional
    public Optional<Link> findById(Integer chatId) {
        var links = jdbcTemplate.query(
            "SELECT * FROM link WHERE id = ?",
            linkRowMapper,
            chatId);

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
            linkRowMapper,
            url.toString());

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
            chatLinkRowMapper,
            chatId, linkId);

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
                url.toString());

            link = findByUrl(url);
        }

        Integer linkId = link.get().id();

        jdbcTemplate.update(
            "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)",
            chatId, linkId);

        return link.get();
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        jdbcTemplate.update(
            "DELETE FROM link WHERE id = ?",
            id);
    }

    @Override
    @Transactional
    public void removeLinkRelation(Long chatId, Integer linkId) {
        jdbcTemplate.update(
            "DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?",
            chatId, linkId);

        var chatLinkRelations = jdbcTemplate.query(
            "SELECT * FROM chat_link WHERE link_id = ?",
            chatLinkRowMapper,
            linkId);

        if (chatLinkRelations.isEmpty()) {
            remove(linkId);
        }
    }

}

