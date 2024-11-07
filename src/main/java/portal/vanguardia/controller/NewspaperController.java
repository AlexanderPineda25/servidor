package portal.vanguardia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Newspaper;
import portal.vanguardia.service.impl.NewspaperServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/newspaper")
public class NewspaperController {
    @Autowired
    NewspaperServiceImpl newspaperServiceImpl;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Newspaper> saveNewspaper(@RequestPart("newspaper") Newspaper newspaper, @RequestPart(value = "image", required = false) MultipartFile image,
                                              @RequestPart(value = "file", required = false)MultipartFile file) {
        try {
            Newspaper savedBook = newspaperServiceImpl.saveNewspaper(newspaper,image, file);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Newspaper> updateNewspaperImage(@PathVariable Long id, @RequestPart("image") MultipartFile image) throws IOException {
        Optional<Newspaper> newspaper = newspaperServiceImpl.getNewspaperById(id);
        if (newspaper.isPresent()) {
            Newspaper updatedBook = newspaperServiceImpl.updateNewspaperImage(image, newspaper.get());
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/file")
    public ResponseEntity<Newspaper> updateNewspaperFile(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Newspaper> newspaper = newspaperServiceImpl.getNewspaperById(id);
        if (newspaper.isPresent()) {
            Newspaper updatedBook = newspaperServiceImpl.updateNewspaperFile(file, newspaper.get());
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Newspaper> updateNewspaper(@RequestBody Newspaper newspaper){
        try {
            Newspaper savedBook = newspaperServiceImpl.updateNewspaper(newspaper);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Newspaper>> getAllNewspapers(){
        return new ResponseEntity<>(newspaperServiceImpl.getNewspapers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Newspaper> getNewspaperById(@PathVariable Long id) {
        Optional<Newspaper> newspaper = newspaperServiceImpl.getNewspaperById(id);
        return newspaper.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewspaper(@PathVariable Long id) throws IOException {
        Optional<Newspaper> newspaper = newspaperServiceImpl.getNewspaperById(id);
        if (newspaper.isPresent()){
            newspaperServiceImpl.deleteNewspaper(newspaper.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
