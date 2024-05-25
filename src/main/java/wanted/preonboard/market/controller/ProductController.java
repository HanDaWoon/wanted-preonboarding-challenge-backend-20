package wanted.preonboard.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wanted.preonboard.market.config.CustomUser;
import wanted.preonboard.market.domain.dto.ProductInsertDto;
import wanted.preonboard.market.domain.dto.ProductUpdateDto;
import wanted.preonboard.market.domain.dto.ResponseBad;
import wanted.preonboard.market.domain.dto.ResponseOk;
import wanted.preonboard.market.message.ProductMessage;
import wanted.preonboard.market.service.Product.ProductService;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal CustomUser user, @RequestBody ProductInsertDto product) {
        ResponseEntity<Map<String, Object>> response;
        try {
            if (productService.createProduct(user.getMember().getId(), product)) {
                response = new ResponseOk(ProductMessage.REGISTERED_SUCCESSFULLY.getMessage()).toResponse();
            } else {
                response = new ResponseBad(ProductMessage.ERROR.getMessage()).toResponse();
            }
        } catch (Exception e) {
            response = new ResponseBad(ProductMessage.ERROR.getMessage() + e.getMessage()).toResponse();
        }
        return response;
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        return new ResponseOk(productService.getProducts()).toResponse();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        try {
            return new ResponseOk(productService.getProductById(productId)).toResponse();
        } catch (NullPointerException e) {
            return new ResponseBad(ProductMessage.INVALID_PRODUCT_ID.getMessage()).toResponse();
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProductById(@PathVariable Long productId, @RequestBody ProductUpdateDto product) {
        try {
            return new ResponseOk(productService.updateProductById(productId, product)).toResponse();
        } catch (NullPointerException e) {
            System.out.println(e);
            return new ResponseBad(ProductMessage.INVALID_PRODUCT_ID.getMessage()).toResponse();
        } catch (Exception e) {
            return new ResponseBad(ProductMessage.ERROR.getMessage() + e.getMessage()).toResponse();
        }
    }
}
