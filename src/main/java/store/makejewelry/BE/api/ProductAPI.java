package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.*;
import store.makejewelry.BE.model.DetailProduct;
import store.makejewelry.BE.model.Email.Admin.ProductRequest;
import store.makejewelry.BE.model.Email.Admin.ProductResponse;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.repository.*;
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

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StoneRepository stoneRepository;

    @Autowired
    OrderRepository orderRepository;

    @PatchMapping("/{id}")
    public ResponseEntity disableProduct(@PathVariable long id) {
        // nhan request tu FE
        DisableMethodRespone disableProductTpl = productService.disableProduct(id);
        return ResponseEntity.ok(disableProductTpl);
    }

    @PutMapping("/{id}")
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

//    @PutMapping("/designerWork/{id}")
//    public ResponseEntity designerWork(@RequestBody ProductRequest productRequest, long id) {
//        // nhan request tu FE
//        ProductResponse updateProductResponse = productService.designerWork(productRequest , id );
//        return ResponseEntity.ok(updateProductResponse);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<DetailProduct> detailProduct (long id){
//        Order order = orderRepository.findOrderById(id);
//        Product product = productRepository.findProductByOrderId(id);
//        Material material = materialRepository.findMaterialById(product.getId());
//        Category category = categoryRepository.findCategoryById(product.getId());
//        Stone stone = stoneRepository.findStoneById(product.getId());
//        DetailProduct detailProduct = new DetailProduct();
//        detailProduct.setProductName(product.getProductName());
//        detailProduct.setId(product.getId());
//        detailProduct.setImage(product.getImage());
//        detailProduct.setSize(product.getSize());
//        detailProduct.setThickness(product.getThickness());
//        detailProduct.setPrice(product.getPrice());
//        detailProduct.setWeight(product.getWeight());
//        detailProduct.setMaterial(material);
//        detailProduct.setCategory(category);
//        detailProduct.setStone(stone);
//        detailProduct.setDescription(product.getDescription());
//        return ResponseEntity.ok(detailProduct);
//    }
}