package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
