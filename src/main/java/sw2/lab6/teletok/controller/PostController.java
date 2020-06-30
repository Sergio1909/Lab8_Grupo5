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

@RestController
@CrossOrigin
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PostCommentRepository postCommentRepository;


    @GetMapping(value = {"", "/"})
    public String listPost(){
        return "post/list";
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

    @GetMapping("/post/{id}")
    public String viewPost() {
        return "post/view";
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
}
