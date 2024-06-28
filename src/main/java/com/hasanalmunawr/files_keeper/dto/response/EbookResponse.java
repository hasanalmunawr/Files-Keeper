package com.hasanalmunawr.files_keeper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EbookResponse {

    private String title;
    private String isbn;
    private String publisher;
    private String category;
    private String description;
    private double price;
    private String language;

    private LocalDate publicationDate;
    private int numberOfPages;
    private double rating;

    private byte[] coverEbook;
    private byte[] ebookFile;
    private boolean availability;
}
