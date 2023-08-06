package com.techiesmeetups.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "clubs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String photoURL;
    private String content;
    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Event> events = new ArrayList<>();


    // toString() method
    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", content='" + content + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}