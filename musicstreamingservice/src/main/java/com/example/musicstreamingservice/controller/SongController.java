package com.example.musicstreamingservice.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.musicstreamingservice.dao.StatusRepository;
import com.example.musicstreamingservice.dao.UserRepository;
import com.example.musicstreamingservice.model.Songs;
import com.example.musicstreamingservice.model.Status;
import com.example.musicstreamingservice.service.SongService;
import com.example.musicstreamingservice.service.StatusService;

@RestController
@RequestMapping("/api/v1/song")
public class SongController {
    
    @Autowired
  SongService service;

  @Autowired
  StatusService statusservice;

  @Autowired
  UserRepository repo;

  @Autowired
  StatusRepository statusrepo;

  @PostMapping("/addSong")
  
//   public String addSong(@RequestBody Songs song) {

//     Status status = statusrepo.findById(1).get();
//     if(status.getStatusId()==1){
//         return  service.saveSong(song);
//     }

//     return "not an admin";
public ResponseEntity<String> createSong(@RequestBody String songData) {

    Songs song = setSong(songData);
    if(song.getStatusId()==null){
        return new ResponseEntity<>("status wrong- " , HttpStatus.CREATED);
    }
    int songId = service.saveSong(song);
    return new ResponseEntity<>("status saved- " + songId, HttpStatus.CREATED);

}

private Songs setSong(String songData) {
    //Create new status object
    // Status status = new Status();
    Songs song = new Songs();

    //statusData has data type as String but format is JSON, so we create object of json
    // JSONObject json = new JSONObject(songdata);
    // String statusName = json.getString("statusName");
    // String statusDescription = json.getString("statusDescription");

    // status.setStatusName(statusName);
    // status.setStatusDescription(statusDescription);

    // return status;
    JSONObject json = new JSONObject(songData);
    String songName = json.getString("songName");
    String singerName = json.getString("singerName");
    String songType = json.getString("songType");
    String duration = json.getString("duration");

    song.setSongName(songName);
    song.setSingerName(singerName);
    song.setSongType(songType);
    song.setDuration(duration);
    
    
    String statusId = json.getString("statusId");
    Status status = statusrepo.findById(Integer.parseInt(statusId)).get();
    
    if(status.getStatusId()==1){
        song.setStatusId(status);
    }
    
    return song;


   
    
  }

  @GetMapping("/GetSongs")
  public List<Songs> GetSongs(Songs song) {
    return service.getSong(song);
  }

  @GetMapping("/GetSong/songId/{songId}")
  public Songs GetSong(@PathVariable Integer songId) {
    return service.getSongById(songId);
  }

  @PutMapping("Update/song/songId/{songId}")
  public String updateSong(@PathVariable Integer songId, @RequestBody Songs newSong) {
    service.updateSong(songId, newSong);
    return "Song details Updated";
  }

  @DeleteMapping("Delete/Song/songId/{songId}")
  public String deleteSong(@PathVariable Integer songId) {
    service.deleteById(songId);
    return "Deleted the song with id =" + songId;
  }
}
