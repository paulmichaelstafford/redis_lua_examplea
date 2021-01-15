package com.ogham_stone.example.code.redislua.app.point.lua;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ogham_stone.example.code.redislua.app.point.PointService;

@Service
public class LuaService
{
	private final Logger logger = LoggerFactory.getLogger(PointService.class);

	private final String findUniqueByPointNearSha1;
	private final RedisTemplate redisTemplate;

	private static final String ROOT_KEY = "GPXPointCache";
	private static final String Z_SET_NAME = "point";
	private static final String UNIT_TYPE = "km";

	@Autowired
	public LuaService(String findUniqueByPointNearSha1, RedisTemplate redisTemplate)
	{
		this.findUniqueByPointNearSha1 = findUniqueByPointNearSha1;
		this.redisTemplate = redisTemplate;
	}

	public Set<RouteIdMaxMinPosition> findUniqueRouteIdsNear(Double longitude, Double latitude, Double radius)
	{
		long startTime = System.currentTimeMillis();
		logger.info("findUniqueRouteIdsNear() start");
		final Set<RouteIdMaxMinPosition> routeIdMaxMinPositions = new HashSet<>();
		redisTemplate.execute((RedisCallback<Object>) connection -> {
			List<List<Long>> luaResult = connection.evalSha(findUniqueByPointNearSha1, ReturnType.MULTI, 0, ROOT_KEY.getBytes(),
				Z_SET_NAME.getBytes(), longitude.toString().getBytes(), latitude.toString().getBytes(), radius.toString().getBytes(),
				UNIT_TYPE.getBytes());
			for (List<Long> row : luaResult) {
				routeIdMaxMinPositions.add(new RouteIdMaxMinPosition(row.get(0), row.get(1).intValue(), row.get(2).intValue()));
			}
			return true;
		});
		logger.info("findUniqueRouteIdsNear() longitude {} latitude {} radius{} took {}", longitude, latitude, radius,
			System.currentTimeMillis() - startTime);
		return routeIdMaxMinPositions;
	}
}
