package store.makejewelry.BE.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeService {
    private static final String CHARACTERS = "0123456789";
    public String sendCode(String phone ){
        try {
            Random random = new Random();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 5;i++){
                int number = random.nextInt(CHARACTERS.length());
                char charcode =  CHARACTERS.charAt(number);
                stringBuilder.append(charcode);
            }
        String code = stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return phone;
    }
}
