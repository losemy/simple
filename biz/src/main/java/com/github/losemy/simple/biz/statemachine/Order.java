package com.github.losemy.simple.biz.statemachine;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lose
 * @date 2019-10-13
 **/
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 4928277631413727123L;

    private Integer id;
    private String name;
    private OrderStatus status;
}
