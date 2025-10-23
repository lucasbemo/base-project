package com.lz.base.project.dataset.service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lz.base.project.dataset.entity.UserEntity;
import com.lz.base.project.dataset.loader.UsersCsvLoader;
import com.lz.base.project.dataset.port.UserPort;

@Service
public class UserService {

    private final UserPort userPort;
    private final UsersCsvLoader usersCsvLoader;

    public UserService(UserPort userPort, UsersCsvLoader usersCsvLoader) {
        this.userPort = userPort;
        this.usersCsvLoader = usersCsvLoader;
    }

    public UserEntity create(UserEntity e) { return userPort.save(e); }
    public Optional<UserEntity> find(Long id) { return userPort.findById(id); }
    public List<UserEntity> list() { return userPort.findAll(); }
    public UserEntity update(Long id, UserEntity e) {
        e.setId(id);
        return userPort.save(e);
    }
    public void delete(Long id) { userPort.deleteById(id); }

    /**
     * Trigger an import of users from the default dataset/users_data.csv file.
     * Returns the number of records imported.
     */
    public int importFromCsv() {
        Path p = Path.of("dataset/users_data.csv");
        return usersCsvLoader.loadFromFile(p);
    }
}
