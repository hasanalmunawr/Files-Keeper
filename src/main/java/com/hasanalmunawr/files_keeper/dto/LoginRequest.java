package com.hasanalmunawr.files_keeper.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;


public record LoginRequest(
        @NonNull
        @NotBlank
        String email,
        @NonNull
        @NotBlank
        String password) {
}
