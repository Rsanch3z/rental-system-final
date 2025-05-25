package com.rental.controller;

import com.rental.model.User;
import com.rental.service.LandlordService;
import com.rental.service.TenantService;
import com.rental.service.UserService;
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
    private TenantService tenantService;

    @Autowired
    private LandlordService landlordService;

   @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
        // 將 RegisterRequest 手動轉換成 User 實體
        User user = new User();

        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // 👈 原始密碼直接傳入
        user.setRole(request.getRole());
        user.setPhoneNumber(request.getPhoneNumber());

        User newUser = userService.register(user);
        System.out.println("✅ 註冊原始密碼: " + request.getPassword());
        System.out.println("✅ 註冊加密密碼: " + user.getPassword());

        // 根據角色寫入對應資料表
        if ("TENANT".equalsIgnoreCase(user.getRole())) {
            tenantService.createFromUser(user);
        } else if ("LANDLORD".equalsIgnoreCase(user.getRole())) {
            landlordService.createFromUser(user);
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
    response.setToken("dummy-token");
    response.setUser(user);
    return ResponseEntity.ok(response);
}

}
