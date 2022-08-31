package com.zhuzhu.store.controller;

import com.zhuzhu.store.config.ex.*;
import com.zhuzhu.store.entity.User;
import com.zhuzhu.store.service.UserService;
import com.zhuzhu.store.service.exception.InsertException;
import com.zhuzhu.store.service.exception.UsernameDuplicatedException;
import com.zhuzhu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("/regist")
//    表单 格式使用的是默认的ContentType类型application/x-www-form-urlencoded，格式为key1=value1&key2=value2提交到后台 ，不需要加@requestBody
    public JsonResult regist(User user) {
        userService.Register(user);

        return new JsonResult(OK);
    }

    @RequestMapping("/login")
    public JsonResult login(String username, String password, HttpSession session) {
        User data = userService.Login(username, password);
        //服务器自动创建session对象,存储到session中，全局session
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        // 获取session中绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult(OK, data);
    }

    //对post和get都支持
    @RequestMapping("/changepassword")
    public JsonResult changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult(OK);
    }

    @GetMapping("/getByUid")
    public JsonResult getByUid(HttpSession session) {
        User data = userService.getByUid(getuidFromSession(session));
//        System.out.println(data);
        return new JsonResult(OK, data);
    }

    @RequestMapping("/changeInfo")
    public JsonResult changeInfo(User user, HttpSession session) {
        // user z中有：username、email、phone、gender
        // uid数据需要再次封装到user对象中
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult(OK);
    }


    // 设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    // 设置文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<String>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * @param session
     * @param file
     * @return
     */
    @PostMapping("/changeAvatar")
    public JsonResult changerAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty())
        {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize()>AVATAR_MAX_SIZE)
        {
            throw new FileSizeException("文件超出限制大小");
        }
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType))
        {
            throw new FileTypeException("文件类型不支持");
        }
        // 上传的文件。。。。/upload/文件。png
        String parent = session.getServletContext().getRealPath("upload");

        //File 对象指向这个路径，File是否存在
        File dir =new File(parent);
        if (!dir.exists())
        {
            dir.mkdirs(); // 创建目录
        }
        // 获取到这文件名称，UUID工具来生成一个新的字符作为文件名加密
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;
        File dest = new File(dir, filename);
        // 将file写入这个空文件中
        try {
            file.transferTo(dest);
        }catch (FileStateException e)
        {
            throw new FileStateException("文件状态异常");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = getuidFromSession(session);
        String username= getUsernameFromSession(session);
        String avatar = "/upload/" + filename;
        userService.updateAvatarByUid(uid,avatar,username);

        return new JsonResult(OK,avatar);

    }


}
