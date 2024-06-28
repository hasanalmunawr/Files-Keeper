package com.hasanalmunawr.files_keeper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {

    @JsonProperty("first_name")
    private String fistName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String phone;
    private String role;

}
