package store.makejewelry.BE.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.makejewelry.BE.entity.Blog;
import store.makejewelry.BE.model.BlogRequest;
import store.makejewelry.BE.repository.BlogRepository;
import store.makejewelry.BE.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("api/blog")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class BlogAPI {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    @GetMapping("")
    public ResponseEntity<List<Blog>> findAll(){
        List<Blog> list =  blogRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity addBlog (@RequestBody BlogRequest blogRequest){
        Blog blog = blogService.addBlog(blogRequest);
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBlog (@RequestBody BlogRequest blogRequest, @PathVariable long id){
        Blog blog = blogService.update(blogRequest,id);
        return ResponseEntity.ok(blog);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity disableBlog(@PathVariable  long id){
        Blog blog = blogService.disableBlog(id);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlog(@RequestParam("param") String param) {
        List<Blog> list = blogRepository.findByIdOrNameQuery(param);
        return ResponseEntity.ok(list);
    }
}
