package telegram.auth;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramAuthApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		String botToken = System.getenv("BOT_TOKEN");
		if (botToken == null) {
			botToken = dotenv.get("BOT_TOKEN");
		}

		if (botToken == null) {
			throw new RuntimeException("BOT_TOKEN is not set in environment variables or .env file");
		}

		SpringApplication.run(TelegramAuthApplication.class, args);
	}

}
