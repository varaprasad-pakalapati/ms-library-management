package com.gtf.library.repository;

import com.gtf.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("Select b From Book b where b.title like %?1%" + " OR b.isbn LIKE %?1%")
    List<Book> search(final String keyword);

    @Modifying
    @Query("Update Book b set b.noOfCopies = ?1 where b.id = ?2")
    void updateBookCopies(final int noOfCopies, final long id);
}
