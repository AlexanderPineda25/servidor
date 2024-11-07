package portal.vanguardia.service;

import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.File;

import java.io.IOException;

public interface FileService {
    File uploadFile(MultipartFile file) throws IOException;
    void deleteFile(File file) throws IOException;
}
