package com.github.losemy.simple.dal.mybatisplus.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lose
 * @date 2019-09-09
 * 主要做扩展用
 **/
public interface SimpleBaseMapper<T> extends BaseMapper<T> {

}