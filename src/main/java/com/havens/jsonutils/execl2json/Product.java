package com.havens.jsonutils.execl2json;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/6 11:35
 */
public class Product implements Serializable {
    @Id
    @Column(name="ID")
    private String id;

    @Column(name="PRODUCTID")
    private String productId;

    @Column(name="NAMEID")
    private String nameId;

    @Column(name="NAME")
    private String name;
}
