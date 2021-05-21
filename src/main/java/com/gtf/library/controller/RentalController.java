package com.gtf.library.controller;

import com.gtf.library.model.RentDto;
import com.gtf.library.service.RentalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Validated
@Slf4j
@AllArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping(value = "/rent-book")
    public ResponseEntity<Void> rentBook(@RequestBody @Valid RentDto rentDto) {
        log.info("Create book rental: Request received to rent a book {}", rentDto);
        final long id = rentalService.rentBook(rentDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }
}
