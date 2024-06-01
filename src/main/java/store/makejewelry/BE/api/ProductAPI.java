package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.model.Admin.ProductTemplateRequest;
import store.makejewelry.BE.model.Admin.ProductTemplateResponse;
import store.makejewelry.BE.model.DisableMethodRequest;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.service.ProductService;

@RestController
@RequestMapping("api/product")
@SecurityRequirement(name = "api")
@CrossOrigin("*")

public class ProductAPI {

    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity addProduct(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse addProductResponse = productService.addProduct(productTemplateRequest);
        return ResponseEntity.ok(addProductResponse);
    }

    @PatchMapping("")
    public ResponseEntity disableProduct(@RequestBody DisableMethodRequest disableMethodRequest) {
        // nhan request tu FE
        DisableMethodRespone disableProductTpl = productService.disableProductTpl(disableMethodRequest);
        return ResponseEntity.ok(disableProductTpl);
    }

    @PutMapping("")
    public ResponseEntity updateProduct(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse updateProductTemplateResponse = productService.addProduct(productTemplateRequest);
        return ResponseEntity.ok(updateProductTemplateResponse);
    }

    @DeleteMapping("")
    public ResponseEntity deleteProduct(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse deleteProductTemplateResponse = productService.updateProduct(productTemplateRequest);
        return ResponseEntity.ok(deleteProductTemplateResponse);
    }
}