package com.example.yourssuassignment.domain.instagram.client

import com.example.yourssuassignment.dto.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "instagram", url = "https://graph.instagram.com", qualifiers = ["instagramClient"])
interface InstagramFeignClient {
    @GetMapping("/refresh_access_token?grant_type=ig_refresh_token&access_token={access_token}")
    fun getLongTermToken(
        @PathVariable("access_token")
        access_token: String,
    ): InstagramTokenDto

    @GetMapping("/me?fields=id,username&access_token={access_token}")
    fun getInstagramAccount(
        @PathVariable("access_token")
        access_token: String,
    ): InstagramAccountDto

    @GetMapping("/{userId}/media?fields=id%2Cusername%2Ctimestamp%2Cmedia_type%2Cmedia_url%2Cpermalink%2Ccaption&access_token={access_token}")
    fun listInstagramMedias(
        @PathVariable("userId")
        userId: String,
        @PathVariable("access_token")
        access_token: String,
    ): InstagramMediaDefaultDto

    @GetMapping("/{mediaId}/children?fields=id%2Cmedia_url&access_token={access_token}")
    fun listCarouselMedias(
        @PathVariable("mediaId")
        mediaId: String,
        @PathVariable("access_token")
        access_token: String,
    ): InstagramCarouselMediaDefaultDto
}
