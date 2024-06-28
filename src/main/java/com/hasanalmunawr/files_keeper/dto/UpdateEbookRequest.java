package com.hasanalmunawr.files_keeper.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEbookRequest {

    private String publisher;
    private String category;
    @Size(max = 2000)
    private String description;
    private double price;
    private String language;
    private int numberOfPages;
    private boolean isAvailable;

}
