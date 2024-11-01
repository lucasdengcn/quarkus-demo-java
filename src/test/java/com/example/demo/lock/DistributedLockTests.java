package com.example.demo.lock;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DistributedLockTests {

    @Inject
    DistributedLock distributedLock;

    @Test
    void lock_on_success() {
        String businessKey = "2";
        Optional<String> acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertTrue(acquire.isPresent());
        //
        acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertFalse(acquire.isPresent());
        //
        distributedLock.forceRelease(businessKey);
    }

    @Test
    void lock_on_existing_will_fail() {
        String businessKey = "20";
        Optional<String> acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertTrue(acquire.isPresent());
        //
        acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertFalse(acquire.isPresent());
        //
        distributedLock.forceRelease(businessKey);
    }

    @Test
    void ttl_on_existing_key() {
        String businessKey = "3";
        Optional<String> acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertTrue(acquire.isPresent());
        //
        int result = distributedLock.ttl(businessKey, acquire.get());
        Assertions.assertTrue(result > 0);
        //
        distributedLock.forceRelease(businessKey);
    }

    @Test
    void ttl_on_non_existing_key() {
        String businessKey = "non";
        //
        int result = distributedLock.ttl(businessKey, businessKey);
        Assertions.assertEquals(-2, result);
    }

    @Test
    void release_on_success() {
        String businessKey = "4";
        Optional<String> acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertTrue(acquire.isPresent());
        //
        boolean result = distributedLock.release(businessKey, acquire.get());
        Assertions.assertTrue(result);
    }

    @Test
    void release_on_others_lock_will_fail() {
        String businessKey = "5";
        Optional<String> acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertTrue(acquire.isPresent());
        //
        boolean release = distributedLock.release("5", "6");
        Assertions.assertFalse(release);
    }


    @Test
    void extend_lock_duration_on_success() {
        String businessKey = "6";
        Optional<String> acquire = distributedLock.acquire(businessKey, Duration.ofSeconds(60));
        Assertions.assertTrue(acquire.isPresent());
        // wait a while
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //
        int ttl = distributedLock.ttl(businessKey, acquire.get());
        Assertions.assertTrue(ttl > 0);
        // extend
        boolean flag = distributedLock.expire(businessKey, acquire.get(), Duration.ofSeconds(60));
        Assertions.assertTrue(flag);
        //
        int ttl2 = distributedLock.ttl(businessKey, acquire.get());
        distributedLock.release(businessKey, acquire.get());
        //
        Assertions.assertEquals(60, ttl2);
        Assertions.assertTrue(ttl2 > ttl);
    }

}