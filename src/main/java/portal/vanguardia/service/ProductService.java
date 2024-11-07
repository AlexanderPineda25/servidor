package portal.vanguardia.service;

import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product, MultipartFile file) throws IOException;
    Product updateProduct(Product product);
    List<Product> getProducts();
    Optional<Product> getProductById(Long id);
    void deleteProduct(Product product) throws IOException;
    Product updateProductImage(MultipartFile file, Product product) throws IOException;
}
