package com.ogham_stone.example.code.redislua.app.route;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogham_stone.example.code.redislua.app.tag.TagService;

@Service
public class RouteService
{
	public static final int ROUTE_ID_1 = 1;
	public static final int ROUTE_ID_2 = 2;
	public static final int ROUTE_ID_3 = 3;

	private final RouteTagCacheRepository routeTagCacheRepository;

	@Autowired
	public RouteService(RouteTagCacheRepository routeTagCacheRepository)
	{
		this.routeTagCacheRepository = routeTagCacheRepository;
	}

	public void populateRedisCacheWithData()
	{
		routeTagCacheRepository.deleteAll();

		Set<Integer> route1Tags = new HashSet<>();
		route1Tags.add(TagService.TAG_ID_1);
		route1Tags.add(TagService.TAG_ID_2);
		route1Tags.add(TagService.TAG_ID_3);
		route1Tags.add(TagService.TAG_ID_4);
		routeTagCacheRepository.save(new RouteTagCache(ROUTE_ID_1, route1Tags));

		Set<Integer> route2Tags = new HashSet<>();
		route2Tags.add(TagService.TAG_ID_3);
		route2Tags.add(TagService.TAG_ID_4);
		route2Tags.add(TagService.TAG_ID_5);
		route2Tags.add(TagService.TAG_ID_6);
		routeTagCacheRepository.save(new RouteTagCache(ROUTE_ID_2, route2Tags));

		Set<Integer> route3Tags = new HashSet<>();
		route3Tags.add(TagService.TAG_ID_4);
		route3Tags.add(TagService.TAG_ID_5);
		route3Tags.add(TagService.TAG_ID_6);
		routeTagCacheRepository.save(new RouteTagCache(ROUTE_ID_3, route3Tags));
	}
}
