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



    @Query(value = "  Select t.code from token t \n" +
            "inner join user u on t.user_id  = u.id\n" +
            "inner join post p on u.id = p.user_id where u.id = ?1", nativeQuery = true)
    String obtenerToken(int id);



}
