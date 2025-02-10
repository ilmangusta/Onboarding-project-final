package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.WinnerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface WinnerOptionRepository extends JpaRepository<WinnerOption, Long> {
}
