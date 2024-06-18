package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Product;
import store.makejewelry.BE.entity.ProductTemplate;
import store.makejewelry.BE.model.DisableMethodRequest;
import store.makejewelry.BE.model.Admin.ProductTemplateRequest;
import store.makejewelry.BE.model.Admin.ProductTemplateResponse;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.repository.ProductTemplateRepository;
import store.makejewelry.BE.service.ProductTemplateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product-template")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ProductTemplateAPI {
    @Autowired
    ProductTemplateService productTemplateService;

    @Autowired
    ProductTemplateRepository productTemplateRepository;

    @PostMapping()
    public ResponseEntity addProductTpl(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse addProductTemplateResponse = productTemplateService.addProductTpl(productTemplateRequest);
        return ResponseEntity.ok(addProductTemplateResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity disableProductTpl(long id) {
        // nhan request tu FE
        DisableMethodRespone disableProductTpl = productTemplateService.disableProductTpl(id);
        return ResponseEntity.ok(disableProductTpl);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductTemplateResponse> updateProductTpl(@RequestBody ProductTemplateRequest productTemplateRequest, @PathVariable long id) {
            ProductTemplateResponse updateProductTemplateResponse = productTemplateService.updateProductTpl(productTemplateRequest, id);
            return ResponseEntity.ok(updateProductTemplateResponse);
    }

    @GetMapping("")
    public List<ProductTemplate> getAllProduct() {
        List<ProductTemplate> productList = productTemplateRepository.findAll();
        return productList;
    }
    @GetMapping("/search")
    public List<ProductTemplate> searchProductTemplate(@RequestParam("productName") String productName, @RequestParam("id") long id) {
        return productTemplateRepository.searchByProductNameAndProductId(productName , id);
    }
}
