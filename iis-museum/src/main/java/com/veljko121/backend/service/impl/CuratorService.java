package com.veljko121.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.repository.CuratorRepository;
import com.veljko121.backend.core.exception.EmailNotUniqueException;
import com.veljko121.backend.core.exception.UsernameNotUniqueException;
import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.service.ICuratorService;
import com.veljko121.backend.service.IUserService;

import java.util.List;

@Service
public class CuratorService extends CRUDService<Curator, Integer> implements ICuratorService{
    
    private final IUserService userService;

    private final CuratorRepository curatorRepository;

    @Autowired
    public CuratorService(CuratorRepository repository, CuratorRepository curatorRepository, IUserService userService) {
        super(repository);
        this.curatorRepository = curatorRepository;
        this.userService = userService;
    }

    @Override
    public Curator save(Curator curator) {
        if (existsByUsername(curator.getUsername())) throw new UsernameNotUniqueException(curator.getUsername());
        if (existsByEmail(curator.getEmail())) throw new EmailNotUniqueException(curator.getEmail());

        return curatorRepository.save(curator);
    }

    private Boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }
    
    private Boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    @Override
    public Curator update(Curator updated) {
        if (!userService.canUsernameBeChanged(updated)) throw new UsernameNotUniqueException(updated.getUsername());
        if (!userService.canEmailBeChanged(updated)) throw new EmailNotUniqueException(updated.getEmail());

        var oldGuest = findById(updated.getId());
        updated.setPassword(oldGuest.getPassword());
        updated.setRole(oldGuest.getRole());

        return curatorRepository.save(updated);
    }

    @Override
    public Curator findByUsername(String username) {
        return curatorRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public List<Curator> findAll(){
        return curatorRepository.findAll();
    }
}
