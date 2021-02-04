package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
    ConcurrentHashMap<Long, String> postsColl = new ConcurrentHashMap<>();
    Post post1 = new Post(1, "Первый пост");
    Post post2 = new Post(2, "Второй пост");

    public List<Post> all() {
        postsColl.put(post1.getId(), post1.getContent());
        postsColl.put(post2.getId(), post2.getContent());
        return (List) new ArrayList(postsColl.values());
    }

    public Optional<Post> getById(long id) {
        Optional post = Optional.ofNullable(postsColl.get(id));
        return post;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            postsColl.put(post.getId(), post.getContent());
        }
        if (post.getContent() == null) {
            System.out.println("Вероятно пост с таким id удален, обновление не возможно");
        } else {
            postsColl.replace(post.getId(), post.getContent());
        }
        return post;
    }

    public void removeById(long id) {
        postsColl.remove(id);
    }
}
