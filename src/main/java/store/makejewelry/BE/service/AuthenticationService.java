package store.makejewelry.BE.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Account;
import store.makejewelry.BE.enums.RoleEnum;
import store.makejewelry.BE.exception.AuthException;
import store.makejewelry.BE.model.AccountRequest;
import store.makejewelry.BE.model.Email.Admin.AddAccountByAdminRequest;
import store.makejewelry.BE.model.Email.Admin.AddAccountByAdminResponse;
import store.makejewelry.BE.model.Auth.*;
import store.makejewelry.BE.model.Email.EmailDetail;
import store.makejewelry.BE.model.ForgotPassRequest;
import store.makejewelry.BE.model.LoginGoogleRequest;
import store.makejewelry.BE.repository.AccountRepository;
import java.text.ParseException;
import java.util.*;

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



    public AccountResponse register(RegisterRequest registerRequest) throws ParseException {
        Account newAccount = null;
        Account account = new Account();
        account.setPhone(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setFullName(registerRequest.getFullname());
        account.setGender(registerRequest.getGender());
        account.setBirthday(registerRequest.getBirthday());
        account.setAddress(registerRequest.getAddress());
        account.setRole(RoleEnum.CUSTOMER);
        account.setStatus(true);
        newAccount = accountRepository.save(account);
// Trả về đối tượng AccountResponse
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(newAccount.getId());
        accountResponse.setPhone(newAccount.getPhone());
        accountResponse.setEmail(newAccount.getEmail());
        accountResponse.setBirthday(newAccount.getBirthday());
        accountResponse.setGender(newAccount.getGender());
        accountResponse.setFullName(newAccount.getFullName());
        accountResponse.setStatus(newAccount.getStatus());
        accountResponse.setAddress(newAccount.getAddress());
        accountResponse.setRole(newAccount.getRole());
        // Khi thanh cong dang ki gui mail ve
        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(account.getEmail());
            emailDetail.setMsgBody("Welcome to join HappyGolden");
            emailDetail.setSubject("HappyGolden");
            emailDetail.setButton("Login To System");
            emailDetail.setLink("http://159.223.64.244/login");
            emailDetail.setFullName(newAccount.getFullName());
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    emailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        } catch (Exception e) {
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
        accountResponse.setId(account.getId());
        accountResponse.setFullName(account.getFullName());
        if (account.getStatus()) {
            String token = tokenService.generateToken(account);
            accountResponse.setPhone(account.getPhone());
            accountResponse.setRole(account.getRole());
            accountResponse.setToken(token);
        } else {
            throw new AuthException("Account blocked!!!");
        }
        return accountResponse;
    }

    public UpdateAccountResponse updateAccount(UpdateAccountRequest updateAccountRequest, long id) {
        UpdateAccountResponse updateAccountResponse = null;
        Account account = accountRepository.findAccountById(id);
        account.setFullName(updateAccountRequest.getFullName());
        account.setBirthday(updateAccountRequest.getBirthday());
        account.setAddress(updateAccountRequest.getAddress());
        account.setGender(updateAccountRequest.getGender());
        account.setPhone(updateAccountRequest.getPhone());
        account.setPassword(updateAccountRequest.getPassword());
        Account newaccount = accountRepository.save(account);
        updateAccountResponse = new UpdateAccountResponse();
        updateAccountResponse.setFullName(newaccount.getFullName());
        updateAccountResponse.setEmail(newaccount.getEmail());
        updateAccountResponse.setPassword(newaccount.getPassword());
        updateAccountResponse.setBirthday(newaccount.getBirthday());
        updateAccountResponse.setAddress(newaccount.getAddress());
        updateAccountResponse.setPhone(newaccount.getPhone());
        return updateAccountResponse;
    }

    public List<Account> viewAccount() {
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }

    public AccountResponse disableAccount(long id) {
        Account account = accountRepository.findAccountById(id);
        account.setStatus(!account.getStatus());
        Account newaccount = accountRepository.save(account);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setStatus(account.getStatus());
        return accountResponse;
    }

    public AccountResponse loginGoogle(LoginGoogleRequest loginGoogleRequest) {
        AccountResponse accountResponse = new AccountResponse();
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = firebaseToken.getEmail();
            Account account = accountRepository.findByEmail(email);
            if (account == null) {
                account = new Account();
                account.setEmail(firebaseToken.getEmail());
                account.setRole(RoleEnum.CUSTOMER);
                account.setFullName(firebaseToken.getName());
                account.setStatus(true);
                account = accountRepository.save(account);
            } else {
                if (!account.getStatus()) {
                    throw new AuthException("Account blocked!!!");
                }
            }
            accountResponse.setId(account.getId());
            accountResponse.setRole(RoleEnum.CUSTOMER);
            accountResponse.setFullName(account.getFullName());
            accountResponse.setEmail(account.getEmail());
            String token = tokenService.generateToken(account);
            accountResponse.setToken(token);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return accountResponse;
    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return accountRepository.findAccountByPhone(phone);
    }

    public void ForgotPassword(ForgotPassRequest forgotPassRequest) {
        Account account = accountRepository.findByEmail(forgotPassRequest.getEmail());
        if (account == null) {
            try {
                throw new AuthException("Account not found");
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(forgotPassRequest.getEmail());
        emailDetail.setMsgBody("");
        emailDetail.setSubject("Reset password for account" + forgotPassRequest.getEmail());
        emailDetail.setFullName(account.getFullName());
        emailDetail.setButton("Reset password");
        String token = tokenService.generateToken(account);
        emailDetail.setLink("http://159.223.64.244/change-password?token=" + token);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    public Account getCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findById(account.getId()).orElse(null);
    }

    public Account ResetPassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewpassword()));
       return accountRepository.save(account);
    }

    public AddAccountByAdminResponse addAccountByAdmin(AddAccountByAdminRequest x) {
        Account newAccount = null;
        Account account = new Account();
        account.setPhone(x.getPhone());
        account.setPassword(passwordEncoder.encode(x.getPassword()));
        account.setEmail(x.getEmail());
        account.setFullName(x.getFullname());
        account.setGender(x.getGender());
        account.setBirthday(x.getBirthday());
        account.setAddress(x.getAddress());
        account.setRole(x.getRoleEnum());
        account.setStatus(true);
        newAccount = accountRepository.save(account);
// Trả về đối tượng AccountResponse
        AddAccountByAdminResponse y = new AddAccountByAdminResponse();
        y.setPhone(newAccount.getPhone());
        y.setEmail(newAccount.getEmail());
        y.setBirthday(newAccount.getBirthday());
        y.setGender(newAccount.getGender());
        y.setFullName(newAccount.getFullName());
        y.setStatus(newAccount.getStatus());
        y.setAddress(newAccount.getAddress());
        y.setRoleEnum(newAccount.getRole());
// Khi thanh cong dang ki gui mail ve
        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(account.getEmail());
            emailDetail.setMsgBody("Welcome to join HappyGolden");
            emailDetail.setSubject("HappyGolden");
            emailDetail.setAttachment("");
            emailService.sendMailTemplate(emailDetail);
        } catch (Exception e) {
            System.out.println("Error to send mail to " + newAccount.getEmail());
        }

        return y;
    }

    public List<Account> listStaff() {
        List<Account> list = new ArrayList<>();
        List<Account> allAccounts = accountRepository.findAll();
        for (Account account : allAccounts) {
            if (account.getRole() == RoleEnum.SELLER  ||
                    account.getRole() == RoleEnum.DESIGNER || account.getRole() == RoleEnum.MAKER_PRODUCT) {
                list.add(account);
            }
        }
        return list;
    }

    public Account profile(){
        Account account = getCurrentAccount();
        return account;
    }

    public Account updateProfile(AccountRequest accountRequest) {
        //Show thong tin cu
        Account currentProfile = profile();

        Account newProfile = new Account();

        newProfile.setFullName(accountRequest.getAccount().getFullName());
        newProfile.setEmail(accountRequest.getAccount().getEmail());
        newProfile.setPhone(accountRequest.getAccount().getPhone());
        newProfile.setGender(accountRequest.getAccount().getGender());
        newProfile.setPassword(accountRequest.getAccount().getPassword());
        newProfile.setAddress(accountRequest.getAccount().getAddress());
        newProfile.setBirthday(accountRequest.getAccount().getBirthday());

        // Update thong tin
        currentProfile.setFullName(newProfile.getFullName());
        currentProfile.setEmail(newProfile.getEmail());
        currentProfile.setPhone(newProfile.getPhone());
        currentProfile.setAddress(newProfile.getAddress());
        currentProfile.setGender(newProfile.getGender());
        currentProfile.setBirthday(newProfile.getBirthday());
        currentProfile.setPassword(newProfile.getPassword());

        return currentProfile;
    }


}
