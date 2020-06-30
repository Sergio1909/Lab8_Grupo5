package sw2.lab6.teletok.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.Token;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.PostRepository;
import sw2.lab6.teletok.repository.TokenRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import java.util.HashMap;

@RestController
@CrossOrigin
public class PostController {

    @Autowired
    PostRepository postRepository;


    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PostCommentRepository postCommentRepository;




    @GetMapping(value = "/ws/post/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarPosts(Post post, @RequestParam("query") String query) {

        HashMap<String, Post> responseMap = new HashMap<>();
        if (postRepository.buscadorPost(query) != null) {

            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }
    }



    @GetMapping("/post/new")
    public String newPost(){
        return "post/new";
    }

    @PostMapping(value = "/post/save" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePost(@RequestBody Post post, @RequestParam(value = "fetchId", required = false) boolean fetchId)
    {

       // @RequestParam(value = "fetchId", required = false) boolean fetchId) {A
        List<Token> codigosToken = new ArrayList<>();
        codigosToken = tokenRepository.findAll();

        String codeToken = tokenRepository.obtenerToken(post.getUser().getId());
        HashMap<String, Object> responseMap = new HashMap<>();
        if (codigosToken.contains(codeToken)) {
                if (!post.getMediaUrl().equals(null)){
                    if (fetchId) {
                        responseMap.put("id", post.getId());

                        postRepository.save(post);
                        responseMap.put("estado", "post subido");

                    }
                }else {

                    responseMap.put("estado", "error");
                    responseMap.put("msg", "EMPTY_FILE");
                    return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
                }

        }else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "TOKEN_INVALID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseMap, HttpStatus.CREATED);





    }

    @GetMapping("/post/file/{media_url}")
    public String getFile() {
        return "";
    }


    @PostMapping(value ="/post/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postComment(@RequestBody Post post) {
        List<Token> codigosToken = new ArrayList<>();
        codigosToken = tokenRepository.findAll();

        String codeToken = tokenRepository.obtenerToken(post.getUser().getId());
        HashMap<String, Object> responseMap = new HashMap<>();

        if (codigosToken.contains(codeToken)) {

            postRepository.save(post);
                    responseMap.put("id", postCommentRepository.ultimoidinsertado());


                    responseMap.put("estado", "post subido");

                } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "TOKEN_INVALID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }

    @PostMapping("/post/like")
    public String postLike() {
        return "";
    }

    @PostMapping (value = "/ws/post/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity likeAPost (@RequestParam("token") String code,
                                     @RequestParam("postId") int id) {
        HashMap<String, Object> responseMap = new HashMap<>();
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
