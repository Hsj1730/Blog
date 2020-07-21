package com.hsjnb.po;

import javax.persistence.*;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Joe
 * @version : 1.0
 * @date : Created in 2020/07/18 15:23
 * @description : Music实体类
 */

@Entity
@Table(name = "t_music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   //音乐标题

    private String author;  //作者

    private String musicUrl;    //音乐地址

    private String pictureUrl;  //图片地址

    private Boolean isLyrics;   //有无歌词

    private String lyrics;  //歌词

    public Music() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Boolean getLyrics() {
        return isLyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setLyrics(Boolean lyrics) {
        isLyrics = lyrics;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", isLyrics=" + isLyrics +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}
