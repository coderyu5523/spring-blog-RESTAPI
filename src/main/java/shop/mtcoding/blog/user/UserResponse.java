package shop.mtcoding.blog.user;


import lombok.Data;

public class UserResponse {
    // 엔티티를 dto로. 그래서 응답은 풀생성자가 필요함
    @Data
    public static class DTO{
        private int id ;
        private String username;
        private String email;

        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
