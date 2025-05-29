package telegram.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telegram.auth.model.TelegramUser;
import telegram.auth.service.AuthService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user")
    public ResponseEntity<TelegramUser> getUser(@RequestParam String initData) {
        TelegramUser user = authService.authenticate(initData);
        return ResponseEntity.ok(user);
    }

}
