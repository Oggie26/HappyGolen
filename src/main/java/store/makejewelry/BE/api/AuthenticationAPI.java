package store.makejewelry.BE.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Account;
import store.makejewelry.BE.model.Admin.AddAccountByAdminRequest;
import store.makejewelry.BE.model.Admin.AddAccountByAdminResponse;
import store.makejewelry.BE.model.Auth.*;
import store.makejewelry.BE.model.ForgotPassRequest;
import store.makejewelry.BE.model.LoginGoogleRequest;
import store.makejewelry.BE.repository.AccountRepository;
import store.makejewelry.BE.service.AuthenticationService;
import store.makejewelry.BE.service.EmailService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
@CrossOrigin("*")

public class AuthenticationAPI {
    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("register")
    public ResponseEntity<AccountResponse> register(@RequestBody RegisterRequest registerRequest) throws ParseException {
        AccountResponse accountResponse = authenticationService.register(registerRequest);
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping("login")
    public ResponseEntity<AccountResponse> login(@RequestBody LoginRequest loginRequest) {
        AccountResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("forgot-password")
    public void forgotPassword(@RequestBody ForgotPassRequest forgotPassRequest) {
        authenticationService.ForgotPassword(forgotPassRequest);
    }

    @PostMapping("admin-only")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("admin-only");
    }

    @PostMapping("manager-only")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<String> getManager(){
        return ResponseEntity.ok("manager-only");
    }

    @PostMapping("seller-only")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> getSeller(){
        return ResponseEntity.ok("seller-only");
    }

    @PostMapping("design-only")
    @PreAuthorize("hasAuthority('DESIGN')")
    public ResponseEntity<String> getDesign(){
        return ResponseEntity.ok("design-only");
    }

    @PostMapping("maker-product-only")
    @PreAuthorize("hasAuthority('MAKER_PRODUCT')")
    public ResponseEntity<String>  getMakerProduct(){
        return ResponseEntity.ok("maker_product-only");
    }

    @PostMapping("customer-only")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> getCustomer(){
        return ResponseEntity.ok("customer-only");
    }


    @PostMapping("login-google")
    public ResponseEntity<AccountResponse> loginGoogle(@RequestBody LoginGoogleRequest loginGoogleRequest) {
        return ResponseEntity.ok(authenticationService.loginGoogle(loginGoogleRequest));
    }


    @PatchMapping("disable-account/{id}")
    public ResponseEntity disableAccount(@PathVariable long id) {
        AccountResponse disableAccount = authenticationService.disableAccount(id);
        return ResponseEntity.ok(disableAccount);
    }

    @PutMapping("update-account/{id}")
    public ResponseEntity updateAccount(@RequestBody UpdateAccountRequest updateAccountRequest, @PathVariable long id) {
        UpdateAccountResponse updateAccount = authenticationService.updateAccount(updateAccountRequest, id);
        return ResponseEntity.ok(updateAccount);
    }

    @GetMapping("list-account")
    public ResponseEntity<List<Account>> viewAccount() {
        List<Account> list = authenticationService.viewAccount();
        return ResponseEntity.ok(list);
    }

    @PostMapping("addAccountByAdmin")
    public ResponseEntity addAccountByAdmin(@RequestBody AddAccountByAdminRequest addAccountByAdminRequest){
        AddAccountByAdminResponse addAccountByAdminResponse = authenticationService.addAccountByAdmin(addAccountByAdminRequest);
        return ResponseEntity.ok(addAccountByAdminRequest);
    }

    @GetMapping("search-account")
    public List<Account> searchAccount(@RequestParam("phone") String phone, @RequestParam("id") long id) {
        return  accountRepository.searchByPhoneAndId(phone , id);
    }

    @PutMapping("reset-password")
    public  ResponseEntity resetPassWord (@RequestBody ResetPasswordRequest resetPasswordRequest){
        Account account = authenticationService.ResetPassword(resetPasswordRequest);
        return ResponseEntity.ok(account);
    }

}