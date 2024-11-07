package portal.vanguardia.service;

import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Newspaper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface NewspaperService {
    Newspaper saveNewspaper(Newspaper newspaper, MultipartFile image, MultipartFile file) throws IOException;
    Newspaper updateNewspaper(Newspaper newspaper);
    List<Newspaper> getNewspapers();
    Optional<Newspaper> getNewspaperById(Long id);
    void deleteNewspaper(Newspaper newspaper) throws IOException;
    Newspaper updateNewspaperImage(MultipartFile image, Newspaper newspaper) throws IOException;
    Newspaper updateNewspaperFile(MultipartFile file, Newspaper newspaper) throws IOException;
}
