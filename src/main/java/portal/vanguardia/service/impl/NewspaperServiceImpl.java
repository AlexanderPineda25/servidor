package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.repository.NewspaperRepository;
import portal.vanguardia.entity.File;
import portal.vanguardia.entity.Image;
import portal.vanguardia.entity.Newspaper;
import portal.vanguardia.service.FileService;
import portal.vanguardia.service.ImageService;
import portal.vanguardia.service.NewspaperService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NewspaperServiceImpl implements NewspaperService {

    private final NewspaperRepository newspaperRepository;
    private final ImageService imageService;
    private final FileService fileService;

    public NewspaperServiceImpl(NewspaperRepository newspaperRepository, ImageService imageService, FileService fileService) {
        this.newspaperRepository = newspaperRepository;
        this.imageService = imageService;
        this.fileService = fileService;
    }
    @Override
    public Newspaper saveNewspaper(Newspaper newspaper, MultipartFile image, MultipartFile file) throws IOException {
        if (image != null && !image.isEmpty()) {
            Image newImage = imageService.uploadImage(image);
            newspaper.setImage(newImage);
        }
        if (file != null && !file.isEmpty()) {
            File newFile = fileService.uploadFile(file);
            newspaper.setFile(newFile);
        }
        return newspaperRepository.save(newspaper);
    }
    @Override
    public Newspaper updateNewspaper(Newspaper newspaper){
        return newspaperRepository.save(newspaper);
    }
    @Override
    public List<Newspaper> getNewspapers(){
        return newspaperRepository.findAll();
    }
    @Override
    public Optional<Newspaper> getNewspaperById(Long id){
        return newspaperRepository.findById(id);
    }
    @Override
    public void deleteNewspaper(Newspaper newspaper) throws IOException {
        if (newspaper.getImage() != null) {
            imageService.deleteImage(newspaper.getImage());
        }
        if (newspaper.getFile() != null) {
            fileService.deleteFile(newspaper.getFile());
        }
        newspaperRepository.deleteById(newspaper.getId());
    }
    @Override
    public Newspaper updateNewspaperImage(MultipartFile image, Newspaper newspaper) throws IOException {
        if (newspaper.getImage() != null) {
            imageService.deleteImage(newspaper.getImage());
        }
        Image newImage = imageService.uploadImage(image);
        newspaper.setImage(newImage);
        return newspaperRepository.save(newspaper);
    }
    @Override
    public Newspaper updateNewspaperFile(MultipartFile file, Newspaper newspaper) throws IOException {
        if (newspaper.getFile() != null) {
            fileService.deleteFile(newspaper.getFile());
        }
        File newFile = fileService.uploadFile(file);
        newspaper.setFile(newFile);
        return newspaperRepository.save(newspaper);
    }
}
