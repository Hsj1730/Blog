package com.hsjnb.service;

import com.hsjnb.NotFoundException;
import com.hsjnb.dao.PictureRepository;
import com.hsjnb.po.Picture;
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
 * @date : Created in 2020/07/22 16:14
 * @description :
 */

@Service
public class PictureServiceImpl implements PictureService{

    @Resource
    private PictureRepository pictureRepository;

    @Transactional
    @Override
    public List<Picture> listPicture() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        return pictureRepository.findAll(sort);
    }

    @Transactional
    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Transactional
    @Override
    public Picture getPicture(Long id) {
        return pictureRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Picture> listPicture(Pageable pageable) {
        return pictureRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Picture updatePicture(Long id, Picture picture) {
        Picture p = pictureRepository.findById(id).orElse(null);
        if (p == null) {
            throw new NotFoundException("不存在该照片");
        }
        BeanUtils.copyProperties(picture,p);
        return pictureRepository.save(p);
    }

    @Transactional
    @Override
    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }
}
