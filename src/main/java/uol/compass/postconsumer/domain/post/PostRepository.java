package uol.compass.postconsumer.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM posts p WHERE p.isEnabled = true")
    Page<Post> findAllEnabled(Pageable page);
}
