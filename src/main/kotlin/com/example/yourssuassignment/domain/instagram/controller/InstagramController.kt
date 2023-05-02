package com.example.yourssuassignment.domain.instagram.controller

import com.example.yourssuassignment.domain.instagram.controller.response.InstagramAccountResponse
import com.example.yourssuassignment.domain.instagram.controller.response.InstagramMediasResponse
import com.example.yourssuassignment.domain.instagram.controller.response.InstagramTokenResponse
import com.example.yourssuassignment.domain.instagram.facade.InstagramFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/instagram")
class InstagramController(
    private val instagramFacade: InstagramFacade,
) {
    @Operation(
        summary = "인스타그램 장기 토큰 생성",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당하는 정보가 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    fun getInstagramAccessToken(
        @RequestParam
        access_token: String
    ): InstagramTokenResponse =
        InstagramTokenResponse(
            accessToken = instagramFacade.getInstagramAccessToken(
                access_token = access_token
            )
        )

    @Operation(
        summary = "인스타그램 계정 정보 파싱",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당하는 정보가 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @GetMapping("/account")
    fun getInstagramAccount(
        @RequestParam
        access_token: String,
    ): InstagramAccountResponse =
        InstagramAccountResponse(
            account = instagramFacade.getInstagramAccount(
                access_token = access_token
            )
        )

    @Operation(
        summary = "인스타그램 게시물 파싱",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당하는 정보가 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @GetMapping("/media")
    fun listInstagramMedias(
        @RequestParam
        userId: String,
        @RequestParam
        access_token: String,
    ): InstagramMediasResponse =
        InstagramMediasResponse(
            medias = instagramFacade.listInstagramMedias(
                userId = userId,
                access_token = access_token
            )
        )
}
