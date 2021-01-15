package com.ogham_stone.example.code.redislua.app.point;

import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.GeoIndexed;

@RedisHash("GPXPointCache")
public class GPXPointCache
{
	private Long id;
	private Integer routeId;
	private Integer position;
	@GeoIndexed
	private Point point;

	public GPXPointCache(Long id, Integer routeId, Integer position, Point point)
	{
		this.id = id;
		this.routeId = routeId;
		this.position = position;
		this.point = point;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getRouteId()
	{
		return routeId;
	}

	public void setRouteId(Integer routeId)
	{
		this.routeId = routeId;
	}

	public Integer getPosition()
	{
		return position;
	}

	public void setPosition(Integer position)
	{
		this.position = position;
	}

	public Point getPoint()
	{
		return point;
	}

	public void setPoint(Point point)
	{
		this.point = point;
	}
}
