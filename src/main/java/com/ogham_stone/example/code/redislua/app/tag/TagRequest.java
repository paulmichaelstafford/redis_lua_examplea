package com.ogham_stone.example.code.redislua.app.tag;

import java.util.Set;

public class TagRequest
{
	private Set<Integer> routeIds;

	public Set<Integer> getRouteIds()
	{
		return routeIds;
	}

	public void setRouteIds(Set<Integer> routeIds)
	{
		this.routeIds = routeIds;
	}
}
