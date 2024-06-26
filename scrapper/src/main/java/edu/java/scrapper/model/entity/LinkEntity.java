package edu.java.scrapper.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "link")
@Setter
@Getter
@NoArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(name = "last_update", nullable = false)
    private OffsetDateTime lastUpdate;

    @Column(name = "last_check", nullable = false)
    private OffsetDateTime lastCheck;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToMany(mappedBy = "links", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatEntity> chats = new ArrayList<>();

    public LinkEntity(String url) {
        this.url = url;
        lastUpdate = OffsetDateTime.now();
        lastCheck = OffsetDateTime.now();
        count = 0;
    }

    public LinkEntity(String url, Integer count) {
        this.url = url;
        lastUpdate = OffsetDateTime.now();
        lastCheck = OffsetDateTime.now();
        this.count = count;
    }
}
