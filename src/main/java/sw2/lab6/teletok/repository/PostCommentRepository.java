package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    @Query(value = "select MAX(post_comment.id) from post_comment", nativeQuery = true)
    int ultimoidinsertado();
}
