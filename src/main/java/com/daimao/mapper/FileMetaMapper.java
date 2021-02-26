package com.daimao.mapper;

import com.daimao.model.FileMeta;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface FileMetaMapper {
    int save(List<FileMeta> fileList);

    int delete(List<Integer> idList);

    List<FileMeta> queryByPath(String path);

    List<FileMeta> query(String keyword);

    List<FileMeta> queryAll();
}
