package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.err.exception.Exception403;
import shop.mtcoding.blog._core.err.exception.Exception404;

import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository;

    @Transactional
    public Board 글쓰기(BoardRequest.SaveDTO requestDTO, User sessionUser) {
      return  boardJPARepository.save(requestDTO.toEntity(sessionUser));
    }

    @Transactional
    public Board 글조회(int boardId, int sessionUserId) {
        //조회 및 예외처리
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        //권한 처리,
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }


        return board;
    }


    @Transactional
    public void 글삭제(int boardId, int sessionUserId) {
        Board board = boardJPARepository.findById(boardId).orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }// 트랜잭션은 런타임익셉션이 발동하면 롤백된다.
        boardJPARepository.deleteById(boardId);

    }

    public List<BoardResponse.MainDTO> 글목록조회() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boardList = boardJPARepository.findAll(sort);

        return boardList.stream().map(board -> new BoardResponse.MainDTO(board)).toList();
    }

    public BoardResponse.DetailDTO 글상세보기(int boardId, User sessionUser) {
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        return new BoardResponse.DetailDTO(board,sessionUser);
    }

}
