package uol.compass.postconsumer.domain.history;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uol.compass.postconsumer.domain.Status.Status;
import uol.compass.postconsumer.domain.post.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "histories")
public class History {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime timestamp;
    private Status status;

    public History(Status status){
        this.setStatus(status);
        this.setTimestamp(LocalDateTime.now());
    }

    public History(){
        this.setTimestamp(LocalDateTime.now());
    }
}
