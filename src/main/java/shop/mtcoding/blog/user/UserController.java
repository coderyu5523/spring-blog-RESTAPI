package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.err.exception.Exception400;
import shop.mtcoding.blog._core.err.exception.Exception401;
import shop.mtcoding.blog._core.util.ApiUtil;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> userinfo(@PathVariable Integer id){
        User user = userService.회원조회(id);
        return ResponseEntity.ok(new ApiUtil(user));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO requestDTO){

       User user = userService.회원가입(requestDTO);
        return ResponseEntity.ok(new ApiUtil(user));  // 프론트에서 자동으로 세션을 가짐
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO requestDTO){

        User sessionUser =userService.로그인(requestDTO);
        session.setAttribute("sessionUser",sessionUser);

        return ResponseEntity.ok(new ApiUtil(null));
    }


    @PutMapping("/api/users/{id}") //회원 수정은 세션 id 를 받기 때문에 주소에 id가 필요없다. 하지만 프론트 입장에선 필요하다. 또 관리자가 수정을 해야한다면 구분이 필요하다.
    public ResponseEntity<?> update(@RequestBody @PathVariable Integer id, UserRequest.UpdateDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userService.회원수정(sessionUser.getId(),requestDTO);
        session.setAttribute("sessionUser",newSessionUser);

        return ResponseEntity.ok(new ApiUtil(newSessionUser));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil(null)) ;
    }
}
