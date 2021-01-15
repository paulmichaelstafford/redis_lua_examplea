package com.ogham_stone.example.code.redislua.app.point.lua;

public class RouteIdMaxMinPosition
{
	private Long routeId;
	private Integer minPosition;
	private Integer maxPosition;

	public RouteIdMaxMinPosition(Long routeId, Integer minPosition, Integer maxPosition)
	{
		this.routeId = routeId;
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
	}

	public Long getRouteId()
	{
		return routeId;
	}

	public void setRouteId(Long routeId)
	{
		this.routeId = routeId;
	}

	public Integer getMinPosition()
	{
		return minPosition;
	}

	public void setMinPosition(Integer minPosition)
	{
		this.minPosition = minPosition;
	}

	public Integer getMaxPosition()
	{
		return maxPosition;
	}

	public void setMaxPosition(Integer maxPosition)
	{
		this.maxPosition = maxPosition;
	}
}
