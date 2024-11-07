package portal.vanguardia.service;

import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Book;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface BookService {
    Book saveBook(Book book, MultipartFile image, MultipartFile file) throws IOException;
    Book updateBook(Book book);
    List<Book> getBooks();
    Optional<Book> getBookById(Long id);
    void deleteBook(Book book) throws IOException;
    Book updateBookImage(MultipartFile image, Book book) throws IOException;
    Book updateBookFile(MultipartFile file, Book book) throws IOException;

}
