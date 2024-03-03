package com.TooMeet.Post.resposn;

import com.TooMeet.Post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Header header;
    private List<Body> body;
    private int allPage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private UUID postId;
        private String userName;
        private Long userId;
        private String content;
        private String imageUrl;
        private int privacy;
        private int emoji;
        private Timestamp createAt;
        private Timestamp updateAt;
    }

}
