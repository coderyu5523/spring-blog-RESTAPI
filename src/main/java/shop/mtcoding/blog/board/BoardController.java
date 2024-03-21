package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService ;
    private final HttpSession session;

    @GetMapping("/")
    public ResponseEntity<?> main(){
        List<BoardResponse.MainDTO> responseDTO = boardService.글목록조회();
        return  ResponseEntity.ok(new ApiUtil(responseDTO));
        //디스패쳐 서블릿은 오브젝트가 리턴되면, 메세지컨버터가 보드에 있는 애들을 게터로 제이슨으로 변환
        // GETuser를 하는 순간 없기 때문에 select 로 통신. 근데 화면에 그림을 그릴 때는 null 이기 때문에 터짐.
        // 메세지 컨버터가 기다린다면 괜찮음.
        // 그래서 메세지 컨버터가 레이지 로딩을 하면 안되고 서비스에서 레이지 로딩해서 dto 로 받아와야 됨
    }

    @GetMapping("/api/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable int id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO responseDTO = boardService.글상세보기(id,sessionUser);
        return  ResponseEntity.ok(new ApiUtil(responseDTO));
    }

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findOne(@PathVariable int id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글조회(id,sessionUser.getId());
        return  ResponseEntity.ok(new ApiUtil(board));
    }




        @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글쓰기(requestDTO,sessionUser);
        return ResponseEntity.ok(new ApiUtil(board)) ;
    }


    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?>  delete(@RequestBody @PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id,sessionUser.getId());

        return ResponseEntity.ok(new ApiUtil(null));
    }

//    @PutMapping("/api/boards/{id}")
//    public ResponseEntity<?> update(@RequestBody @PathVariable Integer id){
//        User sessionUser = (User) session.getAttribute("sessionUser");
//       Board board = boardService.글조회(id,sessionUser.getId());
//        return ResponseEntity.ok(new ApiUtil(board)) ;
//    }

}
