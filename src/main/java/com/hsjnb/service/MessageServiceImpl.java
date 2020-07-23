package com.hsjnb.service;

import com.hsjnb.dao.MessageRepository;
import com.hsjnb.po.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
 * @date : Created in 2020/07/22 18:20
 * @description :
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageRepository messageRepository;

    @Transactional
    @Override
    public List<Message> listMessage() {
        Sort sort = Sort.by(Sort.Order.asc("createTime"));
        List<Message> messages = messageRepository.findAllByParentMessageNull(sort);
        return eachComment(messages);
    }

    @Transactional
    @Override
    public Message saveMessage(Message message) {
        Long parentMessageId = message.getParentMessage().getId();
        if (parentMessageId != -1) {
            message.setParentMessage(messageRepository.findById(parentMessageId).orElse(null));
        } else {
            message.setParentMessage(null);
        }
        message.setCreateTime(new Date());
        return messageRepository.save(message);
    }

    /**
     * 循环每个顶级的评论节点
     */
    private List<Message> eachComment(List<Message> messages) {
        List<Message> commentsView = new ArrayList<>();
        for (Message message : messages) {
            Message c = new Message();
            BeanUtils.copyProperties(message,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param messages root根节点，blog不为空的对象集合
     */
    private void combineChildren(List<Message> messages) {
        for (Message message : messages) {
            List<Message> replys = message.getReplyMessages();
            for(Message reply : replys) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            message.setReplyMessages(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param message 被迭代的对象
     */
    private void recursively(Message message) {
        tempReplys.add(message);//顶节点添加到临时存放集合
        if (message.getReplyMessages().size()>0) {
            List<Message> replys = message.getReplyMessages();
            for (Message reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyMessages().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
