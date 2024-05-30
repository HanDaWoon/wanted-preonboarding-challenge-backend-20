package wanted.preonboard.market.service.Product;

import wanted.preonboard.market.domain.product.dto.ProductInsertDto;
import wanted.preonboard.market.domain.product.dto.ProductUpdateDto;
import wanted.preonboard.market.domain.product.Product;

import java.util.List;

public interface ProductService {
    Boolean createProduct(Integer sellerId, ProductInsertDto product);

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Boolean updateProductById(Integer productId, ProductUpdateDto product);
}
