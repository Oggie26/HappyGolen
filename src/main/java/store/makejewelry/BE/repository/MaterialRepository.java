package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Material;

import java.util.ArrayList;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material ,Long> {
    Material findMaterialById(long id);
    Material findMaterialByName(String name);

    List<Material> findMaterialsByNameLike(String name);

     default List<Material> searchByNameAndId(String name, long id) {
        List<Material> results = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            List<Material> materialsByName = findMaterialsByNameLike("%" + name + "%");
            results.addAll(materialsByName);
        }
        if (id > 0) {
            Material materialById = findMaterialById(id);
            if (materialById != null) {
                results.add(materialById);
            }
        }
        return results;
    }
}
