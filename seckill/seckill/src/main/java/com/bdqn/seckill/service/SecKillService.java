package com.bdqn.seckill.service;

import com.bdqn.seckill.dao.SecKillMapper;
import com.bdqn.seckill.entity.PromotionSecKill;
import com.bdqn.seckill.exception.SecKillExcpetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ldwjava
 * @Date 2019/7/10 10:20
 * @Desc TODO
 **/

@Service
public class SecKillService {

    @Autowired
    private SecKillMapper secKillMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    public List<PromotionSecKill> findUnstartSkill() {
        return secKillMapper.findUnstartSecKill();
    }

    public void updateStatus(PromotionSecKill promotionSecKill) {
        secKillMapper.updateStatus(promotionSecKill);
    }


    //核心抢购业务
    public void qinagou(Integer psid, Integer pid, String uid) throws Exception {
        Integer goodsId = (Integer) redisTemplate.opsForList().leftPop("seckill:count:" + psid);
        if (goodsId != null) {
            System.out.println("成功抢购");
            //限制用户抢购的数量
            //判断当前用户是否在set集合中
            Boolean isExists = redisTemplate.opsForSet().isMember("seckll:user:" + psid, uid);
            if (isExists) {
                System.out.println("对不起,你已经成功抢购一次了,不能再抢了");
                //由于用户已经抢购过了,需要把刚刚弹出来的商品 补偿会list集合
                redisTemplate.opsForList().rightPush("seckill:count:" + psid, goodsId);
                throw new SecKillExcpetion("对不起,你已经成功抢购一次了,不能再抢了");
            } else {
                System.out.println("你可以抢购");
                redisTemplate.opsForSet().add("seckll:user:" + psid, uid);
            }
        } else {
            System.out.println("对不起,商品已经抢购一空");
            throw new SecKillExcpetion("对不起,商品已经抢购一空");
        }
    }
}
