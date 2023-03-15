package com.example.musicstreamingservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.musicstreamingservice.model.Status;

public interface StatusRepository extends JpaRepository<Status,Integer>{
    
}
