package telegram.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import telegram.auth.model.TelegramUser;
import telegram.auth.repository.TelegramUserRepository;
import telegram.auth.util.TelegramAuthValidator;

import java.time.Instant;
import java.util.Map;

@Service
public class AuthService {

    private final TelegramUserRepository telegramUserRepository;

    private final TelegramAuthValidator validator;

    @Autowired
    public AuthService(TelegramUserRepository telegramUserRepository,
                       TelegramAuthValidator validator) {
        this.telegramUserRepository = telegramUserRepository;
        this.validator = validator;
    }

    public TelegramUser authenticate(String initData) {

        if (!validator.validate(initData)) {
            throw new RuntimeException("Invalid Telegram Data");
        }

        Map<String, String> data = validator.parseInitData(initData);

        Long id = Long.valueOf(data.get("id"));
        String firstName = data.getOrDefault("first_name", "");
        String lastName = data.getOrDefault("last_name", "");
        String userName = data.getOrDefault("username", "");
        Instant authDate = Instant.ofEpochSecond(Long.parseLong(data.get("auth_date")));

        TelegramUser user = new TelegramUser(id, firstName, lastName, userName, authDate);

        return telegramUserRepository.save(user);
    }
}
