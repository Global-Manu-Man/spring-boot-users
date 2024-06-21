package spring_security_shield.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/*@Service
public class CaptchaService {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    private static final String TEST_CAPTCHA_RESPONSE = "test_captcha_response";

    public boolean verifyCaptcha(String captchaResponse) {
        // Devuelve verdadero si es el valor de prueba predefinido
        if (TEST_CAPTCHA_RESPONSE.equals(captchaResponse)) {
            return true;
        }

        // Código original para producción:
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecret);
        body.put("response", captchaResponse);

        Map<String, Object> response = restTemplate.postForObject(
                GOOGLE_RECAPTCHA_VERIFY_URL, body, Map.class);

        return (Boolean) response.get("success");
    }
}*/

@Service
public class CaptchaService {

    private static final String TEST_CAPTCHA_RESPONSE = "test_captcha_response";

    public boolean verifyCaptcha(String captchaResponse) {
        // Devuelve verdadero si es el valor de prueba predefinido
        return TEST_CAPTCHA_RESPONSE.equals(captchaResponse);
    }
}
