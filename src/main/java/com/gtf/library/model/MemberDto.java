package com.gtf.library.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberDto {

    @NotBlank
    private String name;

    private int totalBooksCheckout = 0 ;
}
