package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.*;
import store.makejewelry.BE.enums.OrderStatus;
import store.makejewelry.BE.enums.RoleEnum;
import store.makejewelry.BE.exception.AuthException;
import store.makejewelry.BE.model.*;
import store.makejewelry.BE.model.Email.EmailDetail;
import store.makejewelry.BE.repository.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WarrantyRepository warrantyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductTemplateRepository productTemplateRepository;

    @Autowired
    ProductRepository productRepository;


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProcessOrderRepository processOrderRepository;

    @Autowired
    StoneRepository stoneRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EmailService emailService;

    public OrderRespone createOrder(OrderRequest orderRequest) throws AuthException {
        Account service = authenticationService.getCurrentAccount();
        if (service == null) {
            throw new AuthException("Login to place an order");
        }
        Order order = new Order();
        order.setCustomer(service);
        order.setOrderDate(LocalDateTime.now());
        order.setProductName(orderRequest.getProductName());
        order.setImageTemplate(orderRequest.getImageTemplate());

        ProcessOrder processOrder = new ProcessOrder();
        processOrder.setOrder(order);
        processOrder.setAccount(service);
        processOrder.setStatus(OrderStatus.APPROVAL_MANAGER);
        processOrder.setCreated(LocalDateTime.now());
        order.getProcessOrders().add(processOrder);

        Product product = new Product();
        product.setProductName(order.getProductName());
        product.setDescription(orderRequest.getDescription());
        product.setSize(orderRequest.getSize());
        product.setStatus(true);

        Category category = categoryRepository.findCategoryById(orderRequest.getCategoryId());
        if (category == null || !category.getStatus()) {
            product.setCategory(category);
        }

        Material material = materialRepository.findMaterialById(orderRequest.getMaterialId());
        if (material == null || !material.getStatus()) {
            return null;
        }
        product.setMaterial(material);
        product.setThickness(orderRequest.getThickness());
        ProductService productService = new ProductService();
        Float priceGolden = material.getPrice();
        float makePrice = 1.1F;
        product.setWeight(productService.weight(orderRequest.getSize(), orderRequest.getThickness()));
        product.setPrice(((productService.circlePrice(orderRequest.getSize(), orderRequest.getThickness(), priceGolden)) * makePrice) * orderRequest.getQuantity());

        Stone stone = stoneRepository.findStoneById(orderRequest.getStoneId());
        if (stone != null) {
            product.setStone(stone);
            Float stonePrice = stone.getPrice();
            if (stonePrice == null) {
                stonePrice = 0.0F;
            }
            product.setPrice((product.getPrice() + stonePrice) * makePrice);
        }
        product = productRepository.save(product);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity(orderRequest.getQuantity());
        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setOrder(order);
        order.getOrderDetail().add(orderDetail);
        order.setTotal(orderDetail.getQuantity() * orderDetail.getPrice());
        orderRepository.save(order);

        OrderRespone orderRespone = new OrderRespone();
        orderRespone.setId(order.getId());
        orderRespone.setQuantity(orderRequest.getQuantity());
        orderRespone.setCustomerName(service.getFullName());
        orderRespone.setImage(orderRequest.getImageTemplate());
        orderRespone.setMaterial(product.getMaterial());
        orderRespone.setStone(product.getStone());
        orderRespone.setCategory(product.getCategory());
        orderRespone.setProductName(orderRequest.getProductName());
        orderRespone.setDescription(orderRequest.getDescription());
        orderRespone.setTotal(order.getTotal());
        orderRespone.setEmail(service.getEmail());
        orderRespone.setGender(service.getGender());
        orderRespone.setPhone(service.getPhone());
        orderRespone.setWeight(product.getWeight());
        orderRespone.setSize(product.getSize());
        orderRespone.setThickness(product.getThickness());

        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(service.getEmail());
            emailDetail.setMsgBody("Thank " + service.getFullName() + " for choosing HappyGolden! We are delighted to have you join us and place your order." +
                    " Your trust in our products and services means a lot to us.  " + order.getId());
            emailDetail.setSubject("HappyGolden");
            emailDetail.setButton("View Your Order");
            emailDetail.setLink("http://159.223.64.244/myOrder");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    emailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        } catch (Exception e) {
            System.out.println("Error to send mail to ");
        }

        return orderRespone;
    }
    //Phan cong cho staff
//    public Order sendTaskOrders(long id , AccountIDRequest accountIDRequest) {
//        Order order = orderRepository.findOrderById(id);
//        Account current = authenticationService.getCurrentAccount();
//        Account account = accountRepository.findAccountById(accountIDRequest.getStaffId());
//        if (order != null && current.getRole().equals(RoleEnum.MANAGER)) {
//            ProcessOrder newProcessOrder = new ProcessOrder();
//            newProcessOrder.setName(account.getFullName());
//            newProcessOrder.setRole(account.getRole());
//            newProcessOrder.setAccount(account);
//            newProcessOrder.setOrder(order);
//            order.getProcessOrders().add(newProcessOrder);
//            processOrderRepository.save(newProcessOrder);
//            OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(order.getId());
//            if (order.getStatus() == OrderStatus.INIT){
//                order.setStatus(OrderStatus.SELLER_RECEIVE);
//            } else if (order.getStatus() == OrderStatus.PAYMENT_SUCCESSFULLY) {
//                if (orderDetail.getProduct() != null){
//                    order.setStatus(OrderStatus.DESIGNER_RECEIVE);
//                }else{
//                    order.setStatus(OrderStatus.MAKER_RECEIVE);
//                }
//            }else if (order.getStatus() == OrderStatus.APPROVAL3D_CUSTOMER){
//                order.setStatus(OrderStatus.MAKER_RECEIVE);
//            }
//            try {
//                EmailDetail emailDetail = new EmailDetail();
//                emailDetail.setRecipient(account.getEmail());
//                emailDetail.setMsgBody("You are assigned by manager " + order.getId() +
//                        "Please check your task .");
//                emailDetail.setSubject("HappyGolden");
//                emailDetail.setButton("Your Task");
//                emailDetail.setLink("http://159.223.64.244/dashboard/staff-task");
//                Runnable r = new Runnable() {
//                    @Override
//                    public void run() {
//                        emailService.sendMailTemplate(emailDetail);
//                    }
//                };
//                new Thread(r).start();
//            } catch (Exception e) {
//                System.out.println("Error to send mail to " );
//            }
//            return orderRepository.save(order);
//        } else {
//            return null;
//        }
//    }
//
//    //Task cua Seller
//    public Order taskSeller(long id) {
//        Account service = authenticationService.getCurrentAccount();
//        if (service != null) {
//            Order order = orderRepository.findOrderById(id);
//            OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);
//                if (orderDetail.getProductTemplate() != null) {
//                    if(order.getStatus().equals(OrderStatus.SELLER_RECEIVE)){
//                        order.setStatus(OrderStatus.PAYMENT);
//                    }else if(order.getStatus().equals(OrderStatus.MAKE_SEND)){
//                        order.setStatus(OrderStatus.FINISH_ORDER);
//                        Warranty warranty = new Warranty();
//                        warranty.setCustomerName(service.getFullName());
//                        warranty.setStartDate(new Date());
//                        warranty.setProductName(order.getProductName());
//                        warranty.setAccount(service);
//                        ProductTemplate productTemplate = productTemplateRepository.findProductTemplateByOrderId(id);
//                        warranty.setProductTemplate(productTemplate);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(warranty.getStartDate());
//                        calendar.add(Calendar.YEAR, 2);
//                        Date endDate = calendar.getTime();
//                        warranty.setEndDate(endDate);
//                        warrantyRepository.save(warranty);
//                        try {
//                            EmailDetail emailDetail = new EmailDetail();
//                            emailDetail.setRecipient(service.getEmail());
//                            emailDetail.setMsgBody("Thank " + service.getFullName() + " for choosing HappyGolden! We are delighted to have you join us and place your order." +
//                                    " Your trust in our products and services means a lot to us.  " + order.getId());
//                            emailDetail.setSubject("HappyGolden");
//                            emailDetail.setButton("The Warranty");
//                            emailDetail.setLink("http://159.223.64.244/myOrder");
//                            Runnable r = new Runnable() {
//                                @Override
//                                public void run() {
//                                    emailService.sendMailTemplate(emailDetail);
//                                }
//                            };
//                            new Thread(r).start();
//                        } catch (Exception e) {
//                            System.out.println("Error to send mail to " );
//                        }
//                    }
//                }else{
//                    if (order.getStatus().equals(OrderStatus.SELLER_RECEIVE)){
//                        order.setStatus(OrderStatus.SELLER_SEND);
//                    }else if (order.getStatus().equals(OrderStatus.APPROVAL_MANAGER)){
//                        order.setStatus(OrderStatus.CUSTOMER_RECEIVE);
//                    }else if(order.getStatus().equals(OrderStatus.MAKE_SEND)){
//                        order.setStatus(OrderStatus.FINISH_ORDER);
//                        Warranty warranty = new Warranty();
//                        warranty.setCustomerName(service.getFullName());
//                        warranty.setStartDate(new Date());
//                        warranty.setProductName(order.getProductName());
//                        warranty.setAccount(service);
//                        Product product = productRepository.findProductByOrderId(id);
//                        product.setWarranty(warranty);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(warranty.getStartDate());
//                        calendar.add(Calendar.YEAR, 2);
//                        Date endDate = calendar.getTime();
//                        warranty.setEndDate(endDate);
//                        warrantyRepository.save(warranty);
//                        productRepository.save(product);
//                        try {
//                            EmailDetail emailDetail = new EmailDetail();
//                            emailDetail.setRecipient(service.getEmail());
//                            emailDetail.setMsgBody("Thank " + service.getFullName() + " for choosing HappyGolden! We are delighted to have you join us and place your order." +
//                                    " Your trust in our products and services means a lot to us.  " + order.getId());
//                            emailDetail.setSubject("HappyGolden");
//                            emailDetail.setButton("The Warranty");
//                            emailDetail.setLink("http://159.223.64.244/myOrder");
//                            Runnable r = new Runnable() {
//                                @Override
//                                public void run() {
//                                    emailService.sendMailTemplate(emailDetail);
//                                }
//                            };
//                            new Thread(r).start();
//                        } catch (Exception e) {
//                            System.out.println("Error to send mail to " );
//                        }
//                    }
//                }
//                orderRepository.save(order);
//                return order;
//        }
//        return null;
//    }
//
//    //Task cua Designer
//    public Order taskDesigner(long id, String image) {
//        Account service = authenticationService.getCurrentAccount();
//        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);
//        if (service != null) {
//            Order order = orderRepository.findOrderById(id);
//            if (orderDetail.getProductTemplate() == null){
//                order.setStatus(OrderStatus.DESIGNER_SEND3D);
//                Product product = productRepository.findProductByOrderId(id);
//                product.setImage(image);
//                order.setImage(image);
//                productRepository.save(product);
//                orderRepository.save(order);
//                return order;
//            }else{
//                order.setStatus(OrderStatus.MAKER_RECEIVE);
//                return order;
//            }
//
//        } else {
//            System.out.println("Login to activate");
//            return null;
//        }
//    }
//
//    //Task MakerProduct
//    public Order taskMakerProduct(long id) {
//        Account service = authenticationService.getCurrentAccount();
//        if (service != null) {
//            Order order = orderRepository.findOrderById(id);
//            order.setStatus(OrderStatus.MAKE_SEND);
//            order.setHandDate(new Date());
//            OrderDetail orderDetail = new OrderDetail();
//            orderRepository.save(order);
//            return order;
//        } else {
//            System.out.println("Login to activate");
//            return null;
//        }
//    }
//
//    // Bang Order cho nhan vien
//    public List<Order> listOrderRequest() {
//        List<Order> list = orderRepository.findAll();
//        List<Order> orders = new ArrayList<>();
//        for (Order order : list) {
//            if (order.getStatus().equals(OrderStatus.INIT) ||
//                    order.getStatus().equals(OrderStatus.APPROVAL3D_CUSTOMER) ||
//                    order.getStatus().equals(OrderStatus.PAYMENT_SUCCESSFULLY)) {
//                orders.add(order);
//            }
//        }
//        return orders;
//    }
//
//    //List Order Nhan vien nhan don
//    public List<Order> listOrderTask() {
//        Account account = authenticationService.getCurrentAccount();
//        RoleEnum role = account.getRole();
//        List<Order> orders = new ArrayList<>();
//        List<Order> list = orderRepository.findAll();
//
//        for (Order order : list) {
//            OrderStatus status = order.getStatus();
//            if ((role == RoleEnum.SELLER && (status.equals(OrderStatus.SELLER_RECEIVE) ||
//                    status.equals(OrderStatus.APPROVAL_MANAGER) ||
//                    status.equals(OrderStatus.MAKE_SEND))) ||
//                    (role == RoleEnum.MAKER_PRODUCT && status.equals(OrderStatus.MAKER_RECEIVE)) ||
//                    (role == RoleEnum.DESIGNER && status.equals(OrderStatus.DESIGNER_RECEIVE))) {
//                orders.add(order);
//            }
//        }
//        return orders;
//    }
//    public Order changeStatusProcessOrder(long id, String status) {
//        Order order = orderRepository.findOrderById(id);
//        if (order.getStatus().equals(OrderStatus.SELLER_SEND)) {
//            order.setStatus(status.equalsIgnoreCase("true") ? OrderStatus.APPROVAL_MANAGER : OrderStatus.SELLER_RECEIVE);
//        }
//        return orderRepository.save(order);
//    }
//
//    public Order checkAcceptCustomer  (long id, String status) {
//        Account account = authenticationService.getCurrentAccount();
//        Order order = null;
//        if (account.getRole().equals(RoleEnum.CUSTOMER)) {
//            order = orderRepository.findOrderById(id);
//            if (order.getStatus().equals(OrderStatus.CUSTOMER_RECEIVE)) {
//                order.setStatus(status.equalsIgnoreCase("true") ? OrderStatus.PAYMENT : OrderStatus.SELLER_RECEIVE);
//                orderRepository.save(order);
//            } else if (order.getStatus().equals(OrderStatus.DESIGNER_SEND3D)) {
//                order.setStatus(status.equalsIgnoreCase("true") ? OrderStatus.APPROVAL3D_CUSTOMER : OrderStatus.DESIGNER_RECEIVE);
//                orderRepository.save(order);
//            }
//        } else {
//            System.out.println("Login to activate");
//        }
//        return order;
//    }

    public Order paymentOrder(RechargeRequestDTO rechargeRequestDTO, long id) throws Exception {
        Account account = authenticationService.getCurrentAccount();
        Order order = orderRepository.findOrderById(id);
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);
        if (account.getRole().equals(RoleEnum.CUSTOMER)) {
            VNpaymentService service = new VNpaymentService();
            Float price = order.getTotal();
            rechargeRequestDTO.setAmount(String.valueOf(price));
            String paymentUrl = service.createUrl(rechargeRequestDTO);
            Payment payment = new Payment();
            payment.setDate(new Date());
            payment.setMethod("Card");
            payment.setOrder(payment.getOrder());
            paymentRepository.save(payment);
//            if(orderDetail.getProductTemplate() == null){
//                order.setStatus(OrderStatus.PAYMENT_SUCCESSFULLY);
//            }else{
//                order.setStatus(OrderStatus.MAKER_RECEIVE);
//            }
            order.setPayment(payment);
            orderRepository.save(order);

            return order;
        } else {
            throw new IllegalStateException("Access denied");
        }
    }


    public List<Order> listOrderAccount() {
        Account account = authenticationService.getCurrentAccount();
        return orderRepository.findOrderByCustomer(account);
    }

//    public Order bookProductTemplate (BookingRequest bookingRequest){
//        Order order = new Order();
//        OrderDetail orderDetail = new OrderDetail();
//        Account account = authenticationService.getCurrentAccount();
//        if(account == null){
//            System.out.println("Login to Book");
//        }else{
//            ProductTemplate productTemplate = productTemplateRepository.findProductTemplateById(bookingRequest.getProductTemplateId());
//            if(productTemplate.getStatus()){
//                productTemplate.setSize(bookingRequest.getSize());
//                productTemplate.setThickness(bookingRequest.getThickness());
//                ProductService productService = new ProductService();
//                Float priceGolden = productTemplate.getMaterial().getPrice();
//                float makePrice = 1.1F ;
//                productTemplate.setWeight(productService.weight(bookingRequest.getSize(), bookingRequest.getThickness()));
//                productTemplate.setPrice(((productService.circlePrice(bookingRequest.getSize(), bookingRequest.getThickness(),  priceGolden)) * makePrice) * bookingRequest.getQuantity());
//                productTemplateRepository.save(productTemplate);
//
//                ProcessOrder processOrder = new ProcessOrder();
//                processOrder.setOrder(order);
//                processOrder.setAccount(account);
//                processOrder.setRole(RoleEnum.CUSTOMER);
//                processOrder.setName(account.getFullName());
//                order.getProcessOrders().add(processOrder);
//
//                order.setCustomerName(account.getFullName());
//                order.setOrderDate(new Date());
//                order.setStatus(OrderStatus.INIT);
//                order.setProductName(productTemplate.getProductName());
//                order.setImage(productTemplate.getImage());
//                orderDetail.setQuantity(bookingRequest.getQuantity());
//                orderDetail.setProductTemplate(productTemplate);
//                orderDetail.setPrice(productTemplate.getPrice());
//                orderDetail.setOrder(order);
//
//                order.getOrderDetail().add(orderDetail);
//                order.setTotal(orderDetail.getQuantity() * orderDetail.getPrice());
//                orderRepository.save(order);
//            }
//        }
//        return order;
//    }
//
//    public Order cancelOrder(long id) {
//        Account account = authenticationService.getCurrentAccount();
//        Order order = orderRepository.findOrderById(id);
//
//        if (account != null && order != null) {
//            OrderStatus status = order.getStatus();
//            if (status.equals(OrderStatus.INIT) ||
//                    status.equals(OrderStatus.CUSTOMER_RECEIVE) ||
//                    status.equals(OrderStatus.SELLER_SEND) ||
//                    status.equals(OrderStatus.SELLER_RECEIVE) ||
//                    status.equals(OrderStatus.PAYMENT)) {
//                orderRepository.delete(order);
//            }
//        }
//
//        return order;
//    }
//
//    public List<Order> listWaitAcceptManager() {
//        Account account = authenticationService.getCurrentAccount();
//        List<Order> orders = new ArrayList<>();
//        List<Order> list = orderRepository.findAll();
//        if (account.getRole().equals(RoleEnum.MANAGER)) {
//            for (Order order : list) {
//                if (order.getStatus().equals(OrderStatus.SELLER_SEND)) {
//                    orders.add(order);
//                }
//            }
//        }
//        return orders;
//    }

    public Order changPriceByManager(long id, float newPrice) {
        Account account = authenticationService.getCurrentAccount();
        Order order = orderRepository.findOrderById(id);
        if (account.getRole().equals(RoleEnum.MANAGER)) {
            order.setTotal(newPrice);
        }
        orderRepository.save(order);
        return order;
    }

    public Order orderDetail(long id) {
        Order order = orderRepository.findOrderById(id);
        return order;
    }


    public Order changeStatus(OrderRequestDTO orderRequestDTO) {
        EmailDetail emailDetail = new EmailDetail();

        Order order = orderRepository.findById(orderRequestDTO.getIdOrder()).orElseThrow(() -> new AuthException("order not found"));
        Account account = accountRepository.findAccountById(orderRequestDTO.getIdAccountAssigne());

        if (orderRequestDTO.getOrderStatus() == OrderStatus.CUSTOMER_RECEIVE) {
            order.setTotal(orderRequestDTO.getPrice());
        }

        if (orderRequestDTO.getOrderStatus() == OrderStatus.APPROVAL_MANAGER) {
            order.setTotal(orderRequestDTO.getPrice());
        }

        if (orderRequestDTO.getOrderStatus().equals(OrderStatus.DESIGNER_SEND3D)) {
            order.setImage(orderRequestDTO.getImageDesigner());
        }

        if (orderRequestDTO.getOrderStatus().equals(OrderStatus.DESIGNER_RECEIVE)) {
            order.setMessage(orderRequestDTO.getMessage());
        }

        if (orderRequestDTO.getOrderStatus().equals(OrderStatus.FINISH_ORDER)) {
            for (OrderDetail orderDetail : order.getOrderDetail()) {
                Warranty warranty = new Warranty();
                if (orderDetail.getProduct() != null) {
                    warranty.setProduct(orderDetail.getProduct());
                } else {
                    warranty.setProductTemplate(orderDetail.getProductTemplate());
                }
                warranty.setStartDate(LocalDateTime.now());
                warranty.setEndDate(LocalDateTime.now().plusYears(2));
                warranty.setAccount(order.getCustomer());
                warranty = warrantyRepository.save(warranty);
            }
        }

        if (orderRequestDTO.getOrderStatus().equals(OrderStatus.FINISH_ORDER)) {
            order.setHandDate(LocalDateTime.now());
        }


        ProcessOrder processOrder = new ProcessOrder();
        processOrder.setOrder(order);
        if (account != null) processOrder.setAccount(account);
        processOrder.setStatus(orderRequestDTO.getOrderStatus());
        processOrder.setCreated(LocalDateTime.now());
        processOrder = processOrderRepository.save(processOrder);

        order.getProcessOrders().add(processOrder);
        order = orderRepository.save(order);


        if (orderRequestDTO.getOrderStatus().equals(OrderStatus.CUSTOMER_RECEIVE) || orderRequestDTO.getOrderStatus().equals(OrderStatus.DESIGNER_SEND3D) || orderRequestDTO.getOrderStatus().equals(OrderStatus.FINISH_ORDER)) {
            emailDetail.setRecipient(order.getCustomer().getEmail());
        } else {
            emailDetail.setRecipient(account.getEmail());
        }

        emailDetail.setMsgBody("You have a job");
        emailDetail.setSubject("HappyGolden");
        emailDetail.setButton("have job");
        emailDetail.setLink("http://159.223.64.244/");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();

        return order;
    }
}