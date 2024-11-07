package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.repository.ImageRepository;
import portal.vanguardia.entity.Image;
import portal.vanguardia.service.CloudinaryService;
import portal.vanguardia.service.ImageService;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(CloudinaryService cloudinaryService, ImageRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image uploadImage(MultipartFile image) throws IOException {
        Map uploadResult = cloudinaryService.upload(image);
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");
        Image newImage = new Image(image.getOriginalFilename(), imageUrl, imageId);
        return imageRepository.save(newImage);
    }

    @Override
    public void deleteImage(Image image) throws IOException {
        cloudinaryService.delete(image.getImageId());
        imageRepository.deleteById(image.getId());
    }
}
