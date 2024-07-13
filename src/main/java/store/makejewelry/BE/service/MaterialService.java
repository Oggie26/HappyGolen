package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.model.Email.Admin.MaterialRequest;
import store.makejewelry.BE.repository.MaterialRepository;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;

    public List<Material> getAllMaterial (){
        List<Material> list = materialRepository.findAll();
        return list;
    }

    public List<Material> getMaterialById(long id){
        List<Material> list = List.of(materialRepository.findMaterialById(id));

        return list;
    }

    public Material addMaterial (MaterialRequest materialRequest){
        Material newmaterial = null;
            if(materialRepository.findMaterialById(materialRequest.getId()) == null
            || materialRepository.findMaterialByName(materialRequest.getName()) == null) {
                Material material = new Material();
                material.setId(materialRequest.getId());
                material.setStatus(true);
                material.setName(materialRequest.getName());
                material.setPrice(materialRequest.getPrice());
                newmaterial = materialRepository.save(material);
            }
        return newmaterial  ;
    }

    public Material updateMaterial (MaterialRequest materialRequest, long id){
        Material newMaterial = null;
        try {
            if(materialRepository.findMaterialById(id) != null){
                Material material = new Material();
                material.setId(id);
                material.setStatus(materialRequest.getStatus());
                material.setName(materialRequest.getName());
                material.setPrice(materialRequest.getPrice());
                newMaterial = materialRepository.save(material);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return newMaterial;
    }

    public Material disableMaterial (long id){
        Material material = materialRepository.findMaterialById(id);
        if (material.getStatus()){
            material.setStatus(false);
        }else{
            material.setStatus(true);
        }
        Material newMaterial1 = materialRepository.save(material);
        return newMaterial1;
    }
}
