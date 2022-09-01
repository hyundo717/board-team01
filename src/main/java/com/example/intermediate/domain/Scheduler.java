package com.example.intermediate.domain;

import com.example.intermediate.repository.PostRepository;
import com.example.intermediate.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

//도현님 파일
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final PostRepository postRepository;
    private final PostService postService;

    @Transactional
    @Scheduled(cron = "00 00 1 * * *")    //초,분,시,일,월,요일 순서의 메서드 실행되게끔
    public void updatePostDelete() throws InterruptedException {
        System.out.println("게시글 업데이트");

        List<Post> postList = postRepository.findAll();

        boolean check = false;
        for (Post postOne : postList) {
            if (postOne.getComments().size() == 0) {
                System.out.println("게시글" + postOne.getTitle() + "이 삭제되었습니다.");
                postRepository.delete(postOne);
                check = true;
            }
        }
        if (!check) {
            System.out.println("삭제할 게시글이 없습니다.");
        }
    }
}