package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.*;
import store.makejewelry.BE.enums.OrderStatus;
import store.makejewelry.BE.model.Email.Admin.ProductRequest;
import store.makejewelry.BE.model.Email.Admin.ProductResponse;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.repository.*;

@Service
public class ProductService {

    @Autowired
    StoneRepository stoneRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository  categoryRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    OrderRepository orderRepository;

    public DisableMethodRespone disableProduct(long id) {
        DisableMethodRespone disableMethodRespone =  new DisableMethodRespone();
            Product pt = productRepository.findProductById(id);
            if(pt.getStatus()){
                pt.setStatus(false);
            }else{
                pt.setStatus(true);
            }
            productRepository.save(pt);
            disableMethodRespone.setStatus(pt.getStatus());
        return disableMethodRespone;
    }

    public ProductResponse updateProduct(ProductRequest productRequest, long id) {
        ProductResponse productResponse = new ProductResponse();
        Order order = orderRepository.findOrderById(id);
        Product pt = productRepository.findProductByOrderId(id);
        //Xu li co ton tai hay chua
        if (pt != null) {
            pt.setProductName(productRequest.getProductName());
            pt.setImage(productRequest.getImage());
            pt.setSize(productRequest.getSize());
            pt.setStatus(true);
            pt.setDescription(productRequest.getContent());
            Material material = materialRepository.findMaterialById(productRequest.getMaterial().getId());
            pt.setMaterial(material);
            Category category = categoryRepository.findCategoryById(productRequest.getCategory().getId());
            pt.setCategory(category);
            Float priceGolden = material.getPrice();
            float makePrice = 1.1F ;
            pt.setWeight(weight(productRequest.getSize(), productRequest.getThickness()));
            pt.setPrice((circlePrice(productRequest.getSize(), productRequest.getThickness(), priceGolden)) * makePrice);

            Stone stone = stoneRepository.findStoneById(productRequest.getStone().getId());
            if (stone != null) {
                pt.setStone(stone);
                Float stonePrice = stone.getPrice();
                if (stonePrice == null) {
                    stonePrice = 0.0F;
                }
                pt.setPrice((pt.getPrice() + stonePrice) * makePrice);
            }
            //luu vao db
            productRepository.save(pt);
            //tra ve response
            productResponse.setId(pt.getId());
            productResponse.setProductName(pt.getProductName());
            productResponse.setImage(pt.getProductName());
            productResponse.setDate(pt.getDate());
            productResponse.setPrice(pt.getPrice());
            productResponse.setSize(pt.getSize());
            productResponse.setWeight(pt.getWeight());
            productResponse.setStatus(pt.getStatus());
            productResponse.setContent(pt.getDescription());
            productResponse.setMaterial(pt.getMaterial());
            productResponse.setCategory(pt.getCategory());
            productResponse.setStone(pt.getStone());
        }
        return productResponse;
    }

//    public ProductResponse designerWork(ProductRequest productRequest, long id) {
//        ProductResponse productResponse = new ProductResponse();
//        Order order = orderRepository.findOrderById(id);
//        Product pt = productRepository.findProductByOrderId(id);
//        //Xu li co ton tai hay chua
//        if (pt != null) {
//            order.setStatus(OrderStatus.DESIGNER_SEND3D);
//            pt.setProductName(productRequest.getProductName());
//            pt.setImage(productRequest.getImage());
//            pt.setSize(productRequest.getSize());
//            pt.setStatus(true);
//            pt.setDescription(productRequest.getContent());
//            Material material = materialRepository.findMaterialById(productRequest.getMaterial().getId());
//            pt.setMaterial(material);
//            Category category = categoryRepository.findCategoryById(productRequest.getCategory().getId());
//            pt.setCategory(category);
//            Float priceGolden = material.getPrice();
//            float makePrice = 1.1F ;
//            pt.setWeight(weight(productRequest.getSize(), productRequest.getThickness()));
//            pt.setPrice((circlePrice(productRequest.getSize(), productRequest.getThickness(), priceGolden)) * makePrice);
//
//            Stone stone = stoneRepository.findStoneById(productRequest.getStone().getId());
//            if (stone != null) {
//                pt.setStone(stone);
//                Float stonePrice = stone.getPrice();
//                if (stonePrice == null) {
//                    stonePrice = 0.0F;
//                }
//                pt.setPrice((pt.getPrice() + stonePrice) * makePrice);
//            }
//            //luu vao db
//            productRepository.save(pt);
//            orderRepository.save(order);
//            //tra ve response
//            productResponse.setId(pt.getId());
//            productResponse.setProductName(pt.getProductName());
//            productResponse.setImage(pt.getProductName());
//            productResponse.setDate(pt.getDate());
//            productResponse.setPrice(pt.getPrice());
//            productResponse.setSize(pt.getSize());
//            productResponse.setWeight(pt.getWeight());
//            productResponse.setStatus(pt.getStatus());
//            productResponse.setContent(pt.getDescription());
//            productResponse.setMaterial(pt.getMaterial());
//            productResponse.setCategory(pt.getCategory());
//            productResponse.setStone(pt.getStone());
//            productResponse.setOrder(order);
//        }
//        return productResponse;
//    }


    public float circlePrice(float size, float thickness, float priceGolden) {
        float weight = (float) (  ((((size + thickness) / 2) * 3.14 * thickness ) - ((size / 2 * 3.14)  * thickness )));
        float price = (weight * priceGolden) ;
        return price;
    }

    public float weight ( float size, float thickness) {
        float weight = (float) (  (((size + thickness) / 2) * 3.14 * thickness ) - ((size / 2 * 3.14)  * thickness ));
        return weight;
    }

}
