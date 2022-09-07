# Spring-Boot-Cache-Redis-EhCache
Spring에서 제공하는 캐시 라이브러리 Redis Cache 와 EhCache Cache를 이용한 실행환경별 적용


운영환경(local, production)별 CacheManager 구현체를 구성합니다


local : EhCache

production : Redis

```
$ docker pull redis:alpine  
$ docker network create redis-net
$ docker network ls
$ docker run --name my-redis -p 6379:6379 --network redis-net -d redis:alpine redis-server --appendonly yes
$ docker ps
$ docker run -it --network redis-net --rm redis:alpine redis-cli -h my-redis

$ monitor 
또는
Medis와 같은 Redis Tool로 Data의 get/set 확인
```

@ConditionalOnProperty Annotation을 사용한 운영환경(`spring.profiles.active`)별 CacheManager 구현체 생성하였습니다.

EhCache 설정을 ehcache.xml 이 아닌 POJO Configuration으로 설정하였습니다.
