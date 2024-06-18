package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.*;
import store.makejewelry.BE.enums.OrderStatus;
import store.makejewelry.BE.exception.AuthException;
import store.makejewelry.BE.model.OrderRequest;
import store.makejewelry.BE.repository.AccountRepository;
import store.makejewelry.BE.repository.OrderRepository;
import store.makejewelry.BE.repository.ProductRepository;
import store.makejewelry.BE.repository.ProductTemplateRepository;

import java.util.ArrayList;
import java.util.Date;


@Service
public class OrderService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductTemplateRepository productTemplateRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    public ArrayList<Order> createOrder(OrderRequest orderRequest) {
        AuthenticationService service = new AuthenticationService();
        ArrayList<Order> list = new ArrayList<>();
        ProductService productService = null;
        OrderDetail orderDetail = new OrderDetail();
        Product product = new Product();
        try {
            if (service.getCurrentAccount() != null) {
                Order order = new Order();
                order.setCustomerName(service.getCurrentAccount().getFullName());
                order.setOrderDate(new Date());
                order.setStatus(OrderStatus.INIT);
                orderDetail.setProduct(product);
                list.add(order);
            } else {
                throw new AuthException("Login to Book");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//    public ArrayList<Order> getOrderBySeller(OrderRequest orderRequest, long id) {
//        AuthenticationService service = new AuthenticationService();
//        ArrayList<Order> list = (ArrayList<Order>) orderRepository.findAll();
//        try {
//            if (service.getCurrentAccount() != null) {
//                for (Order order : list) {
//                    if (order.getStatus() == 1) {
//                        order.setStaffName(service.getCurrentAccount().getFullName());
//                        order.setStatus(2);
//                        list.add(order);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public boolean acceptThat (long id){
//        ArrayList<Order> list = orderRepository.findOrderById(id);
//        AuthenticationService service = new AuthenticationService();
//        try {
//            if (service.getCurrentAccount() != null) {
//                Order order = orderRepository.getReferenceById(id);
//                order.setStatus(order.getStatus() + 1);
//                list.add(order);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public boolean rejectThat (long id){
//        ArrayList<Order> list = orderRepository.findOrderById(id);
//        AuthenticationService service = new AuthenticationService();
//        try {
//            if (service.getCurrentAccount() != null) {
//                Order order = orderRepository.getReferenceById(id);
//                order.setStatus(order.getStatus() - 1);
//                list.add(order);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//    public ArrayList<Order> checkAcceptManager(long id){
//        ArrayList<Order> list = orderRepository.findOrderById(id);
//        AuthenticationService service = new AuthenticationService();
//        try {
//            if (service.getCurrentAccount() != null){
//                Order order = orderRepository.getReferenceById(id);
//                if (acceptThat(order.getId())){
//                    order.setStatus(3);
//                }else{
//                    order.setStatus(1);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public ArrayList<Order> checkAcceptCustomer(long id){
//        ArrayList<Order> list = orderRepository.findOrderById(id);
//        AuthenticationService service = new AuthenticationService();
//        try {
//            if (service.getCurrentAccount() != null){
//                Order order = orderRepository.getReferenceById(id);
//                if (acceptThat(order.getId())){
//                    order.setStatus(4);
//                }else{
//                    order.setStatus(2);
//                }
//                list.add(order);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public boolean acceptCustomer(long id){
//        ArrayList<Order> list = orderRepository.findOrderById(id);
//        AuthenticationService service = new AuthenticationService();
//        try {
//            if (service.getCurrentAccount() != null) {
//                Order order = orderRepository.getReferenceById(id);
//                order.setStatus(order.getStatus() + 1);
//                list.add(order);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public boolean rejectCustomer(long id){
//        ArrayList<Order> list = orderRepository.findOrderById(id);
//        AuthenticationService service = new AuthenticationService();
//        try {
//            if (service.getCurrentAccount() != null) {
//                Order order = orderRepository.getReferenceById(id);
//                order.setStatus(order.getStatus() - 1);
//                list.add(order);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//

//
//    public ArrayList<Order> processingOrder (OrderRequest orderRequest, long id){
//        Order order = new Order();
//        ArrayList<Order> list = new ArrayList<>();
//        try {
//            switch (order.getStatus()){
//                // Customer book Order
//                case '0' :
//                    createOrder(orderRequest );
//                    break;
//                // Staff nhan tu OrderRequest tu Customer
//                case '1' :
//                    getOrderBySeller(orderRequest , id);
//                    break;
//                //Nhan vien gui bao gia cho Manager
//                case '2' :
//                    checkAcceptManager(id);
//                    break;
//                //Nhan vien gui bao gia cho Customer
//                case '3' :
//                    checkAcceptCustomer(id);
//                    break;
//                //Nhan vien thiet ke nhan duoc Order
//                case '4' :
//
//
//
//
//                default:
//                    throw new IllegalStateException("Unexpected value: " + order.getStatus());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
}