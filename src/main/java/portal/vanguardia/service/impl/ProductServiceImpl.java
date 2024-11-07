package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Image;
import portal.vanguardia.entity.Product;
import portal.vanguardia.repository.ProductRepository;
import portal.vanguardia.service.ImageService;
import portal.vanguardia.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Override
    public Product saveProduct(Product product, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()){
            Image image = imageService.uploadImage(file);
            product.setImage(image);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product){
        return productRepository.save(product);
    }
    @Override
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    @Override
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    @Override
    public void deleteProduct(Product product) throws IOException {
        if (product.getImage() != null) {
            imageService.deleteImage(product.getImage());
        }
        productRepository.deleteById(product.getId());
    }
    @Override
    public Product updateProductImage(MultipartFile file, Product product) throws IOException {
        if (product.getImage() != null) {
            imageService.deleteImage(product.getImage());
        }
        Image newImage = imageService.uploadImage(file);
        product.setImage(newImage);
        return productRepository.save(product);
    }
}
