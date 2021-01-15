package com.ogham_stone.example.code.redislua.app.route;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("RouteTagCache")
class RouteTagCache
{
	@Id
	private Integer routeId;
	@Indexed
	private Set<Integer> tagIds;

	public RouteTagCache(Integer routeId, Set<Integer> tagIds)
	{
		this.routeId = routeId;
		this.tagIds = tagIds;
	}

	public Integer getRouteId()
	{
		return routeId;
	}

	public void setRouteId(Integer routeId)
	{
		this.routeId = routeId;
	}

	public Set<Integer> getTagIds()
	{
		return tagIds;
	}

	public void setTagIds(Set<Integer> tagIds)
	{
		this.tagIds = tagIds;
	}
}
