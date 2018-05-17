package com.todaynews.ssm2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/22下午 10:13
 */
@Service
public class JedisAdapter implements InitializingBean {

    //
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    //
    private Jedis jedis = null;
    private JedisPool jedisPool = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化
        jedisPool = new JedisPool("localhost", 6379);
    }

    //获取一个Jedis
    private Jedis getJedis() {
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("获取Jedis失败! " + e.getMessage());
        } finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
        return jedis;
    }

    //获取Redis的set集合中某个key对应的value
    public String get(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            logger.error("Jedis get 发生异常!" + e.getMessage());
            return null;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    //向Redis中Set集合添加值:点赞
    public long sadd(String key, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e){
            logger.error("Jedis sadd 发生异常!" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    //移除：取消点赞
    public long srem(String key, String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e){
            logger.error("Jedis srem 异常：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    //判断key,value是否是在set集合中
    public boolean sismember(String key, String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e){
            logger.error("Jedis sismember 异常：" + e.getMessage());
            return false;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    //获取set集合大小
    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("Jedis scard 异常：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    // 存入List集合中
    public long lpush(String key, String value) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long result = jedis.lpush(key, value);
            return result;
        } catch (Exception e) {
            logger.error("Jedis lpush 异常 ：" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    // 从List集合中取出
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            //假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
            //反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout, key);
        }catch (Exception e){
            logger.error("Jedis brpop 异常 ：" + e.getMessage());
            return null;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }
}
