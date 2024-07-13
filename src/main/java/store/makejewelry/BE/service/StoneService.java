package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Stone;
import store.makejewelry.BE.model.StoneRequest;
import store.makejewelry.BE.repository.StoneRepository;

@Service
public class StoneService {
    @Autowired
    StoneRepository stoneRepository;

    public Stone addStone(StoneRequest stoneRequest) {
        try {
            Stone stone = stoneRepository.findStoneById(stoneRequest.getId());
            if (stone != null) {
                System.out.println("Stone already exists!");
                return null;
            }

            Stone newStone = new Stone();
            if (stoneRepository.findStoneByName(stoneRequest.getName()) == null) {
                newStone.setName(stoneRequest.getName());
                newStone.setPrice(stoneRequest.getPrice());
                newStone.setStatus(true);
            } else {
                System.out.println("Stone with the same name already exists!");
                return null;
            }

            return stoneRepository.save(newStone);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Stone updateStone(StoneRequest stoneRequest, long id) {
        try {
            Stone stone = stoneRepository.findStoneById(id);
            if (stone != null) {
                if (stoneRepository.findStoneByName(stoneRequest.getName()) == null) {
                    stone.setName(stoneRequest.getName());
                    stone.setStatus(stoneRequest.getStatus());
                    stone.setPrice(stoneRequest.getPrice());
                    return stoneRepository.save(stone);
                } else {
                    System.out.println("Stone name is already taken!");
                    return null;
                }
            } else {
                System.out.println("Stone does not exist");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Stone disableStone(long id){
        Stone stone = stoneRepository.findStoneById(id);
        if(stone.getStatus()){
            stone.setStatus(false);
        }else{
            stone.setStatus(true);
        }
        return stoneRepository.save(stone);
    }
}
