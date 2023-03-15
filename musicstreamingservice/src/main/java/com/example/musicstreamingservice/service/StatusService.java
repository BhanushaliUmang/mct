package com.example.musicstreamingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.musicstreamingservice.dao.StatusRepository;
import com.example.musicstreamingservice.model.Status;




@Service
public class StatusService {

    @Autowired
    StatusRepository repository;

    public int saveStatus(Status status) {
        Status statusObj = repository.save(status);
        return statusObj.getStatusId();
    }
    
}
