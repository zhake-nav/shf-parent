package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/6 15:53
 * @Version 1.0
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {
    public static final String PAGE_UPLOAD = "house/upload";
    private static final String PAGE_SUCCESS = "common/successPage";
    private static final String LIST_ACTION = "redirect:/house/";
    @Reference
    private HouseImageService houseImageService;

    /**
     * 上传界面
     * @param houseId
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Integer type, ModelMap model) {
        model.addAttribute("houseId", houseId);
        model.addAttribute("type", type);
        return PAGE_UPLOAD;
    }

    /**
     * 图片同步到七牛云，将七牛云上的图片链接更新到houseImage
     * @param houseId
     * @param type
     * @param files
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam("file") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            // 随机生成一个新的文件名
            String newFileName = UUID.randomUUID().toString();

            // 上传图片
            QiniuUtil.upload2Qiniu(file.getBytes(), newFileName);

            // 调用服务成的insert方法，存入数据库
            // 生成url
            String url = "http://rpnesvx53.hn-bkt.clouddn.com/" + newFileName;
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setType(type);
            houseImage.setImageName(newFileName);
            houseImage.setImageUrl(url);
            houseImageService.insert(houseImage);
        }
        return Result.ok();
    }

    /**
     * 删除房产/房源图片
     * @param houseId
     * @param id
     * @return
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id) {
        HouseImage houseImage = houseImageService.getById(id);
        QiniuUtil.deleteFileFromQiniu(houseImage.getImageUrl());
        houseImageService.delete(id);
        return LIST_ACTION + houseId;
    }
}
