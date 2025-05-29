package telegram.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telegram.auth.model.TelegramUser;
import telegram.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TelegramUser> authenticate(@RequestParam("initData") String initData) {
        TelegramUser user = authService.authenticate(initData);
        return ResponseEntity.ok(user);
    }

}
