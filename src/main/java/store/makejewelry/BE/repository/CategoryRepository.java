package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category findCategoryById(long id);
    Category findCategoryByName(String name);

    List<Category> findCategoriesByNameLike( String name);

    default List<Category> searchByNameAndId(String name, long id) {
        List<Category> results = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            List<Category> categoriesByName = findCategoriesByNameLike("%" + name + "%");
            results.addAll(categoriesByName);
        }
        if (id > 0) {
            Category categoryById = findCategoryById(id);
            if (categoryById != null) {
                results.add(categoryById);
            }
        }
        return results;
    }
}
