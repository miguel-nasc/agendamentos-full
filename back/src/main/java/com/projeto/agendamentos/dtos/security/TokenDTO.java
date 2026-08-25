package com.projeto.agendamentos.dtos.security;

import java.util.Date;
import java.util.Objects;

public class TokenDTO {
    private String username;
    private Boolean auth;
    private Date now;
    private Date validity;
    private String accessToken;
    private String refreshToken;

    public TokenDTO() {
    }

    public TokenDTO(String username, Boolean auth, Date now,
                    Date validity, String accessToken, String refreshToken) {
        this.username = username;
        this.auth = auth;
        this.now = now;
        this.validity = validity;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenDTO tokenDTO = (TokenDTO) o;
        return Objects.equals(username, tokenDTO.username) && Objects.equals(auth, tokenDTO.auth) && Objects.equals(now, tokenDTO.now) && Objects.equals(validity, tokenDTO.validity) && Objects.equals(accessToken, tokenDTO.accessToken) && Objects.equals(refreshToken, tokenDTO.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, auth, now, validity, accessToken, refreshToken);
    }
}
