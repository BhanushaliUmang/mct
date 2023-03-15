package com.example.musicstreamingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_song")
public class Songs {
    
    @Id
    @Column(name = "song_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songId;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "singer_name")
    private String singerName;

    @Column(name = "song_type")
    private String songType;

    @Column(name = "duration")
    private String duration;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status statusId;
}
