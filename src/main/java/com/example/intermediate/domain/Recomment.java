package com.example.intermediate.domain;

import com.example.intermediate.controller.request.RecommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recomment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(nullable = false)
    private String content;

    @Column
    private int likesNum;

    public void update(RecommentRequestDto recommentRequestDto) {
        this.content = recommentRequestDto.getContent();
    }
    public void like(){
        this.likesNum += 1;
    }
    public void unlike(){
        this.likesNum -= 1;
    }
    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

}
