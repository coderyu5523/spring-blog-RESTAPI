package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import shop.mtcoding.blog._core.err.exception.Exception400;
import shop.mtcoding.blog._core.err.exception.Exception401;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    //todo : 회원정보조회 API 필요     @GetMapping("/api/users/{id}")


    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO){

       User user = userService.회원가입(requestDTO);
        session.setAttribute("sessionUser",user);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO){

        User sessionUser =userService.로그인(requestDTO);
        session.setAttribute("sessionUser",sessionUser);

       return "redirect:/";
    }


    @PutMapping("/api/users/{id}")
    public String update(UserRequest.UpdateDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원조회(sessionUser.getId(),requestDTO);
        session.setAttribute("sessionUser",user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
