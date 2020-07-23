package com.hsjnb.web;

import com.hsjnb.po.Message;
import com.hsjnb.po.User;
import com.hsjnb.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
 * @date : Created in 2020/07/22 18:26
 * @description :
 */

@Controller
public class MessageController {

    @Resource
    private MessageService messageService;

    @Value("${message.avatar}")
    private String avatar;

    //显示留言页面
    @GetMapping("/message")
    public String message() {
        return "message";
    }

    @GetMapping("/messagecomment")
    public String messageComment(Model model){
        model.addAttribute("messages",messageService.listMessage());
        return "message::messageList";
    }

    @PostMapping("/message")
    public String post(Message message, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminComment(true);
        } else {
            message.setAvatar(avatar);
        }
        messageService.saveMessage(message);
        return "redirect:/messagecomment";
    }
}
