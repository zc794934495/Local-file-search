package com.daimao.service;



import com.daimao.mapper.OperateTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {
    @Autowired
    private OperateTableMapper operateTableMapper;

    /**
     * 建表
     */
    public void init() {
        operateTableMapper.init();
    }
}
