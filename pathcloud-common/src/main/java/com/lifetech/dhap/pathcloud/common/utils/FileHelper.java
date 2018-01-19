package com.lifetech.dhap.pathcloud.common.utils;

import java.io.*;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-21-13:34
 */
public class FileHelper {

    public  static void zip(FileOutputStream zipFileStream, List<File> inputFiles) throws IOException {
        CheckedOutputStream cos = new CheckedOutputStream(zipFileStream, new CRC32());
        ZipOutputStream out = new ZipOutputStream(cos);
        for (File inputFile : inputFiles) {
            zip(out, inputFile, inputFile.getName());
        }
        out.close(); // 输出流关闭
    }

    private static void zip(ZipOutputStream out, java.io.File f, String base) throws IOException {
        if (f == null) {
            return;
        }
        if (f.isDirectory()) {
            java.io.File[] fl = f.listFiles();
            if (fl != null && fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            } else if (fl != null) {
                for (int i = 0; i < fl.length; i++) {
                    zip(out, fl[i], base + "/" + fl[i].getName()); // 递归遍历子文件夹
                }
            }
        } else {
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(f));
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            int count;
            byte data[] = new byte[8192];
            while ((count = bi.read(data, 0, 8192)) != -1) {
                out.write(data, 0, count); // 将字节流写入当前zip目录
            }
            bi.close();// 输入流关闭
        }
    }

}
