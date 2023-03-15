package com.example.musicstreamingservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.musicstreamingservice.model.Songs;

public interface SongRepository extends JpaRepository<Songs,Integer>{
    
}
