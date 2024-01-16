package com.example.popop.domain.post.dto;

import com.example.popop.domain.post.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPostDto {

    private String title;
    private String content;
    private String address;
    private PostStatus status;
    private boolean isPopup;
    private LocalDate popupStartDate;
    private LocalDate popupEndDate;

    // isPopup이 null인지 확인
    public Boolean getIsPopup() {
        return isPopup;
    }

}
