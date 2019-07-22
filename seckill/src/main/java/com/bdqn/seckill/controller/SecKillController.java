package com.bdqn.seckill.controller;

import com.bdqn.seckill.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SecKillController {

    @Autowired
    SecKillService secKillService;


    @RequestMapping("/seckill")
    @ResponseBody
    public Map seckill(Integer psid, Integer pid, String uid) {
        Map result = new HashMap();
        try {
            secKillService.qinagou(psid, pid, uid);
            result.put("code", 0);
            result.put("msg", "恭喜你抢购成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
