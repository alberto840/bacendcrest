package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    public void resetPasswordAndSendEmail(String email) {
        Optional<Users> userOptional = usersRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("No user found with this email");
        }

        Users user = userOptional.get();
        String newPassword = generateRandomPassword();
        String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Guardar nueva contraseña en la base de datos
        user.setPassword(encryptedPassword);
        usersRepository.save(user);

        // Enviar correo con la nueva contraseña
        sendEmail(user.getEmail(), newPassword);
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    private void sendEmail(String email, String newPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Your New Password - Pet Veterinaria");
            helper.setText("<p>Your new password is: <b>" + newPassword + "</b></p><p>Please change it after logging in.</p>", true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
