package com.gtf.library.model;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class RentDto {

    @Min(1)
    public long bookId;

    @Min(1)
    public long memberId;
}
