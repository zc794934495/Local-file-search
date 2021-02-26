package com.daimao.service;



import com.daimao.mapper.FileMetaMapper;
import com.daimao.model.FileMeta;
import com.daimao.util.ListUtil;
import com.daimao.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    private FileMetaMapper fileMetaMapper;

    /**
     * 保存filelist中的file到数据库
     * @param fileList
     */
    private void saveFileList(List<FileMeta> fileList) {
        fileMetaMapper.save(fileList);
    }

    /**
     * 删除filelist中的file到数据库
     * @param fileList
     */
    private void deleteFileList(List<FileMeta> fileList) {
        List<Integer> idList = fileList.stream().map(FileMeta::getId).collect(Collectors.toList());
        fileMetaMapper.delete(idList);
    }

    /**
     * 通过扫描的文件目录与本地数据文件比对，更新数据库
     * @param scanResultList 扫描出的文件
     */
    public void service(List<FileMeta> scanResultList) {
        List<FileMeta> queryRes = fileMetaMapper.queryAll();

        // 1. queryResultList - scanResultList
        List<FileMeta> ds1 = ListUtil.differenceSet(queryRes, scanResultList);
        if(ds1.size() != 0) {
            deleteFileList(ds1);
        }


        // 2. scanResultList - queryResultList
        List<FileMeta> ds2 = ListUtil.differenceSet(scanResultList, queryRes);
        if(ds2.size() != 0) {
            saveFileList(ds2);
        }

    }

    /**
     * 根据keyword查询文件
     * @param keyword 关键字
     * @return 根据关键字查询出的结果集
     */
    public List<FileMeta> query(String keyword) {
        return fileMetaMapper.query(keyword);
    }

    public List<FileMeta> queryAll() {
        return fileMetaMapper.queryAll();
    }


    public void scan(File root) {
        List<FileMeta> scanRes = new ArrayList<>();
        scanDir(root,scanRes);
        service(scanRes);
    }

    /**
     * 对选中目录进行扫描
     * @param root 选中的目录
     * @param scanRes 保存扫描出的结果集
     */
    private void scanDir(File root,List<FileMeta> scanRes) {
        if(!root.isDirectory()) {
            return;
        }
        File[] children = root.listFiles();
        if(children == null) {
            return;
        }
        for(File child : children) {
            scanDir(child, scanRes);
            if (child.isFile()) {
                scanRes.add(new FileMeta(child));
            }
        }
    }

}
