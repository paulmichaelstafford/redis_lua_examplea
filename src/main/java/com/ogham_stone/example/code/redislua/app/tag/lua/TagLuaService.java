package com.ogham_stone.example.code.redislua.app.tag.lua;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TagLuaService
{
	private final Logger logger = LoggerFactory.getLogger(TagLuaService.class);

	private final String intersectOfSetsSha1;
	private final RedisTemplate redisTemplate;

	private static final String ROUTE_TAG_CACHE_NAME = "RouteTagCache";
	private static final String TAG_CACHE_NAME = "TagCache";
	private static final String TAG_ID_VARIABLE_NAME = "tagId";

	@Autowired
	public TagLuaService(String intersectOfSetsSha1, RedisTemplate redisTemplate)
	{
		this.intersectOfSetsSha1 = intersectOfSetsSha1;
		this.redisTemplate = redisTemplate;
	}
	public Set<Integer> getCommonTags(Set<Integer> routeIds)
	{
		long startTime = System.currentTimeMillis();
		logger.info("getCommonTags() start");
		final Set<Integer> tagIds = new HashSet<>();
		String routeIdsCsv = routeIds.stream().map(String::valueOf)
			.collect(Collectors.joining(","));
		redisTemplate.execute((RedisCallback<Object>) connection -> {
			List<Long> luaResult = null;
			luaResult = connection.evalSha(intersectOfSetsSha1, ReturnType.MULTI, 0, ROUTE_TAG_CACHE_NAME.getBytes(), TAG_CACHE_NAME.getBytes(),
				TAG_ID_VARIABLE_NAME.getBytes(), routeIdsCsv.getBytes());
			for(Long row : luaResult) {
				tagIds.add(row.intValue());
			}
			return true;
		});
		logger.info("getCommonTags() routeIds {} took {}", routeIdsCsv, System.currentTimeMillis() - startTime);
		return tagIds;
	}
}
