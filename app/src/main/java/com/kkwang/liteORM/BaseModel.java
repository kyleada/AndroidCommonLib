package com.kkwang.liteORM;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by kw on 2016/3/8.
 */
public class BaseModel implements Serializable {

    // 设置为主键,自增
    @PrimaryKey(AssignType.AUTO_INCREMENT) public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}