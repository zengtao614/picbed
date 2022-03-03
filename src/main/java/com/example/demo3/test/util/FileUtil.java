package com.example.demo3.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-5-6
 * @Content:文件处理工具类
 */
public class FileUtil {


    /**
     * 创建目录
     * @param destDirName 目标目录名
     * @return 创建成功返回true，否则返回false
     */
    public static boolean createFolder(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        }
        if (dir.mkdirs()) {
            // 创建成功
            return true;
        } else {
            return false;
        }
    }

    /**
     * 压缩ZIP包
     *
     * @param inputFile 输入文件列表
     * @param zipfile   输出的压缩文件
     * @return
     */
    public static boolean toZip(List<String> inputFile, String zipfile) {
        File file = null;
        try {
            file = new File(zipfile);
            // 创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            for (String s : inputFile) {
                File f = new File(s);
                compress(f, f.getName(), zipOut, true);
            }
            zipOut.close();
            fous.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != file) {
                file.delete();
            }
            return false;
        }
        return true;
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zos zip输出流
     * @param name 压缩后的名称
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     * 					false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, String name, ZipOutputStream zos, boolean keepDirStructure)
            throws Exception {

        byte[] buf = new byte[10240];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, name + "/" + file.getName(), zos, keepDirStructure);
                    } else {
                        compress(file, file.getName(), zos, keepDirStructure);
                    }
                }
            }
        }
    }




    public static void main(String[] args){
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        FileInputStream fis = null;
        try {
            File file = new File("D:" + File.separator + "pic");
            File[] files = file.listFiles();
            fos = new FileOutputStream("D:" + File.separator + "pic" + File.separator + "file.zip");
            zos = new ZipOutputStream(fos);
            byte[] bufs = new byte[1024 * 10];
            for (File f:files){
                ZipEntry zipEntry = new ZipEntry(f.getName());
                zos.putNextEntry(zipEntry);

                fis = new FileInputStream(f);
                int read = 0;
                while ((read = fis.read(bufs,0,10240))!=-1){
                    zos.write(bufs,0,read);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fis!=null){
                    fis.close();
                }
                if (zos!=null){
                    zos.close();
                }
                if (fos!=null){
                    fos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
