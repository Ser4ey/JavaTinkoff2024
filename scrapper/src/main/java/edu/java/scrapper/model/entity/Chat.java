package edu.java.scrapper.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chat")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Chat {
    @Id
//    @Column(name = "chat_id")
    private Long chatId;

    @ManyToMany
    @JoinTable(
        name = "chat_link",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private List<Link> links;

    public Chat(long chatId) {
        this.chatId = chatId;
    }
}
