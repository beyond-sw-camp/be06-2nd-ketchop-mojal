package com.example.mojal2ndproject2.image;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangePostImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private ExchangePost exchangePost;
}
