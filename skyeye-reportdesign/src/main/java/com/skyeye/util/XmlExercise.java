/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.util;

import org.json.JSONObject;
import org.json.XML;

/**
 * @ClassName: XmlExercise
 * @Description: Xml、json之间的相互转换工具类
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/26 11:50
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public class XmlExercise {

    /**
     * 将xml字符串<STRONG>转换</STRONG>为JSON字符串
     *
     * @param xmlString xml字符串
     * @return JSON<STRONG>对象</STRONG>
     */
    public static String xml2json(String xmlString) {
        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        return xmlJSONObj.toString();
    }

}
