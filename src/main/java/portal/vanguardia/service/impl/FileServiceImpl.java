package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.repository.FileRepository;
import portal.vanguardia.entity.File;
import portal.vanguardia.service.CloudinaryService;
import portal.vanguardia.service.FileService;

import java.io.IOException;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private final CloudinaryService cloudinaryService;
    private final FileRepository fileRepository;

    public FileServiceImpl(CloudinaryService cloudinaryService, FileRepository fileRepository) {
        this.cloudinaryService = cloudinaryService;
        this.fileRepository = fileRepository;
    }

    @Override
    public File uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String fileUrl = (String) uploadResult.get("url");
        String fileId = (String) uploadResult.get("public_id");
        File newFile = new File(file.getOriginalFilename(), fileUrl, fileId);
        return fileRepository.save(newFile);
    }

    @Override
    public void deleteFile(File file) throws IOException {
        cloudinaryService.delete(file.getFileId());
        fileRepository.deleteById(file.getId());
    }

}
