package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Token;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query(value = "SELECT * FROM teletok.token\n" +
            "where user_id = ?1;", nativeQuery = true)
    public Token token(int id);

}
