package com.hsjnb.web.admin;

import com.hsjnb.po.Music;
import com.hsjnb.service.MusicService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

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
 * @date : Created in 2020/07/22 17:24
 * @description :
 */

@Controller
@RequestMapping("/admin")
public class MusicController {

    @Resource
    private MusicService musicService;

    @GetMapping("/musics")
    public String musics(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model) {
        model.addAttribute("page",musicService.listMusic(pageable));
        return "admin/musics";
    }

    @GetMapping("/musics/input")
    public String input(Model model) {
        model.addAttribute("music", new Music());
        return "admin/musics-input";
    }

    @GetMapping("/musics/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("music", musicService.getMusic(id));
        return "admin/musics-input";
    }

    @PostMapping("/musics")
    public String post(@Valid Music music, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return "admin/musics-input";
        }
        Music m = musicService.saveMusic(music);
        if (m == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/musics";
    }

    @PostMapping("/musics/{id}")
    public String editPost(@Valid Music music, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        if(result.hasErrors()){
            return "admin/musics-input";
        }
        Music m = musicService.updateMusic(id,music);
        if (m == null ) {
            attributes.addFlashAttribute("message", "编辑失败");
        } else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/musics";
    }

    @GetMapping("/musics/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        musicService.deleteMusic(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/musics";
    }
}
