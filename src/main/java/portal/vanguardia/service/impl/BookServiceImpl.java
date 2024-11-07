package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.repository.BookRepository;
import portal.vanguardia.entity.Book;
import portal.vanguardia.entity.File;
import portal.vanguardia.entity.Image;
import portal.vanguardia.service.BookService;
import portal.vanguardia.service.FileService;
import portal.vanguardia.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ImageService imageService;
    private final FileService fileService;

    public BookServiceImpl(BookRepository bookRepository, ImageService imageService, FileService fileService) {
        this.bookRepository = bookRepository;
        this.imageService = imageService;
        this.fileService = fileService;
    }

    @Override
    public Book saveBook(Book book,MultipartFile image, MultipartFile file) throws IOException {
        if (image != null && !image.isEmpty()) {
            Image newImage = imageService.uploadImage(image);
            book.setImage(newImage);
        }
        if (file != null && !file.isEmpty()) {
            File newFile = fileService.uploadFile(file);
            book.setFile(newFile);
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book){
        return bookRepository.save(book);
    }
    @Override
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    @Override
    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }
    @Override
    public void deleteBook(Book book) throws IOException {
        if (book.getImage() != null) {
            imageService.deleteImage(book.getImage());
        }
        if (book.getFile() != null) {
            fileService.deleteFile(book.getFile());
        }
        bookRepository.deleteById(book.getId());
    }
    @Override
    public Book updateBookImage(MultipartFile image, Book book) throws IOException {
        if (book.getImage() != null) {
            imageService.deleteImage(book.getImage());
        }
        Image newImage = imageService.uploadImage(image);
        book.setImage(newImage);
        return bookRepository.save(book);
    }
    @Override
    public Book updateBookFile(MultipartFile file, Book book) throws IOException {
        if (book.getFile() != null) {
            fileService.deleteFile(book.getFile());
        }
        File newFile = fileService.uploadFile(file);
        book.setFile(newFile);
        return bookRepository.save(book);
    }
}

