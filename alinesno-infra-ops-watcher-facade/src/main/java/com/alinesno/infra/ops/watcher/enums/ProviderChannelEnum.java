package com.alinesno.infra.ops.watcher.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 支持的文档类型
 */
@Getter
public enum ProviderChannelEnum {

    PPT("PPT", "支持PPT、PPTX等，兼容WPS、Office文档类型", "fa-solid fa-file-powerpoint"),
    EXCEL("Excel", "支持Excel等，兼容WPS、Office文档类型", "fa-solid fa-file-excel"),
    WORD("Word", "支持Doc、Docx等，兼容WPS、Office文档类型", "fa-solid fa-file-word"),
    CVS("CVS", "支持CVS在线查看等，兼容WPS、Office文档类型", "fa-solid fa-file-csv"),
    PDF("PDF", "支持PDF在线查看等，兼容WPS、Office文档类型", "fa-solid fa-file-pdf"),
    PNG("PNG", "支持 jpg, jpeg, png, gif, bmp, ico, jfif, webp 等图片预览（翻转，缩放，镜像）", "fa-solid fa-file-image"),
    MP4("MP4", "支持 mp3, wav, mp4, flv 等音视频格式文件", "fa-solid fa-file-video"),
    MP3("MP3", "支持 mp3, wav, mp4, flv 等音视频格式文件", "fa-solid fa-file-audio"),
    ZIP("ZIP", "支持 zip, rar, jar, tar, gzip, 7z 等压缩包", "fa-solid fa-file-archive"),
    XMIND("XMind", "支持 xmind 软件模型文件", "fa-solid fa-brain");

    private final String name;
    private final String desc;
    private final String icon;

    ProviderChannelEnum(String name, String desc, String icon) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
    }

    public static List<ProviderChannelEnum> getAllDocumentTypes() {
        return Arrays.asList(ProviderChannelEnum.values());
    }

    public static Object[] getAllNames() {
        ProviderChannelEnum[] documentTypes = ProviderChannelEnum.values();
        Object[] names = new Object[documentTypes.length];
        for (int i = 0; i < documentTypes.length; i++) {
            names[i] = documentTypes[i].getName();
        }
        return names;
    }

    public static String getAllNameStr() {

        StringBuilder names = new StringBuilder();

        for(ProviderChannelEnum d : getAllDocumentTypes()){
            names.append(",")
                 .append(d.getName());
        }

        return names.toString();
    }
}
