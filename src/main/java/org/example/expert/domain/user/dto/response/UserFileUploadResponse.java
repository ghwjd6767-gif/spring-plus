package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserFileUploadResponse {

    private final String key;

    public UserFileUploadResponse(String key) {
        this.key = key;
    }
}
