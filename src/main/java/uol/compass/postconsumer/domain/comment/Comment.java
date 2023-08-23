package uol.compass.postconsumer.domain.comment;


import jakarta.persistence.*;
import lombok.*;
import uol.compass.postconsumer.domain.post.Post;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comments")
public class Comment {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String body;
}
