package com.mpd.prime.repository;

import com.mpd.prime.domain.Corps;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Corps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorpsRepository extends JpaRepository<Corps, Long> {

}
