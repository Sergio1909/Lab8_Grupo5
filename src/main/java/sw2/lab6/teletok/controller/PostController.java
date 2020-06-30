package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.repository.PostRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/ws/post/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarPosts(Post post, @RequestParam("query") String query) {

        HashMap<String,Post> responseMap = new HashMap<>();
        if(postRepository.buscadorPost(query)!=null){

            return new ResponseEntity(responseMap, HttpStatus.OK);
        }else{
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }

    }

    @GetMapping("/post/new")
    public String newPost(){
        return "post/new";
    }

    @PostMapping("/post/save")
    public String savePost()
    {


        return "redirect:/";
    }

    @GetMapping("/post/file/{media_url}")
    public String getFile() {
        return "";
    }

    @GetMapping("/post/{id}")
    public String viewPost() {
        return "post/view";
    }

    @PostMapping("/post/comment")
    public String postComment() {
        return "";
    }

    @PostMapping("/post/like")
    public String postLike() {
        return "";
    }

    @PostMapping (value = "/ws/post/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity likeAPost (@RequestParam("token") String code,
                                     @RequestParam("postId") int id) {

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

        return new ResponseEntity(responseMap, HttpStatus.CREATED);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionExcepcion(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un post");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }


}
