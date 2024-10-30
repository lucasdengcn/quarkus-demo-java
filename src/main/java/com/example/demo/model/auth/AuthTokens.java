package com.example.demo.model.auth;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Builder
@Schema
public class AuthTokens {
    private String userName;
    private String scopes;
    private String accessToken;
    private String refreshToken;
    private String idToken;
}
