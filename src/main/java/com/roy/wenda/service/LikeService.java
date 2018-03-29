package com.roy.wenda.service;

import com.roy.wenda.wendaUtil.JedisAdapter;
import com.roy.wenda.wendaUtil.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        return jedisAdapter.sismember(dislikeKey,String.valueOf(userId))? -1:0;
    }

    public long like(int userId,int entityType,int emtityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,emtityId);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));

        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityType,emtityId);
        jedisAdapter.srem(dislikeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId,int entityType,int emtityId){
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityType,emtityId);
        jedisAdapter.sadd(dislikeKey,String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType,emtityId);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}
