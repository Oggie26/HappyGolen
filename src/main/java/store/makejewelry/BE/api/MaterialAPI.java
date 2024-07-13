package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.model.Email.Admin.MaterialRequest;
import store.makejewelry.BE.repository.MaterialRepository;
import store.makejewelry.BE.service.MaterialService;

import java.util.List;

@RestController
@RequestMapping("api/material")
@SecurityRequirement(name = "api")
@CrossOrigin("*")

public class MaterialAPI {

    @Autowired
    MaterialService materialService;

    @Autowired
    MaterialRepository materialRepository;

    @GetMapping ("")
    public ResponseEntity<List<Material>> findAll(){
        List<Material> list =  materialRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity addCategory (@RequestBody MaterialRequest materialRequest){
        Material material = materialService.addMaterial(materialRequest);
        return ResponseEntity.ok(material);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMaterial (@RequestBody MaterialRequest materialRequest, @PathVariable long id){
        Material material = materialService.updateMaterial(materialRequest , id);
        return ResponseEntity.ok(material);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity disableMaterial(@PathVariable  long id){
        Material material = materialService.disableMaterial(id);
        return ResponseEntity.ok(material);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Material>> searchMaterial(@RequestParam("param") String param) {
        List<Material> list = materialRepository.findByIdOrNameQuery(param);
        return ResponseEntity.ok(list);
    }
}
