package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
