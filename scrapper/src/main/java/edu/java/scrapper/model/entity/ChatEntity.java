package edu.java.scrapper.model.entity;

import edu.java.scrapper.model.Chat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "chat")
@Setter
@Getter
@NoArgsConstructor
public class ChatEntity {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "chat_link",
        joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id")
    )
    private List<LinkEntity> links = new ArrayList<>();

    public ChatEntity(long chatId) {
        this.chatId = chatId;
    }

    public Chat toChat() {
        return new Chat(this.chatId);
    }
}
