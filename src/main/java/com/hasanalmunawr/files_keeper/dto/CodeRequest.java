package com.hasanalmunawr.files_keeper.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record CodeRequest(
        @NonNull
        @NotBlank
        Integer code,
        String newPassword
) {

}
