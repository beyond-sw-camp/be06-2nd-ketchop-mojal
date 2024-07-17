package com.example.mojal2ndproject2.category;

import jakarta.annotation.PostConstruct;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInit {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void dataInsert() {

        Set<String> categories = Set.of(
                "언어와 문화", "스포츠와 레크리에이션", "음악과 예술", "학문과 교육", "프로그래밍과 IT",
                "건강과 웰빙", "요리와 제과", "공예와 DIY", "비즈니스와 경영", "재정 및 투자",
                "사진과 비디오", "문학과 글쓰기", "여행과 탐험", "사회적 기술과 커뮤니케이션",
                "디자인과 크리에이티브", "자연과 과학", "정치와 사회", "자동차와 기술", "게임과 취미",
                "애완동물과 동물 관리", "나눔"
        );

        for (String c : categories) {
            Category category = Category.builder().name(c).build();
            categoryRepository.save(category);
        }
    }

}
