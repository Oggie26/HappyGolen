//package store.makejewelry.BE.api;
//
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("api/goldPrice")
//@SecurityRequirement(name = "api")
//@CrossOrigin("*")
//public class GoldPriceAPI {
//    @GetMapping("/gold-price")
//    public ResponseEntity<String> getGoldPrice() {
//        RestTemplate restTemplate = new RestTemplate();
//        String apiUrl = "https://www.talupa.com/gold/vietnam";
//
//        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            String responseBody = response.getBody();
//            return ResponseEntity.ok("Gold Price: " + responseBody);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
