package portal.vanguardia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Book;
import portal.vanguardia.service.impl.BookServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    BookServiceImpl bookServiceImpl;
    @Autowired
    public BookController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Book> saveBook(@RequestPart("book") Book book, @RequestPart(value = "image", required = false)MultipartFile image,
                                         @RequestPart(value = "file", required = false)MultipartFile file) {
        try {
            Book savedBook = bookServiceImpl.saveBook(book,image, file);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}/image")
    public ResponseEntity<Book> updateBookImage(@PathVariable Long id, @RequestPart("image") MultipartFile image) throws IOException {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if (book.isPresent()) {
            Book updatedBook = bookServiceImpl.updateBookImage(image, book.get());
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/file")
    public ResponseEntity<Book> updateBookFile(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if (book.isPresent()) {
            Book updatedBook = bookServiceImpl.updateBookFile(file, book.get());
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        try {
            Book savedBook = bookServiceImpl.updateBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookServiceImpl.getBooks(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws IOException {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if (book.isPresent()){
            bookServiceImpl.deleteBook(book.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
