package com.backend.clothingstore.authenticate.passwordReset.services;

import com.backend.clothingstore.DTO.EmailDTO;
import com.backend.clothingstore.authenticate.passwordReset.repository.ResetPasswordRepository;
import com.backend.clothingstore.email.EmailSender;
import com.backend.clothingstore.model.ResetPasswordToken;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.authenticate.registerConfirmation.services.ConfirmationTokenService;
import com.backend.clothingstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class ResetPasswordService {


    @Autowired
    ResetPasswordRepository resetPasswordRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    UserService userService;
    @Autowired
    EmailSender emailSender;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public void saveToken(ResetPasswordToken resetPasswordToken) {
        resetPasswordRepository.save(resetPasswordToken);
    }
    public ResetPasswordToken findByToken(String token) {
        resetPasswordRepository.findByResetPasswordToken(token);
        return null;
    }
    public void deleteToken(int id) {
        resetPasswordRepository.deleteById(id);
    }

    public ResponseEntity<?> requestResetPassword(EmailDTO email) {

        User user = userService.findByuserEmail(email.getEmail());
        if(user==null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String code = generateResetPasswordCode();

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(
                code,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                null,
                user
        );
        saveToken(resetPasswordToken);

        emailSender.sendPassChange(user.getEmail(), buildEmailWithCode(user.getUsername(),code));
        return ResponseEntity.ok("Paaword token was send to your email");

    }
    public boolean resetPassword(String token, String newPassword) {
        Optional<ResetPasswordToken> optionalToken = resetPasswordRepository.findByResetPasswordToken(token);

        if (optionalToken.isEmpty()) {
            return false;
        }

        ResetPasswordToken resetPasswordToken = optionalToken.get();

        // Verifică dacă tokenul a expirat sau a fost deja folosit
        if (resetPasswordToken.isUsed() || resetPasswordToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        // Actualizează parola utilizatorului
        User user = resetPasswordToken.getUser();
        //encode password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());


        userService.save(user);


        resetPasswordToken.setUsed(true);
        resetPasswordToken.setConfirmationDate(LocalDateTime.now());
        saveToken(resetPasswordToken);

        return true;
    }



    private String buildEmailWithCode(String name, String code) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "            </td>\n" +
                "            <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "              <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset Your Password</span>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p>" +
                "        <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">We received a request to reset your password. Use the code below to complete the reset:</p>" +
                "        <p style=\"Margin:0 0 20px 0;font-size:24px;line-height:25px;color:#1D70B8;font-weight:bold;text-align:center\">" + code + "</p>" +
                "       <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#ff1500\">Code will expire in 5 minutes!</p>" +
                "        <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">If you did not request a password reset, please ignore this email.</p>" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "</div>";
    }


    public static String generateResetPasswordCode() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}
