package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portal.vanguardia.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
