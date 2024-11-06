package com.backend.clothingstore.servicesImpl;

import com.backend.clothingstore.DTO.UserProfileDTO;
import com.backend.clothingstore.email.EmailSender;
import com.backend.clothingstore.errorHeandler.UserNotFoundException;
import com.backend.clothingstore.model.ConfirmationToken;
import com.backend.clothingstore.model.modelEnum.Role;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.authenticate.registerConfirmation.services.ConfirmationTokenService;
import com.backend.clothingstore.repositories.UserRepository;
import com.backend.clothingstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    EmailSender emailSender;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return "Logged in successfully";
    }

    @Override
    public User authenticate(String email, String password,String role) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Get the User object
            // Check the provided password against the stored hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user; // Authentication successful
            }
        }

        return null; // Authentication failed
    }

    @Override
    public User updateUser(UserProfileDTO userDTO) {

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userDTO.getId()));

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setState(userDTO.getState());
        user.setZip(userDTO.getZip());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setAddressLine(userDTO.getAddressLine());
        user.setPhone(userDTO.getPhone());
        user.setIsVerified(true);

        // Salvăm modificările în baza de date
        return userRepository.save(user);
    }

    @Override
    public User updateAdminUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update only non-null fields

        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());

        //encode password
        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

//        Doar pentru roll cand testezi cu postman
        if (user.getRole() != null) existingUser.setRole(user.getRole());

        if(user.getState() != null) existingUser.setState(user.getState());
        if(user.getZip() != null) existingUser.setZip(user.getZip());
        if(user.getCountry() != null) existingUser.setCountry(user.getCountry());
        if(user.getCity() != null) existingUser.setCity(user.getCity());
        if(user.getAddressLine() != null) existingUser.setAddressLine(user.getAddressLine());
        if(user.getPhone() != null) existingUser.setPhone(user.getPhone());



        existingUser.setUpdatedAt(LocalDateTime.now());
        // Save and return the updated user
        return userRepository.save(existingUser);
    }


    @Override
    public User findByuserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already in use");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsVerified(false); // Inițial nu este verificat

        user.setRole(Role.USER); // by default e user

        User savedUser = userRepository.save(user);


        //Mail service here:

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7), null,
                user
        );
        savedUser.setVerificationToken(token);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link= "http://localhost:8080/user-confirmation?token="+token;

        emailSender.send(user.getEmail(), buildEmail(user.getUsername(),link));

        return savedUser;
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 7 days. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }



}
