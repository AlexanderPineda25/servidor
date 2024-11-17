package portal.vanguardia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portal.vanguardia.entity.Product;
import portal.vanguardia.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestPart("product") Product product, @RequestPart("file") MultipartFile file) {
        try {
            Product savedProduct = productServiceImpl.saveProduct(product, file);
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}/image")
    public ResponseEntity<Product> updateProductImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Product> product = productServiceImpl.getProductById(id);
        if (product.isPresent()) {
            Product updatedProduct = productServiceImpl.updateProductImage(file, product.get());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        try {
            Product savedProduct = productServiceImpl.updateProduct(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productServiceImpl.getProducts(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductId(@PathVariable Long id) {
        Optional<Product> product = productServiceImpl.getProductById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws IOException {
        Optional<Product> product = productServiceImpl.getProductById(id);
        if (product.isPresent()){
            productServiceImpl.deleteProduct(product.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
