package com.ogham_stone.example.code.redislua.app;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ogham_stone.example.code.redislua.app.point.PointService;
import com.ogham_stone.example.code.redislua.app.point.lua.RouteIdMaxMinPosition;
import com.ogham_stone.example.code.redislua.app.route.RouteService;
import com.ogham_stone.example.code.redislua.app.tag.TagService;

@Service
public class MasterService
{
	private final PointService pointService;
	private final TagService tagService;
	private final RouteService routeService;

	@Autowired
	public MasterService(PointService pointService, TagService tagService, RouteService routeService)
	{
		this.pointService = pointService;
		this.tagService = tagService;
		this.routeService = routeService;
	}

	@PostConstruct
	public void populateData()
	{
		pointService.populateRedisCacheWithData();
		tagService.populateRedisCacheWithData();
		routeService.populateRedisCacheWithData();
	}

	public Set<RouteIdMaxMinPosition> findUniqueRouteIdsNear(Double neLatitude, Double neLongitude, Double swLatitude, Double swLongitude)
	{
		return pointService.findUniqueRouteIdsNear(neLatitude, neLongitude, swLatitude, swLongitude);
	}

	public Set<Integer> getCommonTags(Set<Integer> routeIds) throws JsonProcessingException
	{
		return tagService.getCommonTags(routeIds);
	}
}
