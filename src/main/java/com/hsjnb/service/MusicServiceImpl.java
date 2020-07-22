package com.hsjnb.service;

import com.hsjnb.NotFoundException;
import com.hsjnb.dao.MusicRepository;
import com.hsjnb.po.Music;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
 * @date : Created in 2020/07/22 17:14
 * @description :
 */

@Service
public class MusicServiceImpl implements MusicService {

    @Resource
    private MusicRepository musicRepository;

    @Transactional
    @Override
    public List<Music> listMusic() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        return musicRepository.findAll(sort);
    }

    @Transactional
    @Override
    public Music saveMusic(Music music) {
        return musicRepository.save(music);
    }

    @Transactional
    @Override
    public Music getMusic(Long id) {
        return musicRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Music> listMusic(Pageable pageable) {
        return musicRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Music updateMusic(Long id, Music music) {
        Music m = musicRepository.findById(id).orElse(null);
        if (m == null) {
            throw new NotFoundException("不存在该音乐");
        }
        BeanUtils.copyProperties(music,m);
        return musicRepository.save(m);
    }

    @Transactional
    @Override
    public void deleteMusic(Long id) {
        musicRepository.deleteById(id);
    }
}
