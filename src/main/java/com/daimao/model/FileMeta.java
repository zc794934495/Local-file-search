package com.daimao.model;

import com.daimao.util.OutPutUtil;
import com.daimao.util.PinYinUtil;
import lombok.Getter;
import lombok.ToString;

import java.io.File;
import java.util.Date;
import java.util.Objects;

@Getter
@ToString
public class FileMeta {
    private final Integer id;
    private final String name;
    private final String path;
    private final String pinyin;
    private final String pinyinFirst;
    private final boolean directory;
    private final Long length;
    private final Long lastModified;

    public FileMeta(File file) {
        this.id = null;
        this.name = file.getName();
        this.path = file.getAbsolutePath();
        this.pinyin = PinYinUtil.getPinYin(name);
        this.pinyinFirst = PinYinUtil.getPinYinFirst(name);
        this.directory = file.isDirectory();
        this.length = file.length();
        this.lastModified = file.lastModified();
    }

    public FileMeta(Integer id,String name,String path,String pinyin,String pinyinFirst,Boolean directory,Long length,Long lastModified) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.pinyin = pinyin;
        this.pinyinFirst = pinyinFirst;
        this.directory = directory;
        this.length = length;
        this.lastModified = lastModified;
    }

    public String getLengthUI() {
        return OutPutUtil.formatLength(length);
    }

    public String getLastModifiedUI() {
        return OutPutUtil.formatTimestamp(lastModified);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMeta fileMeta = (FileMeta) o;
        return path.equals(fileMeta.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
