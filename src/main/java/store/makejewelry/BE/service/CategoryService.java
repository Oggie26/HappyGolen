package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.model.CategoryRequest;
import store.makejewelry.BE.repository.CategoryRepository;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category addCategory(CategoryRequest categoryRequest) {
        try {
            Category category = categoryRepository.findCategoryById(categoryRequest.getId());
            if (category != null) {
                System.out.println("Category already exists!");
                return null;
            }
            Category newCategory = new Category();
            newCategory.setId(categoryRequest.getId());
            if (categoryRepository.findCategoryByName(categoryRequest.getName()) == null) {
                newCategory.setName(categoryRequest.getName());
                newCategory.setStatus(true);
            } else {
                System.out.println("Category name is already taken!");
                return null;
            }

            return categoryRepository.save(newCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Category updateCategory(CategoryRequest categoryRequest, long id) {
        try {
            Category category = categoryRepository.findCategoryById(id);
            if (category != null) {
                if (categoryRepository.findCategoryByName(categoryRequest.getName()) == null) {
                    category.setName(categoryRequest.getName());
                    category.setStatus(categoryRequest.getStatus());
                    return categoryRepository.save(category);
                } else {
                    System.out.println("Category name is already taken!");
                    return null;
                }
            } else {
                System.out.println("Category does not exist");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Category disableCategory(long id){
        Category category = categoryRepository.findCategoryById(id);
        if(category.getStatus()){
            category.setStatus(false);
        }else{
            category.setStatus(true);
        }
        return categoryRepository.save(category);
    }


}
