package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.Product;
import store.makejewelry.BE.model.Admin.ProductRequest;
import store.makejewelry.BE.model.Admin.ProductResponse;
import store.makejewelry.BE.model.DisableMethodRespone;
import store.makejewelry.BE.repository.CategoryRepository;
import store.makejewelry.BE.repository.MaterialRepository;
import store.makejewelry.BE.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository  categoryRepository;

    @Autowired
    MaterialRepository materialRepository;

    public ProductResponse addProduct (ProductRequest productRequest) {
        ProductResponse productResponse = new ProductResponse();
        Product pt = new Product();
        //Xu li co ton tai hay chua
        if (productRepository.findProductByProductName(productRequest.getProductName()) == null) {
            pt.setProductName(productRequest.getProductName());
            pt.setImage(productRequest.getImage());
            pt.setDate(((productRequest.getDate())));
            pt.setPrice(productRequest.getPrice());
            pt.setSize(productRequest.getSize());
            pt.setQuantity(productRequest.getQuantity());
            pt.setWeight(productRequest.getWeight());
            pt.setStatus(productRequest.getStatus());
            pt.setDescription(productRequest.getContent());
            Material material = materialRepository.findMaterialById(productRequest.getMaterialId());
            pt.setMaterial(material);
            Category category = categoryRepository.findCategoryById(productRequest.getCategoryId());
            pt.setCategory(category);

            //luu vao db
            Product newpt = productRepository.save(pt);
            //tra ve response
            productResponse.setId(newpt.getId());
            productResponse.setProductName(newpt.getProductName());
            productResponse.setImage(newpt.getImage());
            productResponse.setDate(newpt.getDate());
            productResponse.setPrice(newpt.getPrice());
            productResponse.setSize(newpt.getSize());
            productResponse.setQuantity(newpt.getQuantity());
            productResponse.setWeight(newpt.getWeight());
            productResponse.setStatus(newpt.getStatus());
            productResponse.setContent(newpt.getDescription());
            productResponse.setCategoryId(newpt.getId());
            productResponse.setMaterialID(newpt.getId());

        }
        return productResponse;
    }

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
        Product pt = new Product();
        //Xu li co ton tai hay chua
        if (productRepository.findProductById(id) != null) {
            pt.setProductName(productRequest.getProductName());
            pt.setImage(productRequest.getImage());
            pt.setDate(productRequest.getDate());
            pt.setPrice(productRequest.getPrice());
            pt.setSize(productRequest.getSize());
            pt.setQuantity(productRequest.getQuantity());
            pt.setWeight(productRequest.getWeight());
            pt.setStatus(productRequest.getStatus());
            pt.setDescription(productRequest.getContent());
            Material material = materialRepository.findMaterialById(productRequest.getMaterialId());
            pt.setMaterial(material);
            Category category = categoryRepository.findCategoryById(productRequest.getCategoryId());
            pt.setCategory(category);
            //luu vao db
            Product newpt = productRepository.save(pt);
            //tra ve response
            productResponse.setProductName(newpt.getProductName());
            productResponse.setImage(newpt.getImage());
            productResponse.setDate(newpt.getDate());
            productResponse.setPrice(newpt.getPrice());
            productResponse.setSize(newpt.getSize());
            productResponse.setQuantity(newpt.getQuantity());
            productResponse.setWeight(newpt.getWeight());
            productResponse.setStatus(newpt.getStatus());
            productResponse.setContent(newpt.getDescription());
            productResponse.setMaterialID(newpt.getId());
            productResponse.setCategoryId(newpt.getId());
        }
        return productResponse;
    }




}
