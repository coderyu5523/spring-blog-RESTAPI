package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService ;
    private final HttpSession session;

    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
         Reply reply = replyService.댓글쓰기(requestDTO,sessionUser);

        return ResponseEntity.ok(new ApiUtil(reply)) ;
    }

    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@RequestBody @PathVariable("id") Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(id,sessionUser);

        return ResponseEntity.ok(new ApiUtil(null)) ;
    }

}
