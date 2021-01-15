package com.ogham_stone.example.code.redislua.app.tag;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ogham_stone.example.code.redislua.app.tag.lua.TagLuaService;

@Service
public class TagService
{
	public static final int TAG_ID_1 = 1;
	public static final int TAG_ID_2 = 2;
	public static final int TAG_ID_3 = 3;
	public static final int TAG_ID_4 = 4;
	public static final int TAG_ID_5 = 5;
	public static final int TAG_ID_6 = 6;

	private final TagLuaService tagLuaService;
	private final TagCacheRepository tagCacheRepository;


	@Autowired
	public TagService(TagLuaService tagLuaService, TagCacheRepository tagCacheRepository)
	{
		this.tagLuaService = tagLuaService;
		this.tagCacheRepository = tagCacheRepository;
	}

	public void populateRedisCacheWithData()
	{
		tagCacheRepository.deleteAll();
		tagCacheRepository.save(new TagCache(TAG_ID_1, "tag1"));
		tagCacheRepository.save(new TagCache(TAG_ID_2, "tag2"));
		tagCacheRepository.save(new TagCache(TAG_ID_3, "tag3"));
		tagCacheRepository.save(new TagCache(TAG_ID_4, "tag4"));
		tagCacheRepository.save(new TagCache(TAG_ID_5, "tag5"));
		tagCacheRepository.save(new TagCache(TAG_ID_6, "tag6"));
	}

	public Set<Integer> getCommonTags(Set<Integer> routeIds) throws JsonProcessingException
	{
		return tagLuaService.getCommonTags(routeIds);
	}
}
