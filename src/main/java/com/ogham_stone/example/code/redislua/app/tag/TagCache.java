package com.ogham_stone.example.code.redislua.app.tag;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("TagCache")
public class TagCache
{
	@Id
	private Integer tagId;
	private String name;

	public TagCache(Integer tagId, String name)
	{
		this.tagId = tagId;
		this.name = name;
	}

	public Integer getTagId()
	{
		return tagId;
	}

	public void setTagId(Integer tagId)
	{
		this.tagId = tagId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
