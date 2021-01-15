package com.ogham_stone.example.code.redislua.app.point;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GPXPointCacheRepository extends CrudRepository<GPXPointCache, Long>
{
    Optional<GPXPointCache> findByEmail(String email);

}
