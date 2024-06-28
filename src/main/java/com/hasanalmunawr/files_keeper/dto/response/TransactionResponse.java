package com.hasanalmunawr.files_keeper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private String id;
    @JsonProperty("ebook_name")
    private String ebookName;
    @JsonProperty("ebook_id")
    private Integer ebookId;
    @JsonProperty("transaction_data_time")
    private LocalDateTime transactionDateTime;
    private Integer amount;
    @JsonProperty("price_total")
    private Double price;
}
