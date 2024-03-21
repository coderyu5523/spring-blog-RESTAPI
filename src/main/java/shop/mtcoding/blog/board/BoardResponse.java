package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {
    //게시글 목록보기 화면
    @Data
    public static class MainDTO{
        private int id;
        private String title;

        public MainDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }

    // 게시글 상세보기 화면
    @Data
    public static class DetailDTO{
        private int id;
        private String title;
        private String content ;
        private int userId ;
        private String usernaem;  // 게시글 작성자
        private boolean isOwner ;

        private List<ReplyDTO> replies = new ArrayList<>();     // new 를해서 초기화해야 값이 없을 때 0의 빈 배열을 리턴함. null 이면 터짐

        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.usernaem = board.getUser().getUsername();// 조인해서 가져옴
            this.isOwner = false;
            if(sessionUser!=null){
                if(sessionUser.getId()==userId){
                  isOwner =true ;
                }
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply,sessionUser)).toList();
        }
        @Data
        public class ReplyDTO{
            private int id ;
            private String content ;
            private int userId ;
            private String username ; // 댓글 작성자
            private boolean isOwner ;


            public ReplyDTO(Reply reply,User sessionUser) {
                this.id = reply.getId(); //lazy loading 발동
                this.content = reply.getComment();
                this.userId = reply.getUser().getId(); //lazy loading 발동
                this.username = reply.getUser().getUsername();

                this.isOwner = false;
                if(sessionUser!=null){
                    if(sessionUser.getId()==userId){
                        isOwner =true ;
                    }
                }
            }
        }


    }




}
