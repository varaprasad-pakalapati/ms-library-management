package com.gtf.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    private String author;

    private long noOfPages;

    @Min(1)
    private int noOfCopies;
}
