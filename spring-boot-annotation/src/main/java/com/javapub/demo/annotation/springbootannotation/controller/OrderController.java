package com.javapub.demo.annotation.springbootannotation.controller;

import com.javapub.demo.annotation.springbootannotation.annotation.Log;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/1/25 15:17
 * @Version: 1.0
 * @Description:
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * http://127.0.0.1:9001/order/order-info?id=1
     *
     * @param id
     * @return
     */
    @Log
    @RequestMapping("/order-info")
    public String OrderInfo(@RequestParam String id) {
        return "this is OrderController.OrderInfo id=" + id;
    }

    /**
     * http://127.0.0.1:9001/order/order-info-2/1
     *
     * @param id
     * @return
     */
    @Log
    @RequestMapping("/order-info-2/{id}")
    public String OrderInfo2(@PathVariable("id") String id) {
        return "this is OrderController.OrderInfo2 id=" + id;
    }

}
