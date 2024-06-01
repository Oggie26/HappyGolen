package store.makejewelry.BE.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Account;
import store.makejewelry.BE.exception.AuthException;
import store.makejewelry.BE.model.Auth.AccountResponse;
import store.makejewelry.BE.model.Auth.LoginRequest;
import store.makejewelry.BE.model.Auth.RegisterRequest;
import store.makejewelry.BE.model.DisableMethodRequest;
import store.makejewelry.BE.model.Email.CodeRequest;
import store.makejewelry.BE.model.Email.EmailDetail;
import store.makejewelry.BE.model.LoginGoogleRequest;
import store.makejewelry.BE.repository.AccountRepository;
import java.net.URLEncoder;
import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    EmailService emailService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    public AccountResponse register(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setPhone(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setFullname(registerRequest.getFullname());
        account.setGender(registerRequest.getGender());
        account.setBirthday(registerRequest.getBirthday());
        account.setStatus(true);
        Account newAccount = accountRepository.save(account);

// Trả về đối tượng AccountResponse
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setPhone(newAccount.getPhone());
        accountResponse.setEmail(newAccount.getEmail());
        accountResponse.setBirthday(newAccount.getBirthday());
        accountResponse.setGender(newAccount.getGender());
        accountResponse.setFullname(newAccount.getFullname());
        accountResponse.setStatus(newAccount.getStatus());
// Khi thanh cong dang ki gui mail ve
       try{
           EmailDetail emailDetail = new EmailDetail();
           emailDetail.setRecipient(account.getEmail());
           emailDetail.setMsgBody("Welcome to join HappyGolden");
           emailDetail.setSubject("HappyGolden");
           emailDetail.setAttachment("");
           emailService.sendMailTemplate(emailDetail, account.getFullname());
       }catch (Exception e){
           System.out.println("Error to send mail to " + newAccount.getEmail());
       }

        return accountResponse;
    }

    public AccountResponse login(LoginRequest loginRequest) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhone(),
                        loginRequest.getPassword()

                ));
        Account account = accountRepository.findAccountByPhone(loginRequest.getPhone());
        AccountResponse accountResponse = new AccountResponse();
        if (account.getStatus()) {
            String token = tokenService.generateToken(account);
            accountResponse.setPhone(account.getPhone());
            accountResponse.setToken(token);
        } else {
            throw new AuthException("Account blocked!!!");
        }
        return accountResponse;
    }

    public AccountResponse code(CodeRequest codeRequest) {
        AccountResponse accountResponse = new AccountResponse();
        try {
            String Email = codeRequest.getEmail();
            Account account = accountRepository.findByEmail(Email);
            String token = tokenService.generateToken(account);
            String resetLink = "159.223.64.244/resetpassword?token=" + URLEncoder.encode(token, "UTF-8");

            try{
                EmailDetail emailDetail = new EmailDetail();
                emailDetail.setRecipient(account.getEmail());
                emailDetail.setMsgBody("Welcome to join HappyGolden");
                emailDetail.setSubject("HappyGolden");
                emailDetail.setAttachment("");
                emailService.sendMailTemplate(emailDetail, account.getFullname());
            }catch (Exception e){
                System.out.println("Error to send mail to " + account.getEmail());
            }

            String newPassword = codeRequest.getNewpassword();
            String checkPassword = codeRequest.getCheckPassword();
            if(newPassword.equalsIgnoreCase(checkPassword)){
                accountResponse = new AccountResponse();
                accountResponse.setPassword(newPassword);
                accountResponse.setToken(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountResponse;
    }

    public AccountResponse updateAccount(AccountResponse accountResponse) {
        Account account = accountRepository.findAccountById(accountResponse.getId());
        account.setStatus(false);
        Account newaccount = accountRepository.save(account);
        accountResponse = new AccountResponse();
        accountResponse.setStatus(account.getStatus());
        return accountResponse;
    }

    public AccountResponse  viewAccount() {
        ArrayList<Account> list = (ArrayList<Account>) accountRepository.findAll();
        AccountResponse accountResponse = new AccountResponse();
        return accountResponse;
    }

    public AccountResponse disableAccount(DisableMethodRequest disableMethodRequest) {
        Account account = accountRepository.findAccountById(disableMethodRequest.getId());
        account.setStatus(false);
        Account newaccount = accountRepository.save(account);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setStatus(account.getStatus());
        return accountResponse;
    }

    public AccountResponse loginGoogle(LoginGoogleRequest loginGoogleRequest){
        AccountResponse accountResponse = new AccountResponse();
        try{
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = firebaseToken.getEmail();
            Account account = accountRepository.findByEmail(email);
            if(account == null){
                account.setEmail(firebaseToken.getEmail());
                account.setFullname(firebaseToken.getName());
                accountRepository.save(account);
            }else{
                if (account.getStatus()){
                    accountResponse.setId(account.getId());
                    accountResponse.setFullname(account.getFullname());
                    accountResponse.setEmail(account.getEmail());
                    String token = tokenService.generateToken(account);
                    accountResponse.setToken(token);
                }else{
                    throw new AuthException("Account blocked!!!");
                }
            }
        }catch (FirebaseAuthException e){
            e.printStackTrace();
        }
        return accountResponse;

    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return accountRepository.findAccountByPhone(phone);
    }
}
