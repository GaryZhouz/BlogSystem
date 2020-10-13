package com.wzz.controller;

import com.wzz.pojo.User;
import com.wzz.service.UserServiceImpl;
import com.wzz.utils.MyUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
    用户相关的控制器
 */
@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //登录检测
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, Model model) throws NoSuchAlgorithmException {
        //查询这位用户
        User loginUser = userService.queryUserByUsername(username);
        //获取用户的盐
        String salt = loginUser.getSalt();
        //加密对比数据库的密文
        String targetPassword = MyUtils.saltEncryption(password, salt);
        //登陆成功
        if (loginUser != null && targetPassword.equals(loginUser.getPassword())) {
            //登陆成功 用户信息放入session
            session.setAttribute("username", loginUser.getUsername());
            session.setAttribute("userRole", loginUser.getRoleId());
            //管理员进入管理界面
            return "redirect:/admin";
        } else {//登陆失败
            User user = userService.queryAdminUser();
            //放入头部头像地址
            model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());
            model.addAttribute("msg", "客官,请检查你的账号密码叭");
            return "login";
        }
    }

    /*
        用户注销
     */
    @GetMapping("/lgout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("userRole");
        return "redirect:/";
    }

    /*
    用户名检测(注册)ajax
     */
    @PostMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(String username) {
        //当前注册的用户的用户名是否已存在
        User registerUser = userService.queryUserByUsername(username);
        if (registerUser == null) {
            return "y";
        } else {
            return "n";
        }
    }

    /*
    注册用户
     */
    @PostMapping("/register")
    public String register(
            @Param("username") String username, @Param("password") String password,
            @Param("trueName") String trueName, @Param("gender") String gender) throws NoSuchAlgorithmException {
        //盐值
        String salt = UUID.randomUUID().toString().substring(0, 6);
        //加密后的密码
        String targetPassword = MyUtils.saltEncryption(password, salt);

        User targetUser = new User(username, targetPassword, trueName, Integer.parseInt(gender), null, null, "这个人很懒,还未有座右铭", null);
        targetUser.setSalt(salt);
        //插入用户
        userService.addUser(targetUser);
        return "redirect:/";
    }

    /*
    用户找回密码
     */
    @PostMapping("/findBackPwd")
    public String findBackPwd(String oldUsername, String trueName, String gender, String newPassword, Model model) throws NoSuchAlgorithmException {
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());//将网站主页的头像url带过去

        //找到当前找回用户的信息
        User current_user = userService.queryUserByUsername(oldUsername);
        if (current_user == null) {
            model.addAttribute("tip", "用户不存在");
            return "forgetPwd";
        } else {//用户存在  验证身份信息
            if (trueName.equals(current_user.getTrueName()) && Integer.parseInt(gender) == current_user.getGender()) {
                //盐值
                String salt = UUID.randomUUID().toString().substring(0, 6);
                //加密后的密码
                String targetPassword = MyUtils.saltEncryption(newPassword, salt);

                userService.updatePwd(oldUsername, targetPassword, salt);
                return "redirect:/";
            } else {//用户存在 但是验证未通过
                model.addAttribute("tip", "验证信息失败,请客官重新一试");
                return "forgetPwd";
            }
        }
    }

    /*
    后台管理员修改自己登录密码
     */
    @PostMapping("/admin/adminUpdatePwd")
    public String adminUpdatePwd(String oldPassword, String newPassword, Model model) throws NoSuchAlgorithmException {
        //查询到当前管理员用户信息
        User user = userService.queryAdminUser();
        //处理之后的密文
        String oldPwd = MyUtils.saltEncryption(oldPassword, user.getSalt());

        if (oldPwd.equals(user.getPassword())) {
            //盐值
            String salt = UUID.randomUUID().toString().substring(0, 6);
            //加密后的密码
            String targetPassword = MyUtils.saltEncryption(newPassword, salt);

            userService.updatePwd(user.getUsername(), targetPassword, salt);
            return "redirect:/lgout";
        } else {
            model.addAttribute("err", "密码修改失败,原密码错误");
            return "admin/updatePwd";
        }
    }

    /*
    后台管理员用户个人信息修改
     */
    @PostMapping("/admin/adminUserUpdateInfo")
    public String adminUserUpdateInfo(
            String username, String trueName, String gender,
            String qq, String wechat, String signature,//signature个人信息
            @RequestParam("filename") MultipartFile file) {
        //管理员用户的原始头像url
        User user = userService.queryAdminUser();
        String realPath = user.getAvatarImgUrl();//放入数据库的真实地址 以便前端使用
        /*
        上传头像图片到静态资源文件夹中
         */
        if (!file.isEmpty()) {
            //获取文件名
            String fileName = file.getOriginalFilename();

            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 文件上传路径
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\imgs\\upload\\";

            // 解决中文路径,图片显示问题
            fileName = UUID.randomUUID() + suffixName;
            File dest = new File(filePath + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                //上传
                file.transferTo(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            realPath = "/imgs/upload/" + fileName;
        }

        userService.updateAdminUserInfo(username, trueName, gender, qq, wechat, signature, realPath);

        return "redirect:/lgout";
    }

    /*
    后台管理员删除普通用户
     */
    @GetMapping("/admin/deleteUser/{username}")
    public String deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return "redirect:/admin/allUser/1";
    }

    /*
    后台管理员修改用户信息
     */
    @PostMapping("/admin/updateUserInfo")
    public String updateUserInfo(String oldUsername, String username, String trueName, String gender, String qq, String wechat, String signature) {
        userService.updateUserInfo(oldUsername, username, trueName, gender, qq, wechat, signature);
        return "redirect:/admin/allUser/1";
    }

    /*
    后台管理员添加用户
     */
    @PostMapping("/admin/addUser")
    public String addUser(String username, String password, String trueName, String gender, String qq, String wechat, String signature) throws NoSuchAlgorithmException {
        String salt = UUID.randomUUID().toString().substring(0, 6);
        String targetPassword = MyUtils.saltEncryption(password, salt);

        User targetUser = new User(username, targetPassword, trueName, Integer.parseInt(gender), qq, wechat, signature, null);
        targetUser.setSalt(salt);
        userService.addUser(targetUser);
        return "redirect:/admin/allUser/1";
    }

    /*
    后台管理员用户检索用户(模糊搜索)
     */
    static String tempUsername = null;//记录第一次传来的username

    @RequestMapping("/admin/searchUser/{currentPage}")
    public String SearchUser(String username, @PathVariable int currentPage, Model model) {
        if (username != null) tempUsername = username;
        Map<String, Object> map = new HashMap<>();
        map.put("username", "%" + tempUsername + "%");
        map.put("start", (Integer) 0);
        map.put("pageSize", (Integer) 9999);
        //查询到所有的相关用户信息
        List<User> users = userService.queryUserByLike(map);
        //最大用户数
        int userSize = users.size();
        //一面最大展示数
        int pageSize = 10;
        //最多分多少页
        int maxPage = userSize / pageSize;
        if (userSize % pageSize != 0) maxPage++;//防止少一页
        //查询当前页数的用户信息
        int start = (currentPage - 1) * 10;
        map.clear();
        map.put("username", "%" + tempUsername + "%");
        map.put("start", (Integer) start);
        map.put("pageSize", (Integer) pageSize);
        List<User> pageUser = userService.queryUserByLike(map);
        model.addAttribute("users", pageUser);

        int next = currentPage + 1;
        int back = currentPage - 1;
        if (next >= maxPage) next = maxPage;
        if (back <= 1) back = 1;
        model.addAttribute("next", next);
        model.addAttribute("back", back);
        model.addAttribute("userSize", userSize);
        return "admin/userListSearch";
    }

    /*
    普通用户后台修改自己的用户信息
     */
    @PostMapping("/userAdmin/updateUserInfo")
    public String updateUserInfo(String trueName, String gender, String qq, String wechat, String signature,
                                 @RequestParam("filename") MultipartFile file, HttpSession session) {

        //普通用户的原始头像url
        User user = userService.queryUserByUsername(session.getAttribute("username").toString());
        String realPath = user.getAvatarImgUrl();//放入数据库的真实地址 以便前端使用
         /*
        上传头像图片到静态资源文件夹中
         */
        if (!file.isEmpty()) {
            //获取文件名
            String fileName = file.getOriginalFilename();

            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 文件上传路径
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\imgs\\upload\\commonUser\\";

            // 解决中文问题,liunx 下中文路径,图片显示问题
            fileName = UUID.randomUUID() + suffixName;
            File dest = new File(filePath + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                //上传
                file.transferTo(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            realPath = "/imgs/upload/commonUser/" + fileName;
        }

        userService.updateCommonUserInfo(session.getAttribute("username").toString(), trueName, gender, qq, wechat, signature, realPath);

        return "redirect:/userAdmin/toUpdateUserInfo/" + session.getAttribute("username");
    }

    /*
    普通用户的密码修改
     */
    @PostMapping("/CommonUserUpdatePwd")
    public String CommonUserUpdatePwd(String username, String oldPassword, String newPassword, HttpSession session, Model model) throws NoSuchAlgorithmException {
        //查出当前用户的信息
        User user = userService.queryUserByUsername(username);
        //处理用户当前密码
        String handlePwd = MyUtils.saltEncryption(oldPassword, user.getSalt());

        if (user != null && user.getPassword().equals(handlePwd)) {//验证成功
            //返回主页 抹除sesion
            session.removeAttribute("username");
            session.removeAttribute("userRole");
            //盐值
            String salt = UUID.randomUUID().toString().substring(0, 6);
            //加密后的密码
            String targetPassword = MyUtils.saltEncryption(newPassword, salt);
            userService.updatePwd(username, targetPassword, salt);
            return "redirect:/";
        } else {//修改验证失败
            model.addAttribute("user", userService.queryUserByUsername(username));
            model.addAttribute("err", "信息验证失败,请重新再试");
            return "commonUserAdmin/updatePwd";
        }
    }


}
