package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.model.CategoryRequest;
import store.makejewelry.BE.repository.CategoryRepository;
import store.makejewelry.BE.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/category")
@SecurityRequirement(name = "api")
@CrossOrigin("*")

public class CategoryAPI {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository  categoryRepository;

    @GetMapping ("")
    public ResponseEntity <List<Category>> findAll(){
        List<Category> list =  categoryRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity addCategory (@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.addCategory(categoryRequest);
            return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable long id){
        Category category = categoryService.updateCategory(categoryRequest , id);
            return ResponseEntity.ok(category);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity disableCategory(@PathVariable  long id){
        Category category = categoryService.disableCategory(id);
            return ResponseEntity.ok(category);
    }

    @GetMapping("/search")
    public List<Category> searchCategory(@RequestParam("name") String name, @RequestParam("id") long id) {
        return categoryRepository.searchByNameAndId(name , id);
    }
}
