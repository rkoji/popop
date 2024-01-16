package com.example.popop.domain.post.dto;

import com.example.popop.domain.post.entity.PostStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {

    private String title;
    private String content;
//    private Long author;
    private String address;
//    private MultipartFile attachments;
    private PostStatus status;
    private boolean isPopup;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate popupStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate popupEndDate;

}
