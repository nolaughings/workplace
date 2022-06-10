package com.ruoyi.cms.controller;

import cn.hutool.core.io.FileUtil;
import com.ruoyi.cms.domain.OnlineEditFile;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.plugs.common.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OnlineEditController extends BaseController {
    @Value("${spring.profiles.active}")
    private String active;

    @RequestMapping(value={"/sys/onlineEdit"})
    public String onlineEdit(Model model)throws Exception {


        return "/cms/onlineEdit/onlineEdit";
    }
    @RequestMapping(value={"/sys/onlineEdit/fileList"})
    @ResponseBody
    public List<OnlineEditFile> onlineEditFileList(Model model)throws Exception {

        String filePath = ResourceUtils.getURL("classpath:").getPath();
        List<OnlineEditFile> fileList = new ArrayList<>();
        getAllFilePaths(filePath,fileList,0,"");
        //return AjaxResult.success(fileList);
        return fileList;
    }
    /**
     * 获取内容
     * @return
     */
    @RequestMapping(value = "/sys/onlineEdit/getContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getContent(String filePath) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        String content = FileUtil.readUtf8String(path+filePath);
        return AjaxResult.success(content);
    }
    /**
     * 保存内容
     * @return
     */
    @RequestMapping(value = "/sys/onlineEdit/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult save(String filePath, String content) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        File file = new File(path+filePath);
        long lastModified = file.lastModified();
        FileUtil.writeUtf8String(content,path+filePath);
        file.setLastModified(lastModified);
        return AjaxResult.success();
    }
    /**
     * 递归获取某目录下的所有子目录以及子文件
     * @param filePath
     * @param filePathList
     * @return
     */
    private static List<OnlineEditFile> getAllFilePaths(String filePath, List<OnlineEditFile> filePathList,
                                                        Integer level, String parentPath) {
        File[] files = new File(filePath).listFiles();
        if (files == null) {
            return filePathList;
        }
        for (File file : files) {
            int num = filePathList.size()+1;
            OnlineEditFile sysFile = new OnlineEditFile();
            sysFile.setName(file.getName());
            sysFile.setId(num);
            sysFile.setParentId(level);
            if (file.isDirectory()) {
                sysFile.setDirectory(true);
                if(level==0){
                    if(file.getName().equals("templates")){
                        filePathList.add(sysFile);
                        parentPath = Constants.SYS_FILE_SEPARATOR+file.getName();
                        getAllFilePaths(file.getAbsolutePath(), filePathList,num,parentPath);
                        num++;
                    }
                }else{
                    filePathList.add(sysFile);
                    String subParentPath = parentPath+Constants.SYS_FILE_SEPARATOR+file.getName();
                    getAllFilePaths(file.getAbsolutePath(), filePathList,num,subParentPath);
                    num++;
                }
            } else {
                if(level!=0){
                    sysFile.setDirectory(false);
                    sysFile.setParentPath(parentPath+ Constants.SYS_FILE_SEPARATOR+file.getName());
                    filePathList.add(sysFile);
                    num++;
                }
            }
        }
        return filePathList;
    }
}
