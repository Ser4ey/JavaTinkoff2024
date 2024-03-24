package edu.java.scrapper.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "link")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(name = "last_update", nullable = false)
    private OffsetDateTime lastUpdate;

    @Column(name = "last_check", nullable = false)
    private OffsetDateTime lastCheck;

    @ManyToMany(mappedBy = "links")
    private List<Chat> chats;

    public Link(String url, OffsetDateTime lastUpdate, OffsetDateTime lastCheck) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
    }
}
