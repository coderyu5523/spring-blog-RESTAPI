package shop.mtcoding.blog.reply;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.User;

import java.time.LocalDateTime;

@NoArgsConstructor  // 빈생성자가 필요
@Entity
@Data
@Table(name = "reply_tb")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    private String comment;
    //user_id 로 테이블 만들어짐.
    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board ;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Transient
    private boolean isReplyOwner;

    @Builder
    public Reply(int id, String comment, User user, Board board, LocalDateTime createdAt) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.board = board;
        this.createdAt = createdAt;
    }

}
