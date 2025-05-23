package com.marks.redemo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/19 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Controller
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          HttpSession session) {
        session.setAttribute("username", username);
        redisTemplate.opsForValue().set(
                "user:"+username,
                session.getId(),
                60, TimeUnit.SECONDS
        );
        return "redirect:/info";
    }

    @GetMapping("/info")
    public String info(HttpSession session, Model model) {
        String username = (String)session.getAttribute("username");
        redisTemplate.expire(
                "user:"+username,
                60, TimeUnit.SECONDS
        );
        model.addAttribute("sessionId", session.getId());
        return "info";
    }
}
