# CachingInSpringBoot
This project is a simple example of caching in Spring Boot. It uses the `@Cacheable` annotation to cache the result of a method. 

Spring cache is an abstract framework.
It should be used when methods returns the same result for the given input 
for multiple invocations.
Spring provides support for multiple cache providers like EhCache, Hazelcast, Infinispan, Redis, and others.
Types of caching in Spring:
1. In memory caching:
    - Simplest form of caching
    - Examples include `ConcurrentHashMap`, EhCache and `Caffeine`
    - Cache is stored in memory
    - Not suitable for large applications
2.  Spring's @Cacheable annotation:
    - Enables method-level caching.
    - Works with a variety of cache providers(EhCache, Caffeine, Redis etc.).
    - Spring provides a simple annotation-based caching mechanism
    - It is a declarative caching mechanism
3. Using external caching providers like Redis, EhCache, etc.
    - Redis is a popular caching provider
    - Redis is an in-memory data structure store, used as a database, cache, and message broker
    - Redis is a key-value store
    - Redis is a NoSQL database
    - Redis is an open-source software project
    - Redis is a data structure server
    - Redis is a high-performance database
    - Redis is a distributed cache
    - dependency: <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
      </dependency>
4. Http Response Cache:
    - Uses headers like `Cache-Control` and `ETag` to reduce API calls.
etc.


