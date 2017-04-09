package com.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class RedisTest {

	@Test
	public void testRedisSingle(){
		Jedis redis = new Jedis("localhost");
		System.out.println(redis.ping());
		redis.set("key1", "redis test");
		System.out.println(redis.get("key1"));
		redis.del("key1");
		redis.close();
	}
	
	@Test
	public void testRedisPool(){
		JedisPool jedisPool = new JedisPool("localhost");
		Jedis redis = jedisPool.getResource();
		System.out.println(redis.ping());
		redis.set("key2", "redis test2");
		System.out.println(redis.get("key2"));
		redis.del("key2");
		redis.close();
		jedisPool.close();
	}
	@Autowired
	JedisPool jedisPool;
	@Test
	public void testRedisWithSpring(){
		Jedis redis = jedisPool.getResource();
		System.out.println(redis.ping());
		redis.set("key3", "redis test3");
		System.out.println(redis.get("key3"));
		redis.del("key3");
		redis.close();
	}
}
