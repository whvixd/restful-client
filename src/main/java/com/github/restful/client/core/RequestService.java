package com.github.restful.client.core;

import com.github.restful.client.model.common.RequestParam;

/**
 * Created by wangzhx on 2018/11/6.
 */
public interface RequestService<T> {

    T invoke(RequestParam requestParam);
}
