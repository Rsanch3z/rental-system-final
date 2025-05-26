package com.rental.controller;

import com.rental.model.Forum;
import com.rental.model.Tenant;
import com.rental.model.ForumPhoto;
import com.rental.repository.ForumRepository;
import com.rental.repository.TenantRepository;
import com.rental.repository.ForumPhotoRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/forums")
public class ForumController {

    private final ForumRepository forumRepo;
    private final TenantRepository tenantRepo;
    private final ForumPhotoRepository photoRepo;

    public ForumController(ForumRepository forumRepo, TenantRepository tenantRepo, ForumPhotoRepository photoRepo) {
        this.forumRepo = forumRepo;
        this.tenantRepo = tenantRepo;
        this.photoRepo = photoRepo;
    }

    @GetMapping
    public List<Forum> getAllForums() {
        return forumRepo.findAll();
    }

    @GetMapping("/{postId}")
    public Forum getForumById(@PathVariable Integer postId) {
        return forumRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("貼文不存在"));
    }

    @PostMapping
    public Forum createForum(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam Integer tenantId) {

        Tenant tenant = tenantRepo.findById(Long.valueOf(tenantId))
                .orElseThrow(() -> new RuntimeException("找不到租客"));

        Forum forum = new Forum();
        forum.setTitle(title);
        forum.setContent(content);
        forum.setTenant(tenant);
        forum.setPostDate(LocalDateTime.now());

        return forumRepo.save(forum);
    }

    @PutMapping("/{postId}")
    public Forum updateForum(@PathVariable Integer postId,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String content) {

        Forum forum = forumRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("貼文不存在"));

        if (title != null && !title.isBlank()) {
            forum.setTitle(title);
        }
        if (content != null && !content.isBlank()) {
            forum.setContent(content);
        }

        return forumRepo.save(forum);
    }

    @DeleteMapping("/{postId}")
    public void deleteForum(@PathVariable Integer postId) {
        if (!forumRepo.existsById(postId)) {
            throw new RuntimeException("貼文不存在");
        }
        forumRepo.deleteById(postId);
    }

    @PostMapping("/{postId}/photo")
    public ForumPhoto uploadPhoto(@PathVariable Integer postId,
                                  @RequestParam("file") MultipartFile file) throws IOException {

        Forum forum = forumRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("貼文不存在"));

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + filename);
        file.transferTo(dest);

        ForumPhoto photo = new ForumPhoto();
        photo.setForum(forum);
        photo.setPhotoUrl("/uploads/" + filename);

        return photoRepo.save(photo);
    }
}
