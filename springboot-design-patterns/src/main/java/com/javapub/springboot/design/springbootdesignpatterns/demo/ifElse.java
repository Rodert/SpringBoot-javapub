package com.javapub.springboot.design.springbootdesignpatterns.demo;

import java.util.ArrayList;
import java.util.List;

public class ifElse {
    public static void main(String[] args) {
        // 属性值
        String props = "name";
        // 升序、降
        String order = "ascending";
        List<User> users = new ArrayList<>();  // 将 users 进行赋值

        if ("age".equals(props)) {
            if ("ascending".equals(order)) {
                // 升序排序
            } else {
                // 降序排序
            }
        } else if ("name".equals(props)) {
            if ("ascending".equals(order)) {
                // 升序排序
            } else {
                // 降序排序
            }
        } else if ("birthPlace".equals(props)) {
            if ("ascending".equals(order)) {
                // 升序排序
            } else {
                // 降序排序
            }
        }
    }


    class User {
        String props;
        String order;

        public String getProps() {
            return props;
        }

        public void setProps(String props) {
            this.props = props;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }
}
