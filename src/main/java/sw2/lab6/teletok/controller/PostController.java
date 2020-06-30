package sw2.lab6.teletok.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class PostController {

    @GetMapping(value = {"", "/"})
    public String listPost(){
        return "post/list";
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

    @GetMapping(value = {"/ws/post/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewPost() {

        HashMap<String, Object> responseMap = new HashMap<>();



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
}
