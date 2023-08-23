package uol.compass.postconsumer.application.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.postconsumer.application.dto.ResponseDTO;
import uol.compass.postconsumer.domain.post.PostService;
import uol.compass.postconsumer.infrastructure.utils.Constants;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc", required = false) String direction
    ) {
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        postService.getAll(pageNumber, size, sortBy, direction)
                )
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchPost(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(
                ResponseDTO.ok(postService.fetchPost(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> reprocessPost(@PathVariable Integer id){
        return ResponseEntity.ok(
                new ResponseDTO<>(postService.reprocessPost(id))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> disablePost(@PathVariable Integer id){
        return ResponseEntity.ok(
                new ResponseDTO<>(postService.disablePost(id))
        );
    }
}
