package shop.mtcoding.blog.board;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(BoardNativeRepository.class)
@DataJpaTest
public class BoardNativeRepositoryTest {
    @Autowired //DI
    private BoardNativeRepository boardNativeRepository;
    @Test
    public void findAll_test(){
        //given

        //when
        List<Board> boardList = boardNativeRepository.findAll();
        //then
        System.out.println("findAll_test/size : "+boardList.size());
        System.out.println("findAll_test/username : "+boardList.get(2).getUsername());

        //org.assertj.core.apo
        Assertions.assertThat(boardList.size()).isEqualTo(4);
        Assertions.assertThat(boardList.get(2).getUsername()).isEqualTo("ssar");

    }

    @Test
    public void findById(){
        //given
        int id = 1 ;

        //when
        Board board = boardNativeRepository.findById(id);

        //then
        System.out.println("findById :  "+board);

        assertThat(board.getTitle()).isEqualTo("제목1");
        assertThat(board.getContent()).isEqualTo("내용1");

    }

}