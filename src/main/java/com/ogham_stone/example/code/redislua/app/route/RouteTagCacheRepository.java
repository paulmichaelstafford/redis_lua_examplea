package com.ogham_stone.example.code.redislua.app.route;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RouteTagCacheRepository extends CrudRepository<RouteTagCache, Long>
{
    Optional<RouteTagCache> findByEmail(String email);
}
