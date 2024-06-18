package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Product;
import store.makejewelry.BE.model.Admin.ProductRequest;
import store.makejewelry.BE.model.Admin.ProductResponse;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.repository.ProductRepository;
import store.makejewelry.BE.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("api/product")
@SecurityRequirement(name = "api")
@CrossOrigin("*")

public class ProductAPI {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping()
    public ResponseEntity addProduct(@RequestBody ProductRequest productRequest) {
        // nhan request tu FE
        ProductResponse addProductResponse = productService.addProduct(productRequest);
        return ResponseEntity.ok(addProductResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity disableProduct(@PathVariable long id) {
        // nhan request tu FE
        DisableMethodRespone disableProductTpl = productService.disableProduct(id);
        return ResponseEntity.ok(disableProductTpl);
    }

    @PutMapping("")
    public ResponseEntity updateProduct(@RequestBody ProductRequest productRequest, long id) {
        // nhan request tu FE
        ProductResponse updateProductResponse = productService.updateProduct(productRequest , id );
        return ResponseEntity.ok(updateProductResponse);
    }

    @GetMapping("")
    public ResponseEntity <List<Product>> getAllProduct() {
        List<Product> list = productRepository.findAll();
        return ResponseEntity.ok(list);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Product>> searchProduct(@RequestParam("productName") String productName, @RequestParam("id") long id) {
//            return (ResponseEntity<List<Product>>) productRepository.findProductByIdOrProductName(id , productName);
//        }

}