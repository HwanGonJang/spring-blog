package com.example.yourssuassignment.domain.instagram.facade

import com.example.yourssuassignment.domain.instagram.client.InstagramFeignClient
import com.example.yourssuassignment.dto.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class InstagramFacade(
    private val instagramFeignClient: InstagramFeignClient,
) {
    // 2023-02-17T05:31:39+0000
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+SSSS")

    fun getInstagramAccessToken(
        access_token: String
    ): InstagramTokenDto = instagramFeignClient.getLongTermToken(
        access_token = access_token
    )

    // 장기 토큰 발급
    fun getInstagramAccount(
        access_token: String,
    ): InstagramAccountDto = instagramFeignClient.getInstagramAccount(
        access_token = access_token
    )

    fun listInstagramMedias(
        userId: String,
        access_token: String,
    ): List<InstagramMediaResultDto> {
        val medias = instagramFeignClient.listInstagramMedias(
            userId = userId,
            access_token = access_token
        ).data

        return medias.map {
            if (it.media_type == "CAROUSEL_ALBUM") {
                val carouselMediaUrls = instagramFeignClient.listCarouselMedias(
                    mediaId = it.id,
                    access_token = access_token
                ).data.map { media -> media.media_url }

                InstagramMediaResultDto(
                    id = it.id,
                    username = it.username ?: "",
                    timestamp = LocalDateTime.parse(it.timestamp, formatter),
                    media_type = it.media_type,
                    media_urls = carouselMediaUrls,
                    permalink = it.permalink,
                    caption = it.caption,
                )
            } else {
                InstagramMediaResultDto(
                    id = it.id,
                    username = it.username ?: "",
                    timestamp = LocalDateTime.parse(it.timestamp, formatter),
                    media_type = it.media_type,
                    media_urls = listOf(it.media_url),
                    permalink = it.permalink,
                    caption = it.caption,
                )
            }
        }
    }
}
