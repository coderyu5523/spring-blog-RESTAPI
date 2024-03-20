package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog.user.User;



@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService ;
    private final HttpSession session;

    // todo : 글목록조회 api 필요    @GetMapping("/")
    //todo : 글 상세보기 api 필요    @GetMapping("/api/boards/{id}/detail")
    // todo : 글조회 api 필요       @GetMapping("/api/boards/{id}")


    @PostMapping("/api/boards")
    public String save(BoardRequest.SaveDTO requestDTO){
        //ORM 으로 INSERT 할 때, USER객체의 ID만 들어가있어도 된다.
        //즉 비영속 객체여도 된다. 하지만 없을 수도 있기 때문에 조회를 먼저 하는게 좋다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글쓰기(requestDTO,sessionUser);
        return "redirect:/";
    }


    @DeleteMapping("/api/boards/{id}")
    public String delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id,sessionUser.getId());

        return "redirect:/";
    }


    @PutMapping("/api/boards/{id}")
    public String update(@PathVariable Integer id,BoardRequest.UpdateDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글조회(id,sessionUser.getId(),requestDTO);
        return "redirect:/board/"+id ;
    }


}
