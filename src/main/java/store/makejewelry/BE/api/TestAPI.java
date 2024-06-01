package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "api")
public class TestAPI {
    @GetMapping("test")
    public ResponseEntity test(){
        return ResponseEntity.ok("test ok");
    }
}
