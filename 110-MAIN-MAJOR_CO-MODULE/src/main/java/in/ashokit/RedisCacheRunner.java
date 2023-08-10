package in.ashokit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheRunner implements ApplicationRunner {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		
		opsForHash.put("DHS", "DHS_OFFICE_ADDRESS", "1/H#ISLAND#RI#8793651532#dhs@gmail.com#www.dhs.com");
	}

}
