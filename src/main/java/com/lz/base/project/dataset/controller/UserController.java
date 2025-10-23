package com.lz.base.project.dataset.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lz.base.project.dataset.entity.UserEntity;
import com.lz.base.project.dataset.service.UserService;

@RestController
@RequestMapping("/api/dataset/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable Long id) {
        Optional<UserEntity> u = userService.find(id);
        return u.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity e) {
        UserEntity saved = userService.create(e);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable Long id, @RequestBody UserEntity e) {
        UserEntity updated = userService.update(id, e);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    public ResponseEntity<String> importFromCsv() {
        int imported = userService.importFromCsv();
        return ResponseEntity.ok("Imported " + imported + " users");
    }
}
