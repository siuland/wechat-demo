package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    /**
     * 验证消息的确来自微信服务器
     * <p>
     * 开发者通过检验signature对请求进行校验。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return
     */
    @GetMapping("send")
    public String configAccess(String signature, String timestamp, String nonce, String echostr) {
        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名校验 ===》 非法请求");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }

        log.error("签名校验 ===》 验证成功");
        // 返回echostr
        return echostr;
    }

    @PostMapping("send")
    public String send(@RequestBody String requestBody, @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名校验 ===》 非法请求");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }
        log.error("签名校验 ===》 验证成功");

        // 解析消息体，封装为对象
        WxMpXmlMessage xmlMessage = WxMpXmlMessage.fromXml(requestBody);
        WxMpXmlOutMessage outMessage = null;
        try {
            // 将消息路由给对应的处理器，获取响应
            outMessage = wxMpMessageRouter.route(xmlMessage);
        } catch (Exception e) {
            log.error("消息路由异常", e);
        }
        // 将响应消息转换为xml格式返回
        return outMessage == null ? null : outMessage.toXml();
    }
}
