package store.makejewelry.BE.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Order;
import store.makejewelry.BE.model.*;
import store.makejewelry.BE.repository.OrderRepository;
import store.makejewelry.BE.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("api/order")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class OrderAPI {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("")
    public ResponseEntity<OrderRespone> createOrder (@RequestBody OrderRequest orderRequest){
        OrderRespone orderRespone = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(orderRespone);
    }

    @PostMapping("{productTemplateId}")
    public ResponseEntity<OrderRespone> createOrderProductTemplate (@RequestBody OrderRequest orderRequest, @PathVariable long productTemplateId){
        OrderRespone orderRespone = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(orderRespone);
    }

    @GetMapping("")
    public ResponseEntity<List<Order>>listOrder (){
        List<Order> list = orderRepository.findAll();
        return ResponseEntity.ok(list);
    }

//    @PutMapping("Send-Work/{id}")
//    public ResponseEntity<Order> getPendingOrders (@PathVariable long id,@RequestBody AccountIDRequest accountIDRequest){
//        Order order = orderService.sendTaskOrders(id ,accountIDRequest);
//        return ResponseEntity.ok(order);
//    }
//
//    @PutMapping("TaskSeller/{id}")
//    public ResponseEntity<Order> taskSeller (@PathVariable long id){
//        Order order = orderService.taskSeller(id);
//        return ResponseEntity.ok(order);
//    }

//    @PutMapping("TaskDesigner")
//    public ResponseEntity<Order> taskDesigner (@RequestParam("id") long id, @RequestParam("image") String image){
//        Order order = orderService.taskDesigner(id,image);
//        return ResponseEntity.ok(order);
//    }
//
//    @PutMapping("TaskMakerProduct/{id}")
//    public ResponseEntity<Order> taskMakerProduct (@PathVariable long id){
//        Order order = orderService.taskMakerProduct(id);
//        return ResponseEntity.ok(order);
//    }
//
//    @GetMapping("listOrderRequest")
//    public ResponseEntity<List<Order>> listOrderRequest (){
//        List<Order> list = orderService.listOrderRequest();
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("listOrderTask")
//    public ResponseEntity<List<Order>> listOrderTask (){
//        List<Order> list = orderService.listOrderTask();
//        return ResponseEntity.ok(list);
//    }
//    @PreAuthorize("hasAuthority('MANAGER')")
//    @PutMapping("/ChangeStatusProcessOrder")
//    public ResponseEntity<Order> changeStatusProcessOrder(@RequestParam("id") long id, @RequestParam("status") String status) {
//        Order order = orderService.changeStatusProcessOrder(id,status);
//        return ResponseEntity.ok(order);
//    }

//    @PutMapping("/checkAcceptCustomer")
//    public ResponseEntity<Order> checkAcceptCustomer(@RequestParam("id") long id, @RequestParam("status") String status) {
//        Order order = orderService.checkAcceptCustomer(id,status);
//        return ResponseEntity.ok(order);
//    }

    @GetMapping("/GetOrderByAccountId")
    public ResponseEntity<List<Order>> listOrderAccount (){
        List<Order> list = orderService.listOrderAccount();
        return ResponseEntity.ok(list);
    }

//    @PostMapping("Booking-Template")
//    public ResponseEntity<Order> bookingProductTemplate (@RequestBody BookingRequest bookingRequest){
//        Order order = orderService.bookProductTemplate(bookingRequest);
//        return ResponseEntity.ok(order);
//    }

//    @DeleteMapping("cancelOrder")
//    public ResponseEntity<Order> cancelOrder (@PathVariable long id ){
//        Order order = orderService.cancelOrder(id);
//            return ResponseEntity.ok(order);
//    }

//    @GetMapping("listOrderWaitAcceptManager")
//    public ResponseEntity<List<Order>> listOrderWaitAcceptManager(){
//        List<Order> list = orderService.listWaitAcceptManager();
//        return ResponseEntity.ok(list);
//    }

    @PostMapping("/PaymentOrder/{id}")
    public ResponseEntity<Order> paymentOrder(@RequestBody RechargeRequestDTO rechargeRequestDTO, @PathVariable long id) throws Exception {
        Order order = orderService.paymentOrder(rechargeRequestDTO, id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("Search")
    public ResponseEntity<List<Order>> searchOrder(@RequestParam("param") String param) {
        List<Order> list = orderRepository.findByIdOrNameQuery(param);
        return ResponseEntity.ok(list);
    }

    @PatchMapping("changPriceByManager")
    public ResponseEntity<Order> changPriceMânger(@RequestParam long id , @RequestParam float price){
        Order order = orderService.changPriceByManager(id,price);
        return ResponseEntity.ok(order);
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> orderDetail(@PathVariable long id){
        Order order = orderService.orderDetail(id);
        return ResponseEntity.ok(order);
    }


    @PutMapping("/changeStatus")
    public ResponseEntity changeStatus(@RequestBody OrderRequestDTO orderRequestDTO){
        Order order = orderService.changeStatus(orderRequestDTO);
        return ResponseEntity.ok(order);
    }


}



