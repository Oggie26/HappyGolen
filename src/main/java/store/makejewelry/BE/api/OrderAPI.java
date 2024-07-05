package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.makejewelry.BE.repository.OrderRepository;
import store.makejewelry.BE.service.OrderService;

@RestController
@RequestMapping("api/order")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class OrderAPI {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

}
