package com.changing.parser;

import com.changing.model.StructLog;

import java.util.List;

/**
 * @Description :
 * @Author : wuchangqing
 * @Date : 2017/9/22
 */
public interface Parsable {

    /**
     * 解析日志
     *      如果某行不符合要求，错误内部消化
     * @param logs
     * @return
     */
    List<StructLog> parseQuietly(List<String> logs);

//    List<StructLog> parse(List<String> logs) throws Exception;

}
