package com.example.mojal2ndproject2.category.model;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<SharePost> sharePosts = new ArrayList<>();

    @OneToMany(mappedBy = "giveCategory")
    private List<ExchangePost> giveExchangePosts = new ArrayList<>();
    //idx   name
    // 1      a
    // 2      b

    //추가
    @OneToMany(mappedBy = "takeCategory")
    private List<ExchangePost> takeExchangePosts = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<UserHaveCategory> userHaveCategories = new ArrayList<>();
}
