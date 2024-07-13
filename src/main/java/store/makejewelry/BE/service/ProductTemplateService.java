    package store.makejewelry.BE.service;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import store.makejewelry.BE.entity.*;
    import store.makejewelry.BE.model.Email.Admin.ProductTemplateRequest;
    import store.makejewelry.BE.model.Email.Admin.ProductTemplateResponse;
    import store.makejewelry.BE.model.DisableMethodRespone;
    import store.makejewelry.BE.model.ProductTemplateDetail;
    import store.makejewelry.BE.repository.CategoryRepository;
    import store.makejewelry.BE.repository.MaterialRepository;
    import store.makejewelry.BE.repository.ProductTemplateRepository;
    import store.makejewelry.BE.repository.StoneRepository;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    @Service
    public class ProductTemplateService {

        @Autowired
        ProductTemplateRepository productTemplateRepository;

        @Autowired
        MaterialRepository materialRepository;

        @Autowired
        CategoryRepository categoryRepository;

        @Autowired
        ProductService productService;

        @Autowired
        StoneRepository stoneRepository;

        public ProductTemplateResponse addProductTpl(ProductTemplateRequest productTemplateRequest) {
            ProductTemplateResponse productTemplateResponse = new ProductTemplateResponse();
            ProductTemplate pt = new ProductTemplate();
            //Xu li co ton tai ay chua
            if (productTemplateRepository.findProductTemplateByProductName(productTemplateRequest.getProductName()) == null) {
                pt.setProductName(productTemplateRequest.getProductName());
                pt.setImage(productTemplateRequest.getImage());
                pt.setDate(productTemplateRequest.getDate());
                pt.setSize(productTemplateRequest.getSize());
                pt.setThickness(productTemplateRequest.getThickness());
                pt.setStatus(true);
                pt.setDescription(productTemplateRequest.getContent());
                Material material = materialRepository.findMaterialById(productTemplateRequest.getMaterialId());
                pt.setMaterial(material);
                Category category = categoryRepository.findCategoryById(productTemplateRequest.getCategoryId());
                pt.setCategory(category);
                Float priceGolden = material.getPrice();
                pt.setPrice(productService.circlePrice(productTemplateRequest.getSize(), productTemplateRequest.getThickness(), priceGolden));
                pt.setWeight(productService.weight(productTemplateRequest.getSize(), productTemplateRequest.getThickness()));
                Stone stone = stoneRepository.findStoneById(productTemplateRequest.getStoneId());
                if(stone == null) {
                    pt.setStone(null);
                }else{
                    pt.setStone(stone);
                }
                pt = productTemplateRepository.save(pt);
                //luu vao db
                ProductTemplate newpt = productTemplateRepository.save(pt);
            //tra ve response
                productTemplateResponse.setProductName(productTemplateRequest.getProductName());
                productTemplateResponse.setImage(productTemplateRequest.getImage());
                productTemplateResponse.setDate(new Date());
                productTemplateResponse.setPrice(newpt.getPrice());
                productTemplateResponse.setSize(productTemplateRequest.getSize());
                productTemplateResponse.setWeight(pt.getWeight());
                productTemplateResponse.setStatus(true);
                productTemplateResponse.setContent(productTemplateRequest.getContent());
                productTemplateResponse.setThickness(productTemplateRequest.getThickness());
                productTemplateResponse.setCategory(pt.getCategory());
                productTemplateResponse.setMaterial(pt.getMaterial());
                productTemplateResponse.setStone(pt.getStone());
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
            if (productTemplateRepository.findProductTemplateById(id) != null) {
                pt.setProductName(productTemplateRequest.getProductName());
                pt.setImage(productTemplateRequest.getImage());
                pt.setDate(productTemplateRequest.getDate());
                pt.setSize(productTemplateRequest.getSize());
                pt.setStatus(productTemplateRequest.getStatus());
                pt.setStatus(productTemplateRequest.getStatus());
                pt.setDescription(productTemplateRequest.getContent());
                pt.setThickness(productTemplateRequest.getThickness());

                Material material = materialRepository.findMaterialById(productTemplateRequest.getMaterialId());
                pt.setMaterial(material);
                Category category = categoryRepository.findCategoryById(productTemplateRequest.getCategoryId());
                pt.setCategory(category);
                Float priceGolden = material.getPrice();
//                Float materialWeight = material.getWeight();
                pt.setPrice(productService.circlePrice(productTemplateRequest.getSize(), productTemplateRequest.getThickness(),priceGolden));
                pt.setWeight(productService.weight(productTemplateRequest.getSize(), productTemplateRequest.getThickness()));
                Stone stone = stoneRepository.findStoneById(productTemplateRequest.getStoneId());
                if(stone == null) {
                    pt.setStone(null);
                }else{
                    pt.setStone(stone);
                }
                pt = productTemplateRepository.save(pt);
                ProductTemplate newpt = productTemplateRepository.save(pt);
                //tra ve response
                productTemplateResponse.setId(pt.getId());
                productTemplateResponse.setProductName(productTemplateRequest.getProductName());
                productTemplateResponse.setImage(productTemplateRequest.getImage());
                productTemplateResponse.setDate(new Date());
                productTemplateResponse.setPrice(pt.getPrice());
                productTemplateResponse.setSize(productTemplateRequest.getSize());
                productTemplateResponse.setWeight(pt.getWeight());
                productTemplateResponse.setStatus(productTemplateRequest.getStatus());
                productTemplateResponse.setContent(productTemplateRequest.getContent());
                productTemplateResponse.setThickness(productTemplateRequest.getThickness());
                productTemplateResponse.setCategory(pt.getCategory());
                productTemplateResponse.setMaterial(pt.getMaterial());
                productTemplateResponse.setStone(pt.getStone());
            }
            return productTemplateResponse;
        }

        public ProductTemplate disableAccount(long id) {
            ProductTemplate product = productTemplateRepository.findProductTemplateById(id);
            if (product != null) {
                if (product.getStatus()){
                    product.setStatus(false);
                } else{
                    product.setStatus(true);
                }
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


        public ProductTemplateDetail productTemplateDetail(long id){
            ProductTemplateDetail productTemplateDetail = new ProductTemplateDetail();
            ProductTemplate productTemplate = productTemplateRepository.findProductTemplateById(id);
            productTemplateDetail.setProductTemplate(productTemplate);
            productTemplateDetail.setCategory(productTemplate.getCategory());
            productTemplateDetail.setMaterial(productTemplate.getMaterial());
            productTemplateDetail.setStone(productTemplate.getStone());
            return productTemplateDetail;
        }
    }