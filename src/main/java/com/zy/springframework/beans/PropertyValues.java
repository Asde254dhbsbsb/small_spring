package com.zy.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zy
 * @since 2022/7/23  20:53
 */

/**
 * 创建一个用于传递类中属性信息的类
 * 这两个类的作用就是
 * 因为属性可能会有很多，所以用一个集合包装一下
 * */
public class PropertyValues {

    private List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
}
