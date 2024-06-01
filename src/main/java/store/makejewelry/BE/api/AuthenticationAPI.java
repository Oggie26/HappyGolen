package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.model.Auth.AccountResponse;
import store.makejewelry.BE.model.Auth.LoginRequest;
import store.makejewelry.BE.model.Auth.RegisterRequest;
import store.makejewelry.BE.model.DisableMethodRequest;
import store.makejewelry.BE.model.Email.CodeRequest;
import store.makejewelry.BE.model.Email.EmailDetail;
import store.makejewelry.BE.model.LoginGoogleRequest;
import store.makejewelry.BE.service.AuthenticationService;
import store.makejewelry.BE.service.EmailService;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class AuthenticationAPI {
    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        // nhan request tu FE va add xuong db
        AccountResponse accountResponse = authenticationService.register(registerRequest);
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        // nhan request tu FE
        AccountResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("code")
    public ResponseEntity code(@RequestBody CodeRequest codeRequest){
        AccountResponse codeResponse = authenticationService.code(codeRequest);
        return ResponseEntity.ok(codeRequest);
    }


    @PostMapping("admin-only")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAdmin(){
        return ResponseEntity.ok("admin-only");
    }

    @GetMapping("send-mail")
    public void sendMail(){
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient("namphse173452@fpt.edu.vn");
        emailDetail.setSubject("test123");
        emailDetail.setMsgBody("aaa");
        emailService.sendMailTemplate(emailDetail, null);
    }

    @PostMapping("login-google")
    public ResponseEntity<AccountResponse> logingoogle(@RequestBody LoginGoogleRequest loginGoogleRequest) {
        return ResponseEntity.ok(authenticationService.loginGoogle(loginGoogleRequest));
    }
    @PatchMapping("disable-account")
    public ResponseEntity disableAccount(@RequestBody DisableMethodRequest disableMethodRequest) {
        // nhan request tu FE
        AccountResponse disableAccount = authenticationService.disableAccount(disableMethodRequest);
        return ResponseEntity.ok(disableAccount);
    }

    @PutMapping("update-account")
    public ResponseEntity updateAccount(@RequestBody AccountResponse accountResponse) {
        // nhan request tu FE
        AccountResponse updateProductTemplateResponse = authenticationService.updateAccount(accountResponse);
        return ResponseEntity.ok(updateProductTemplateResponse);
    }

    @PostMapping("list-account")
    public ResponseEntity viewAccount(@RequestBody AccountResponse accountResponse) {
        // nhan request tu FE
        AccountResponse viewAccountResponse = authenticationService.viewAccount();
        return ResponseEntity.ok(viewAccountResponse);
    }





}
