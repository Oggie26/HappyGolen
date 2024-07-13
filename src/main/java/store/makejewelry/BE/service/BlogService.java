package store.makejewelry.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.makejewelry.BE.entity.Blog;
import store.makejewelry.BE.model.BlogRequest;
import store.makejewelry.BE.repository.BlogRepository;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public Blog addBlog(BlogRequest blogRequest){
        Blog blog = new Blog();
        blog.setStatus(true);
        blog.setBlogName(blogRequest.getBlogName());
        blog.setContent(blogRequest.getContent());
        blog.setDescription(blogRequest.getDescription());
        blog.setImage(blogRequest.getImage());
        blogRepository.save(blog);
        return blog;
    }

    public Blog update(BlogRequest blogRequest, long id){
        Blog blog = blogRepository.findBlogById(id);
        if(blog != null){
            blog.setImage(blogRequest.getImage());
            blog.setBlogName(blogRequest.getBlogName());
            blog.setDescription(blogRequest.getDescription());
            blog.setContent(blogRequest.getContent());

            blogRepository.save(blog);
        }
        return blog;
    }

    public Blog disableBlog(long id) {
        Blog blog = blogRepository.findBlogById(id);
        if (blog != null) {
            blog.setStatus(!blog.getStatus());
            return blogRepository.save(blog);
        }
        return null;
    }

    public List<Blog> searchByIdOrNameQuery(String param) {
        return blogRepository.findByIdOrNameQuery(param);
    }

}
