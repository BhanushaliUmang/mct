package com.example.musicstreamingservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.musicstreamingservice.dao.SongRepository;
import com.example.musicstreamingservice.model.Songs;

@Service
public class SongService {
    
    @Autowired
    SongRepository songrepo;

    // public String saveSong(Songs song) {
        
    //     songrepo.save(song);
    //     return "saved";
        
    // }
    public int saveSong(Songs song) {
        Songs songObj = songrepo.save(song);
        return songObj.getSongId();
    }
    

    public List<Songs> getSong(Songs song) {
        return songrepo.findAll();
    }

    public Songs getSongById(Integer songId) {
        return songrepo.findById(songId).get();
    }

    public void updateSong(Integer songId, Songs newSong) {
        Songs newAdd = songrepo.findById(songId).get();

        newAdd.setSongName(newAdd.getSongName());
        newAdd.setSingerName(newAdd.getSingerName());
        newAdd.setSongType(newAdd.getSongType());
        newAdd.setDuration(newAdd.getDuration());

        songrepo.save(newAdd);
    }

    public void deleteById(Integer songId) {
        songrepo.deleteById(songId);
    }
}
