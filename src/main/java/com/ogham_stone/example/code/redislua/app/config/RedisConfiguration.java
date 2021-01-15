package com.ogham_stone.example.code.redislua.app.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration
{
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private Integer port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.database}")
	private Integer database;

	@Bean
	JedisConnectionFactory jedisConnectionFactory()
	{
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPassword(password);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setDatabase(database);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate()
	{
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean
	public String findUniqueByPointNearSha1() throws IOException
	{
		final String scriptString = RedisScript.of(getFile(getClass(), "/findRouteIdsAndMaxMinPositionByPointNear.lua"), String.class)
			.getScriptAsString();
		return redisTemplate().execute((RedisCallback<String>) connection -> {
			DefaultStringRedisConnection newConnection = new DefaultStringRedisConnection(connection);
			return newConnection.scriptLoad(scriptString);
		});
	}

	@Bean
	public String intersectOfSetsSha1() throws IOException
	{
		final String scriptString = RedisScript.of(getFile(getClass(), "/intersectOfSets.lua"), String.class)
			.getScriptAsString();
		return redisTemplate().execute((RedisCallback<String>) connection -> {
			DefaultStringRedisConnection newConnection = new DefaultStringRedisConnection(connection);
			return newConnection.scriptLoad(scriptString);
		});
	}

	public static String getFile(Class callingClass, String fileName) throws IOException
	{
		try (InputStream inputStream = callingClass.getClass().getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
		{
			return reader.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
}
