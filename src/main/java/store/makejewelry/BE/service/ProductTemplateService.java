    package store.makejewelry.BE.service;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import store.makejewelry.BE.entity.Category;
    import store.makejewelry.BE.entity.Material;
    import store.makejewelry.BE.entity.ProductTemplate;
    import store.makejewelry.BE.model.Admin.ProductTemplateRequest;
    import store.makejewelry.BE.model.Admin.ProductTemplateResponse;
    import store.makejewelry.BE.model.DisableMethodRespone;
    import store.makejewelry.BE.repository.CategoryRepository;
    import store.makejewelry.BE.repository.MaterialRepository;
    import store.makejewelry.BE.repository.ProductTemplateRepository;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class ProductTemplateService {

        @Autowired
        ProductTemplateRepository productTemplateRepository;

        @Autowired
        MaterialRepository materialRepository;

        @Autowired
        CategoryRepository categoryRepository;

        public ProductTemplateResponse addProductTpl(ProductTemplateRequest productTemplateRequest) {
            ProductTemplateResponse productTemplateResponse = new ProductTemplateResponse();
            ProductTemplate pt = new ProductTemplate();
            //Xu li co ton tai hay chua
            if (productTemplateRepository.findProductTemplateByProductName(productTemplateRequest.getProductName()) == null
                    && productTemplateRepository.findProductTemplateById(productTemplateRequest.getId()) == null) {

                pt.setProductName(productTemplateRequest.getProductName());
                pt.setImage(productTemplateRequest.getImage());
//                pt.setDate(productTemplateRequest.getDate());
                pt.setPrice(productTemplateRequest.getPrice());
                pt.setSize(productTemplateRequest.getSize());
                pt.setQuantity(productTemplateRequest.getQuantity());
                pt.setWeight(productTemplateRequest.getWeight());
                pt.setStatus(productTemplateRequest.getStatus());
                pt.setDescription(productTemplateRequest.getContent());
                Material material = materialRepository.findMaterialById(productTemplateRequest.getMaterialId());
                pt.setMaterial(material);
                Category category = categoryRepository.findCategoryById(productTemplateRequest.getCategoryId());
                pt.setCategory(category);
            //luu vao db
                ProductTemplate newpt = productTemplateRepository.save(pt);
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
                productTemplateResponse.setCategoryId(newpt.getId());
                productTemplateResponse.setMaterialId(newpt.getId());
            }

            return productTemplateResponse;
        }

        public DisableMethodRespone disableProductTpl(long id) {
            DisableMethodRespone disableMethodRespone =  new DisableMethodRespone();
            //check co ton tai k
            if (productTemplateRepository.findProductTemplateById(id) != null) {
                ProductTemplate pt = new ProductTemplate();
                if(pt.getStatus()){
                    pt.setStatus(false);
                }else{
                    pt.setStatus(true);
                }
                productTemplateRepository.save(pt);
                disableMethodRespone.setStatus(pt.getStatus());
            }
            return disableMethodRespone;
        }

        public ProductTemplateResponse updateProductTpl(ProductTemplateRequest productTemplateRequest, long id) {
            ProductTemplateResponse productTemplateResponse = new ProductTemplateResponse();
            ProductTemplate pt = new ProductTemplate();
            //Xu li co ton tai hay chua
            if (productTemplateRepository.findProductTemplateById(productTemplateRequest.getId()) != null) {

                pt.setProductName(productTemplateRequest.getProductName());
                pt.setImage(productTemplateRequest.getImage());
//                pt.setDate(productTemplateRequest.getDate());
                pt.setPrice(productTemplateRequest.getPrice());
                pt.setSize(productTemplateRequest.getSize());
                pt.setQuantity(productTemplateRequest.getQuantity());
                pt.setWeight(productTemplateRequest.getWeight());
                pt.setStatus(productTemplateRequest.getStatus());
                pt.setDescription(productTemplateRequest.getContent());
                Material material = materialRepository.findMaterialById(productTemplateRequest.getMaterialId());
                pt.setMaterial(material);
                Category category = categoryRepository.findCategoryById(productTemplateRequest.getCategoryId());
                pt.setCategory(category);
                //luu vao db
                ProductTemplate newpt = productTemplateRepository.save(pt);
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
                productTemplateResponse.setCategoryId(newpt.getId());
                productTemplateResponse.setMaterialId(newpt.getId());            }

            return productTemplateResponse;
        }

        public ProductTemplate disableAccount(long id) {
            ProductTemplate product = productTemplateRepository.findProductTemplateById(id);

            if (product != null) {
                product.setStatus(!product.getStatus());
                ProductTemplate disabledProduct = productTemplateRepository.save(product);

                return disabledProduct;
            }

            return null;
        }

        public List<ProductTemplate> searchProductTemplate(String name, long id) {
            List<ProductTemplate> resultList = new ArrayList<>();
            List<ProductTemplate> list = productTemplateRepository.findAll();
            for (ProductTemplate product : list) {
                if (product.getProductName().equalsIgnoreCase(name) || product.getId() == id) {
                    resultList.add(product);
                }
            }
            return resultList;
        }
    }