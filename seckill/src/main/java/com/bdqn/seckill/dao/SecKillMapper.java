package com.bdqn.seckill.dao;

import com.bdqn.seckill.entity.PromotionSecKill;

import java.util.List;

public interface SecKillMapper {
    List<PromotionSecKill>findUnstartSecKill();
    void updateStatus(PromotionSecKill promotionSecKill);
}
