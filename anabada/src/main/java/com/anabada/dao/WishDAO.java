package com.anabada.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Wish;

@Mapper
public interface WishDAO {

	int insertWish(Map<String, Object> map);

	int deleteWish(Map<String, Object> map);

	Wish selectWish(Map<String, Object> map);

}
