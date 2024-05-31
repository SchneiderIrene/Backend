package de.leafgrow.leafgrow_project.security.sec_dto;

import java.util.Objects;

public class RefreshRequestDto {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefreshRequestDto that = (RefreshRequestDto) o;

        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return refreshToken != null ? refreshToken.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("RefreshRequestDto: refresh token - %s", refreshToken);
    }
}
