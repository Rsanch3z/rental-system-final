package com.rental.controller;

import com.rental.model.User;
import com.rental.model.Landlord;
import com.rental.model.Tenant;
import com.rental.service.UserService;
import com.rental.repository.LandlordRepository;
import com.rental.repository.TenantRepository;
import com.rental.dto.AuthResponse;
import com.rental.dto.LoginRequest;
import com.rental.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // 1. 建立 User 實體
            User user = new User();
            user.setUsername(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // ✅ 加密密碼
            user.setRole(request.getRole());
            user.setPhoneNumber(request.getPhoneNumber());

            User newUser = userService.register(user);

            System.out.println("✅ 註冊原始密碼: " + request.getPassword());
            System.out.println("✅ 註冊加密密碼: " + user.getPassword());

            // 2. 根據角色建立 Landlord 或 Tenant
            if ("LANDLORD".equalsIgnoreCase(user.getRole())) {
                Landlord landlord = new Landlord();
                landlord.setUser(newUser);
                landlord.setName(request.getName());
                landlord.setEmail(request.getEmail());
                landlord.setPhone(request.getPhoneNumber());
                landlord.setPassword(newUser.getPassword()); // ✅ 從 user 那裡抓密碼給 landlord
                landlord.setVerified(false); // 預設未驗證
                landlordRepository.save(landlord);
            } else if ("TENANT".equalsIgnoreCase(user.getRole())) {
                Tenant tenant = new Tenant();
                tenant.setUser(newUser);
                tenant.setName(request.getName());
                tenant.setEmail(request.getEmail());
                tenant.setPhone(request.getPhoneNumber());
                tenantRepository.save(tenant);
            }

            return ResponseEntity.ok(newUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findByEmail(request.getEmail()).orElse(null);

        // 印出 debug 資訊
        System.out.println("登入請求 email: " + request.getEmail());
        if (user != null) {
            System.out.println("登入請求 password: " + request.getPassword());
            System.out.println("資料庫密碼: " + user.getPassword());
            System.out.println("密碼比對結果: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));
        } else {
            System.out.println("找不到使用者");
        }

        // 驗證失敗回傳
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        // 登入成功
        AuthResponse response = new AuthResponse();
        response.setToken("dummy-token"); // 可替換為 JWT
        response.setUser(user);
        return ResponseEntity.ok(response);
    }
}
