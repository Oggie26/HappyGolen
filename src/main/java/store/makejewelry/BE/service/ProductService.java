package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import store.makejewelry.BE.entity.Product;
import store.makejewelry.BE.entity.ProductTemplate;
import store.makejewelry.BE.model.Admin.ProductTemplateRequest;
import store.makejewelry.BE.model.Admin.ProductTemplateResponse;
import store.makejewelry.BE.model.DisableMethodRequest;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductTemplateResponse addProduct (ProductTemplateRequest productTemplateRequest) {
        ProductTemplateResponse productTemplateResponse = new ProductTemplateResponse();
        Product pt = new Product();
        //Xu li co ton tai hay chua
        if (productRepository.findProductByProductName(productTemplateRequest.getProductName()) == null
                && productRepository.findProductById(productTemplateRequest.getId()) == null) {

            pt.setProductName(productTemplateRequest.getProductName());
            pt.setImage(productTemplateRequest.getImage());
            pt.setDate(productTemplateRequest.getDate());
            pt.setPrice(productTemplateRequest.getPrice());
            pt.setSize(productTemplateRequest.getSize());
            pt.setQuantity(productTemplateRequest.getQuantity());
            pt.setWeight(productTemplateRequest.getWeight());
            pt.setStatus(productTemplateRequest.getStatus());
            pt.setDescription(productTemplateRequest.getContent());
            //luu vao db
            Product newpt = productRepository.save(pt);
            //tra ve response
            productTemplateResponse.setId(newpt.getId());
            productTemplateResponse.setProductName(newpt.getProductName());
            productTemplateResponse.setImage(newpt.getImage());
            productTemplateResponse.setDate(newpt.getDate());
            productTemplateResponse.setPrice(newpt.getPrice());
            productTemplateResponse.setSize(newpt.getSize());
            productTemplateResponse.setQuantity(newpt.getQuantity());
            productTemplateResponse.setWeight(newpt.getWeight());
            productTemplateResponse.setStatus(newpt.getStatus());
            productTemplateResponse.setContent(newpt.getDescription());
        }

        return productTemplateResponse;
    }

    public DisableMethodRespone disableProductTpl(@RequestBody DisableMethodRequest disableMethodRequest) {
        DisableMethodRespone disableMethodRespone =  new DisableMethodRespone();
        //check co ton tai k
        if (productRepository.findProductByProductName(disableMethodRequest.getProductName()) != null  ||
                productRepository.findProductById(disableMethodRequest.getId()) != null) {
            Product pt = new Product();
            pt.setStatus(false);
            //luu
            productRepository.save(pt);
            //tra ve response
            disableMethodRespone.setStatus(pt.getStatus());
        }
        return disableMethodRespone;
    }

    public ProductTemplateResponse updateProduct(ProductTemplateRequest productTemplateRequest) {
        ProductTemplateResponse productTemplateResponse = new ProductTemplateResponse();
        Product pt = new Product();
        //Xu li co ton tai hay chua
        if (productRepository.findProductById(productTemplateRequest.getId()) != null) {

            pt.setProductName(productTemplateRequest.getProductName());
            pt.setImage(productTemplateRequest.getImage());
            pt.setDate(productTemplateRequest.getDate());
            pt.setPrice(productTemplateRequest.getPrice());
            pt.setSize(productTemplateRequest.getSize());
            pt.setQuantity(productTemplateRequest.getQuantity());
            pt.setWeight(productTemplateRequest.getWeight());
            pt.setStatus(productTemplateRequest.getStatus());
            pt.setDescription(productTemplateRequest.getContent());
            //luu vao db
            Product newpt = productRepository.save(pt);
            //tra ve response

            productTemplateResponse.setProductName(newpt.getProductName());
            productTemplateResponse.setImage(newpt.getImage());
            productTemplateResponse.setDate(newpt.getDate());
            productTemplateResponse.setPrice(newpt.getPrice());
            productTemplateResponse.setSize(newpt.getSize());
            productTemplateResponse.setQuantity(newpt.getQuantity());
            productTemplateResponse.setWeight(newpt.getWeight());
            productTemplateResponse.setStatus(newpt.getStatus());
            productTemplateResponse.setContent(newpt.getDescription());
        }

        return productTemplateResponse;
    }

    public DisableMethodRespone disableAccount (@RequestBody DisableMethodRequest disableMethodRequest) {
        DisableMethodRespone deleteProductTpl =  new DisableMethodRespone();
        //check co ton tai k
        if (productRepository.findProductByProductName(disableMethodRequest.getProductName()) != null ){
            ProductTemplate pt = new ProductTemplate();
            productRepository.findProductByIdAndIsDeleteFalse(pt.getId());
        }
        return deleteProductTpl;
    }


}
