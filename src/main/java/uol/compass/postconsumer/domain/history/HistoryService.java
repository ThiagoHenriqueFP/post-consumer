package uol.compass.postconsumer.domain.history;

import org.springframework.stereotype.Service;
import uol.compass.postconsumer.domain.Status.Status;
import uol.compass.postconsumer.domain.post.Post;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void newHistory(Post post, Status status){
//        History history = new History(status);
//        history.setPost(post);
//        History saved = historyRepository.save(history);
        post.getHistory().add(new History(status));
    }
}
