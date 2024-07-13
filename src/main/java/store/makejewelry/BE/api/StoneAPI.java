package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Stone;
import store.makejewelry.BE.model.StoneRequest;
import store.makejewelry.BE.repository.StoneRepository;
import store.makejewelry.BE.service.StoneService;

import java.util.List;

@RestController
@RequestMapping("api/stone")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class StoneAPI {

    @Autowired
    StoneRepository stoneRepository;

    @Autowired
    StoneService stoneService;

    @GetMapping("")
    public ResponseEntity<List<Stone>> findAll(){
        List<Stone> list =  stoneRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity addStone (@RequestBody StoneRequest stoneRequest){
        Stone stone = stoneService.addStone(stoneRequest);
        return ResponseEntity.ok(stone);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStone (@RequestBody StoneRequest stoneRequest, @PathVariable long id){
        Stone stone = stoneService.updateStone(stoneRequest , id);
        return ResponseEntity.ok(stone);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity disableStone(@PathVariable  long id){
        Stone stone = stoneService.disableStone(id);
        return ResponseEntity.ok(stone);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Stone>> searchStone(@RequestParam("param") String param) {
        List<Stone> list = stoneRepository.findByIdOrNameQuery(param);
            return  ResponseEntity.ok(list);
    }
}
