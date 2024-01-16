package com.example.popop.domain.post.entity;

import com.example.popop.domain.post.dto.ModifyPostDto;
import com.example.popop.domain.user.entity.User;
import com.example.popop.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String address;

    private String attachments;

    private int likesCount = 0;

    @Enumerated(STRING)
    private PostStatus status;

    private boolean isPopup;

    private LocalDate popupStartDate;

    private LocalDate popupEndDate;

    public Boolean getIsPopup() {
        return isPopup;
    }

    public void setIsPopup(Boolean isPopup) {
        this.isPopup = isPopup;
    }

    // 게시글 수정
    public void modifyPostForm(ModifyPostDto dto) {
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getContent() != null) {
            this.content = dto.getContent();
        }
        if (dto.getAddress() != null) {
            this.address = dto.getAddress();
        }
        if (dto.getStatus() != null) {
            this.status = dto.getStatus();
        }
        if (dto.getIsPopup() != null) {
            this.isPopup = dto.getIsPopup();
        }
        if (dto.getPopupStartDate() != null) {
            this.popupStartDate = dto.getPopupStartDate();
        }
        if (dto.getPopupEndDate() != null) {
            this.popupEndDate = dto.getPopupEndDate();
        }
    }

    // 사진 수정
    public void modifyAttachments(String attachments) {
        this.attachments = attachments;
    }
}
