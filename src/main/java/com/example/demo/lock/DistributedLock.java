package com.example.demo.lock;

import io.quarkus.redis.datasource.value.SetArgs;
import io.vertx.mutiny.redis.client.RedisAPI;
import io.vertx.mutiny.redis.client.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class DistributedLock {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    @Inject
    private RedisAPI redisAPI;

    private String getLockKey(String businessKey) {
        return "dx-lock:" + businessKey;
    }

    /**
     * acquire the lock on key ${businessKey}
     *
     * @param businessKey
     * @param duration
     * @return transaction id via random value
     */
    public Optional<String> acquire(String businessKey, Duration duration){
        String lockKey = getLockKey(businessKey);
        String txId = UUID.randomUUID() + ":" + businessKey;
        SetArgs setArgs = new SetArgs();
        setArgs.nx();
        setArgs.px(duration);
        // SET resource_name my_random_value NX PX 30000
        List<String> args = List.of(lockKey, txId, "NX", "PX", duration.toMillis() + "");
        log.info("tryLock args: {}", args);
        Response response = redisAPI.set(args).await().atMost(TIMEOUT);
        log.info("tryLock response: {}", response);
        if (null == response){
            return Optional.empty();
        }
        if ("OK".equalsIgnoreCase(response.toString())){
            return Optional.of(txId);
        }
        return Optional.empty();
    }

    /**
     *
     * @param businessKey
     * @param txId
     * @return
     */
    public int ttl(String businessKey, String txId){
        String lockKey = getLockKey(businessKey);
        Response response = redisAPI.ttl(lockKey).await().atMost(TIMEOUT);
        log.info("ttl response: {}", response);
        if (null == response){
            return -2;
        }
        return response.toInteger();
    }

    /**
     *
     * @param businessKey
     */
    public void forceRelease(String businessKey){
        String lockKey = getLockKey(businessKey);
        redisAPI.del(List.of(lockKey)).await().atMost(TIMEOUT);
    }

    /**
     * Release the lock with value ${txId} on key ${businessKey}
     *
     * @param businessKey
     * @param txId
     * @return
     */
    public boolean release(String businessKey, String txId) {
        String script = """
                if redis.call("get",KEYS[1]) == ARGV[1] then
                    return redis.call("del", KEYS[1])
                else
                    return 0
                end
                """;
        String lockKey = getLockKey(businessKey);
        Response response = redisAPI.eval(List.of(script, "1", lockKey, txId)).await().atMost(TIMEOUT);
        log.info("release response: {}, {}, {}", businessKey, txId, response);
        if (null == response){
            return true;
        }
        return response.toInteger() > 0;
    }

    /**
     * extend a lock's duration
     *
     * @param businessKey
     * @param txId
     * @param duration
     * @return
     */
    public boolean expire(String businessKey, String txId, Duration duration) {
        String script = """
                if redis.call("get",KEYS[1]) == ARGV[1] then
                    return redis.call("expire", KEYS[1], ARGV[2])
                else
                    return 0
                end
                """;
        String lockKey = getLockKey(businessKey);
        Response response = redisAPI.eval(List.of(script, "1", lockKey, txId, duration.toSeconds() + "")).await().atMost(TIMEOUT);
        log.info("expire response: {}, {}, {}", businessKey, txId, response);
        if (null == response){
            return true;
        }
        return response.toInteger() > 0;
    }

}
