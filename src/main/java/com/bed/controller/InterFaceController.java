package com.bed.controller;

import com.bed.dao.BedDao;
import com.bed.dao.UserDao;
import com.bed.entity.Bed;
import com.bed.entity.User;
import com.bed.utils.UploadUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class InterFaceController {

    @Resource
    private UserDao userDao;

    @Resource
    private BedDao bedDao;

    //登录接口
    @PostMapping("/login")
    public ResponseEntity login(@PathParam("account") String account, @PathParam("password") String password, HttpServletRequest request){

        User res = userDao.findByAccount(account);  //根据账户查询用户
        if(res == null){  //如果为空直接抛出错误
            throw new RuntimeException("用户不存在");
        }
        if (res.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("account",account);  //在当前session中添加登录账户
            return ResponseEntity.ok("登录成功");
        }else throw new RuntimeException("密码错误"); //密码不正确也抛出错误
    }

    //退出登录接口
    @GetMapping("/logout")
    public void logout(HttpServletRequest request){
        request.getSession().removeAttribute("account");  //退出登录时在session中移除当前账号
    }

    //注册接口
    @PostMapping("/register")
    public String register(@PathParam("account") String account, @PathParam("password") String password){
        User res = userDao.findByAccount(account);  //根据账号查询所要注册的账号是否已经被注册了
        if(res!=null) throw new RuntimeException("该账户已经注册"); //如果查询结果不为空，则抛出错误
        User user = User.builder()
                .id(null)
                .account(account)
                .password(password).build();
        userDao.save(user);   //否则直接插入新账号到数据库中
        return "注册成功";
    }

    //上传图片接口
    @PostMapping("/upload/{type}")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@PathVariable String type,HttpServletRequest request) {
        String fileName = UUID.randomUUID().toString();  //生产随机文件名
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));  //获取图片的类型
        String filePath = "/Users/laishao/Desktop/images/";  //定义图片临时储存的位置
        File dest = new File(filePath + fileName + fileType);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();  //如果父级文件夹不存在，则先创建父级文件夹
        }
        try {
            file.transferTo(dest);  //粘贴到在临时路径下
            String url = UploadUtils.upload(dest); //使用文件上传工具类上传图片，得到图片的链接地址
            Bed bed = Bed.builder().id(null)
                    .account((String) request.getSession().getAttribute("account"))
                    .type(type)
                    .url(url).build();
            bedDao.save(bed);  //插入数据库中
            return url; //返回地址给前端
        } catch (IOException e) {
            throw new RuntimeException("上传失败");
        }
    }

    //获取公共照片
    @GetMapping("/getPublicImage")
    public List<Bed> getPublicImage(){
        return bedDao.findByType("public");
    }

    //获取私人照片
    @GetMapping("/getPrivateImage")
    public List<Bed> getPrivateImage(HttpServletRequest request){
        HttpSession session = request.getSession();
        return bedDao.findByTypeAndAccount("private", (String) session.getAttribute("account"));
    }
}