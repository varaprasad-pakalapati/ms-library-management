package com.gtf.library.repository;

import com.gtf.library.entity.BookRent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRentRepository extends JpaRepository<BookRent, Long> {
}
