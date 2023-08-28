package uol.compass.postconsumer.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.postconsumer.application.post.dto.PostResponseDTO;
import uol.compass.postconsumer.domain.Status.Status;
import uol.compass.postconsumer.domain.comment.Comment;
import uol.compass.postconsumer.domain.history.History;
import uol.compass.postconsumer.domain.history.HistoryService;
import uol.compass.postconsumer.infrastructure.utils.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final HistoryService historyService;
    private final RestTemplate fetch = new RestTemplate();

    public PostService(PostRepository postRepository, HistoryService historyService) {
        this.postRepository = postRepository;
        this.historyService = historyService;
    }

    private static void checkId(Integer id) {
        if (!(id > 0 && id <= 100))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "id is out of range [1-100]"
            );
    }

    public Post fetchPost(Integer id) {
        checkId(id);

        Optional<Post> postAlreadyExists = postRepository.findById(id);
        Post post;
        if (postAlreadyExists.isEmpty()) {
            post = new Post();
            post.setId(id);
            historyService.newHistory(post, Status.CREATED);
            post = populatePost(id, post);
        } else {
            post = postAlreadyExists.get();
        }

        return post;
    }

    public Post populatePost(Integer id, Post post) {
        try {
            var response = fetch.getForEntity(Constants.EXTERNAL_API_POST + "/" + id, Post.class);

            validateResponse(response);

            historyService.newHistory(post, Status.POST_FIND);

            if (response.getBody() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "cannot retrieve data from post"
                );
            }

            historyService.newHistory(post, Status.POST_OK);

            post.setTitle(response.getBody().getTitle());
            post.setBody(response.getBody().getBody());

            fetchComments(post, post.getId());
            post.setProcessed_at(LocalDateTime.now());
        } catch (Exception e) {
            historyService.newHistory(post, Status.FAILED);
            historyService.newHistory(post, Status.DISABLED);
            post.setProcessed_at(LocalDateTime.now());
            throw e;
        }

        return postRepository.save(post);
    }

    public Post disablePost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "this post not exists"
                )
        );

        if (!post.getIsEnabled())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "this post is already disabled"
            );

        historyService.newHistory(post, Status.DISABLED);
        post.setIsEnabled(false);
        return postRepository.save(post);
    }

    public Post reprocessPost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "post not exists on database"
                )
        );
        List<History> histories = post.getHistory();
        if (histories.get(histories.size() - 1).getStatus() != Status.ENABLED && histories.get(histories.size() - 1).getStatus() != Status.DISABLED)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "this post must be enabled or disabled for reprocess"
            );

        if (post.getProcessed_at().plusSeconds(5).isAfter(LocalDateTime.now()))
            return post;

        try {
            historyService.newHistory(post, Status.UPDATING);
            historyService.newHistory(post, Status.POST_FIND);
            var response = fetch.getForEntity(Constants.EXTERNAL_API_POST + "/" + id, Post.class);
            historyService.newHistory(post, Status.POST_OK);

            validateResponse(response);

            post.setTitle(response.getBody().getTitle());
            post.setBody(response.getBody().getBody());

            fetchComments(post, post.getId());
            post.setProcessed_at(LocalDateTime.now());

        } catch (Exception e) {
            historyService.newHistory(post, Status.FAILED);
            historyService.newHistory(post, Status.DISABLED);
            post.setProcessed_at(LocalDateTime.now());
            throw e;
        }
        return postRepository.save(post);
    }

    private void fetchComments(Post post, Integer id) {
        historyService.newHistory(post, Status.COMMENT_FIND);
        var response = fetch.getForEntity(
                Constants.EXTERNAL_API_POST + "/" + id + "/comments",
                Comment[].class
        );

        validateResponse(response);

        List<Comment> comments = List.of(Objects.requireNonNull(response.getBody()));

        // simplify reduce and equals methods
        post.getComments().clear();
        post.getComments().addAll(comments);

        historyService.newHistory(post, Status.COMMENT_OK);

        post.setIsEnabled(true);
        historyService.newHistory(post, Status.ENABLED);
    }

    public void validateResponse(ResponseEntity<?> response) {
        if (response.getStatusCode().isError() || response.getBody() == null) {
            throw new ResponseStatusException(
                    HttpStatus.valueOf(
                            response.getStatusCode().value()
                    ),
                    "An error occurred while fetching the api, post marked as failed"
            );
        }
    }

    public PostResponseDTO<List<Post>> getAll(Integer pageNumber, Integer size, String sortBy, String direction, String disabled) {
        Pageable pageable;
        if (direction.equalsIgnoreCase("asc"))
            pageable = PageRequest.of(pageNumber, size, Sort.Direction.ASC, sortBy);
        else
            pageable = PageRequest.of(pageNumber, size, Sort.Direction.DESC, sortBy);
        Page<Post> page;
        if (Boolean.parseBoolean(disabled))
            page = postRepository.findAll(pageable);
        else
            page = postRepository.findAllEnabled(pageable);

        return new PostResponseDTO<>(
                page.getSize(),
                page.getNumber(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.isLast(),
                page.get().collect(Collectors.toList())
        );

    }
}
