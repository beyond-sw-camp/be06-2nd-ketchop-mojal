package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostReadRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shareposts")
@RequiredArgsConstructor
public class SharePostController {
    private final SharePostService sharePostService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<SharePostCreateRes> create(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                     @RequestBody SharePostCreateReq request){
        Long requestIdx = customUserDetails.getMember().getIdx();
        SharePostCreateRes result = sharePostService.create(requestIdx, request);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<SharePostReadRes> read(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 Long idx){
        Long requestIdx = customUserDetails.getMember().getIdx();
        SharePostReadRes result = sharePostService.read(requestIdx, idx);
        return ResponseEntity.ok(result);
    }

}
