package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Order;
import store.makejewelry.BE.model.RechargeRequestDTO;
import store.makejewelry.BE.service.VNpaymentService;

@RestController
@RequestMapping("api/payment")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class VNPaymentAPI {

    @Autowired
    VNpaymentService vnPaymentService;

    @PostMapping
    public ResponseEntity createUrl(@RequestBody  RechargeRequestDTO rechargeRequestDTO) throws Exception {
        String url = vnPaymentService.createUrl(rechargeRequestDTO );
        return ResponseEntity.ok(url);
    }
}
