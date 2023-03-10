package com.snews.server.services.email;

import jakarta.mail.internet.InternetAddress;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(String username,String email, String passwordResetUrl) {
        MimeMessagePreparator preparator = (mimeMessage) -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(new InternetAddress("noreply@snews", "noreply@snews"));
            helper.setTo(email);
            helper.setSubject("Забравена парола за SNews");
            helper.setText(getMessage(username,passwordResetUrl), true);
        };

        javaMailSender.send(preparator);
    }

    private String getMessage(String username, String passwordResetUrl) {
        return """
                <style>
                    *{
                        color:#505050
                    }
                    
                p{
                    margin: 0;
                    padding: 0;
                }
                                          
                .header {
                    padding-bottom: 1rem;
                }
                                          
                a {
                    text-decoration: none;
                    font-weight: 800;
                }
                                          
                .link {
                    padding: 1rem 0 1rem;
                }
                </style>
                                          
                <p class="header">Здравейте <i><b>%s</b></i>,</p><br>
                <p>Получавате това съобщение, понеже сте натиснали "забравена парола" на сайта на SNews.
                Ако не сте го направили Вие, можете да игнорите това съобщение.
                Натиснете <a href="%s">тук</a> за да въведете нова парола. Линкът е активен 15 минути.
                Ако имате проблем с отварянето на страницата, можете да копирате този адрес в браузъра си.</p><br>
                <p class="link">%s</p><br><br>
                <p>С уважение,</p>
                <p>Екипът на SNews</p>
                      """.formatted(username, passwordResetUrl, passwordResetUrl);
    }
}
