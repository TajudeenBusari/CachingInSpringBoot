package com.tjtechy.Caching_in_spring_boot.config;

import com.tjtechy.Caching_in_spring_boot.entity.Department;
import com.tjtechy.Caching_in_spring_boot.service.DepartmentService;
import com.tjtechy.Caching_in_spring_boot.service.impl.DepartmentServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration //enables to run the class at the start of the application
@EnableCaching
@EnableScheduling
public class CacheConfig {

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  DepartmentServiceImpl departmentService;


  @PostConstruct //runs the method after the bean is created
  public void loadCache() {

    //load all objects in the cache at the start of the application
    Cache cache = cacheManager.getCache("applicationCache");

    System.out.println("********Initialising cache"); //just to see when cache is loaded
    List<Department> departmentList = departmentService.fetchAllDepartments();
    for (Department department : departmentList) {
      //department object is loaded in the cache
      //value is the object and key is the id of the object
      cache.put(department.getId(), department);
    }
  }

  /**
   * //runs the method every 15 seconds with an initial delay of 15 seconds,
   *   // so it takes 30 seconds to run the first time after the application starts
   */
  @Scheduled(fixedRate = 15000, initialDelay = 15000)
  public void clearCacheByScheduling() {
    System.out.println("********Clearing cache by scheduling");
    cacheManager
            .getCacheNames()
            .parallelStream()
            .forEach(name -> cacheManager.getCache(name)
                    .clear());
  }
}


/**
 * Add cacheable annotation to the method that you want to cache in the class where the repository is injected
 * //Another option is to load all objects in the cache at the start of the application,
 * // so requests will go the cache when next data is required instead of going to the database and this can be
 * //confirmed by checking the logs in the console that no queries are being made to the database. No select queries
 * //This is achieved by using CacheManager in the CacheConfig class.
 * //It is also possible to clear the cache by using Schedule annotation in the CacheConfig class instead
 * of using the clear cache methods in the controller
 */
