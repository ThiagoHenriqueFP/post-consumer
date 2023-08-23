package uol.compass.postconsumer.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uol.compass.postconsumer.domain.comment.Comment;
import uol.compass.postconsumer.domain.history.History;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
public class Post {
    @Id
    private Integer id;
    private String title;
    private String body;
    private LocalDateTime processed_at;
    @JsonIgnore
    private Boolean isEnabled;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="post_id")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="post_id")
    private List<History> history = new ArrayList<>();
}
