package com.hsjnb.service;

import com.hsjnb.NotFoundException;
import com.hsjnb.dao.FriendLinkRepository;
import com.hsjnb.po.FriendLink;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @date : Created in 2020/07/22 15:07
 * @description :
 */

@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Resource
    private FriendLinkRepository friendLinkRepository;

    @Transactional
    @Override
    public List<FriendLink> listFriendLink() {
        return friendLinkRepository.findAll();
    }

    @Transactional
    @Override
    public FriendLink saveFriendLink(FriendLink friendLink) {
        return friendLinkRepository.save(friendLink);
    }

    @Transactional
    @Override
    public FriendLink getFriendLink(Long id) {
        return friendLinkRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<FriendLink> listFriendLink(Pageable pageable) {
        return friendLinkRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public FriendLink updateFriendLink(Long id, FriendLink friendLink) {
        FriendLink f = friendLinkRepository.findById(id).orElse(null);
        if (f == null) {
            throw new NotFoundException("不存在该友链");
        }
        BeanUtils.copyProperties(friendLink,f);
        return friendLinkRepository.save(f);
    }

    @Transactional
    @Override
    public void deleteFriendLink(Long id) {
        friendLinkRepository.deleteById(id);
    }
}
