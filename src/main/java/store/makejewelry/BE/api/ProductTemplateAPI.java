package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.model.DisableMethodRequest;
import store.makejewelry.BE.model.Admin.ProductTemplateRequest;
import store.makejewelry.BE.model.Admin.ProductTemplateResponse;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.service.ProductTemplateService;

@RestController
@RequestMapping("api/product-template")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ProductTemplateAPI {
    @Autowired
    ProductTemplateService productTemplateService;

    @PostMapping()
    public ResponseEntity addProductTpl(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse addProductTemplateResponse = productTemplateService.addProductTpl(productTemplateRequest);
        return ResponseEntity.ok(addProductTemplateResponse);
    }

    @PatchMapping("")
    public ResponseEntity disableProductTpl(@RequestBody DisableMethodRequest disableMethodRequest) {
        // nhan request tu FE
        DisableMethodRespone disableProductTpl = productTemplateService.disableProductTpl(disableMethodRequest);
        return ResponseEntity.ok(disableProductTpl);
    }

    @PutMapping("")
    public ResponseEntity updateProductTpl(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse updateProductTemplateResponse = productTemplateService.addProductTpl(productTemplateRequest);
        return ResponseEntity.ok(updateProductTemplateResponse);
    }

    @DeleteMapping("")
    public ResponseEntity deleteProductTpl(@RequestBody ProductTemplateRequest productTemplateRequest) {
        // nhan request tu FE
        ProductTemplateResponse deleteProductTemplateResponse = productTemplateService.updateProductTpl(productTemplateRequest);
        return ResponseEntity.ok(deleteProductTemplateResponse);
    }
}
