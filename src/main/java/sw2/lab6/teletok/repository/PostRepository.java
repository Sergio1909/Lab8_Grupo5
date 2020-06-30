package sw2.lab6.teletok.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value="SELECT * FROM post p where p.description like %?1%  " +
            "p.user_id= (select id  from user u  where nombrecomunidad like %?1%)",
            nativeQuery = true)
    List<Post> buscadorPost(String search);

}
