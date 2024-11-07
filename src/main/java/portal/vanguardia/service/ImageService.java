package portal.vanguardia.service;

import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Image;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile image) throws IOException;
    void deleteImage(Image image) throws IOException;
}
