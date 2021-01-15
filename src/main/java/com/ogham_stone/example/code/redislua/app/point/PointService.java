package com.ogham_stone.example.code.redislua.app.point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.ogham_stone.example.code.redislua.app.Utils;
import com.ogham_stone.example.code.redislua.app.point.lua.LuaService;
import com.ogham_stone.example.code.redislua.app.point.lua.RouteIdMaxMinPosition;
import com.ogham_stone.example.code.redislua.app.route.RouteService;

@Service
public class PointService
{
	private final GPXPointCacheRepository gpxPointCacheRepository;
	private final LuaService luaService;

	@Autowired
	public PointService(GPXPointCacheRepository gpxPointCacheRepository, LuaService luaService)
	{
		this.gpxPointCacheRepository = gpxPointCacheRepository;
		this.luaService = luaService;
	}

	public void populateRedisCacheWithData()
	{
		gpxPointCacheRepository.deleteAll();
		gpxPointCacheRepository.saveAll(generateGPXPointCaches());
	}

	private List<GPXPointCache> generateGPXPointCaches()
	{
		List<GPXPointCache> gpxPointCaches = new ArrayList<>();
		List<Point> pointsRoute1 = new ArrayList<>();
		pointsRoute1.add(new Point(52.445155d, 7.128085d));
		pointsRoute1.add(new Point(52.445156d, 7.128086d));
		pointsRoute1.add(new Point(52.445159d, 7.128089d));
		pointsRoute1.add(new Point(56.445158d, 9.128088d));
		pointsRoute1.add(new Point(56.445159d, 9.128089d));

		long id = 0;
		for(int position = 0; position < pointsRoute1.size(); position++)
		{
			GPXPointCache gpxPointCache = new GPXPointCache(id, RouteService.ROUTE_ID_1, position, pointsRoute1.get(position));
			gpxPointCaches.add(gpxPointCache);
			id++;
		}

		List<Point> pointsRoute2 = new ArrayList<>();
		pointsRoute2.add(new Point(52.445155d, 7.128075d));
		pointsRoute2.add(new Point(52.445146d, 7.128076d));
		pointsRoute2.add(new Point(52.445147d, 7.128077d));
		pointsRoute2.add(new Point(52.445148d, 7.128078d));
		pointsRoute2.add(new Point(52.445149d, 7.128079d));
		pointsRoute2.add(new Point(56.445148d, 9.128078d));
		pointsRoute2.add(new Point(56.445149d, 9.128079d));

		for(int position = 0; position < pointsRoute1.size(); position++)
		{
			GPXPointCache gpxPointCache = new GPXPointCache(id, RouteService.ROUTE_ID_2, position, pointsRoute2.get(position));
			gpxPointCaches.add(gpxPointCache);
			id++;
		}
		return gpxPointCaches;
	}

	public Set<RouteIdMaxMinPosition> findUniqueRouteIdsNear(Double neLatitude, Double neLongitude, Double swLatitude, Double swLongitude)
	{
		Point midPoint = Utils.midPoint(new Point(neLatitude, neLongitude), new Point(swLatitude, swLongitude));
		Double distanceInKm = Utils.distance(swLatitude, neLatitude, swLongitude, neLongitude) / 1000;
		return luaService.findUniqueRouteIdsNear(midPoint.getX(), midPoint.getY(), distanceInKm);
	}
}
