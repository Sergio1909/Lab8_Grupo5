package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sw2.lab6.teletok.entity.Token;
import sw2.lab6.teletok.entity.User;
import sw2.lab6.teletok.repository.TokenRepository;
import sw2.lab6.teletok.repository.UserRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @PostMapping( value = {"/ws/user/signIn"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signIn(@RequestParam("username") String username,
                                 @RequestParam("password") String password){


        HashMap<String, Object> responseMap =new HashMap<>();

        User user1 = userRepository.findByUsername(username);
        Token tokenuserdb = tokenRepository.token(user1.getId());
        String passworddb = user1.getPassword();


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String contra = passwordEncoder.encode(password);
        boolean match = passwordEncoder.matches(passworddb, contra);

        if (match){
            responseMap.put("status", "AUTHENTICATED");
            responseMap.put("token", tokenuserdb.getCode());
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }else {
            responseMap.put("error", "AUTH_FAILED");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/user/signInRedirect")
    public String signInRedirect(Authentication auth, HttpSession session){
        User user = userRepository.findByUsername(auth.getName());
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/user/signUp")
    public String signUp(@ModelAttribute("user") User user){
        return "user/signUp";
    }

    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes attr) {
        if(bindingResult.hasErrors()){
            return "user/signUp";
        } else {
            try {
                attr.addFlashAttribute("msg","Usuario Registrado Exitosamente. Puede Iniciar Sesión.");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setEnable(1);
                user.setRoleId(2);
                userRepository.save(user);
                return "redirect:/";
            } catch (DataIntegrityViolationException ex) {
                bindingResult.rejectValue("username", "typeMismatch"); // pass an error message to the view
                return "user/signUp";
            }
        }
    }



}
