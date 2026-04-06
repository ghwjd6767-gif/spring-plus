package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserFileDownloadUrlResponse {

    private final String url;

    public UserFileDownloadUrlResponse(String url) {
        this.url = url;
    }
}
