package com.wzz.controller;

import com.wzz.pojo.*;
import com.wzz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.wzz.utils.MyUtils.StripHT;
import static com.wzz.utils.MyUtils.contentHand;

/*
路由控制器
 */
@Controller
public class RootContorller {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    FriendServiceImpl friendService;
    @Autowired
    LeaveMessageServiceImpl leaveMessageService;
    @Autowired
    CommentServiceImpl commentService;

    /*
    首页跳转(首页数据传递)
     */
    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        //查询所有的tag信息
        List<Map<String, Object>> list = articleService.queryAllTags();
        Set<String> set = new HashSet<>();
        for (Map<String, Object> map : list) {
            String[] tags = map.get("articleTags").toString().split(",");
            for (String tag : tags) {
                set.add(tag);
            }
        }
        //标签云 字符串处理
        String tags = set.toString();
        tags = tags.substring(1, tags.length() - 1);
        tags = tags.replaceAll(" ", "");
        model.addAttribute("tags", tags);

        //进入页面初始化里面参数(文章分页块)
        model.addAttribute("next", 2);
        model.addAttribute("prev", 1);

        //第一页默认的文章信息
        List<Article> articles = articleService.queryArticleByLimit(0, 3);
        //文章内容处理
        List<Article> targetArticles = contentHand(articles);
        model.addAttribute("articles", targetArticles);

        //首页网站信息传递
        model.addAttribute("articleSize", articleService.queryAllArticle().size());
        model.addAttribute("tagSize", tags.split(",").length);
        //最新留言传递
        model.addAttribute("leaveMessages", leaveMessageService.queryAllLeaveMessageByLimit());
        model.addAttribute("leaveMessageSize", leaveMessageService.queryAllLeaveMessage().size());
        //传递评论条数
        List<Comment> comments = commentService.queryAllComments();
        model.addAttribute("commentSize", comments.size());
        //将网站超级用户信息传给首页
        User user = userService.queryAdminUser();
        model.addAttribute("adminUser", user);
        return "index";
    }

    /*
    后台跳转
     */
    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        if (session.getAttribute("username") != null && session.getAttribute("userRole") != null) {
            if (session.getAttribute("userRole").toString().equals("2")) {//管理员
                //传递管理员界面欢迎页信息
                List<Article> articles = articleService.queryArticleRecent();
                model.addAttribute("articles", articles);
                //最新留言
                List<LeaveMessage> leaveMessages = leaveMessageService.queryAllLeaveMessageByLimit();
                model.addAttribute("leaveMessages", leaveMessages);
                return "admin/main";
            } else {//非管理员 回首页(暂定)
                //将当前普通用户信息传递过去
                User user = userService.queryUserByUsername(session.getAttribute("username").toString());
                model.addAttribute("user", user);
                return "commonUserAdmin/main";
            }
        } else {
            return "/toLogin";
        }
    }

    /*
    跳转后台写文章界面
     */
    @GetMapping("/admin/toEditorPage")
    public String toEditorPage() {
        return "admin/editor";
    }

    /*
    所有用户界面(后台)+分页
        currentPage默认传来为1
     */
    @GetMapping("/admin/allUser/{currentPage}")
    public String allUser(@PathVariable int currentPage, Model model) {
        //所有用户信息
        List<User> AllUser = userService.queryAllUser();
        //所有用户的条数
        int userSize = AllUser.size() - 1;
        //一页最多显示10条
        int pageSize = 10;
        //最多可分多少页
        int maxPage = userSize / 10;
        if (maxPage % 10 != 0) maxPage++;//防止少出一页
        if (maxPage == 0) maxPage = 1;
        //当前查询开始的条数
        int start = (currentPage - 1) * 10;
        start++;
        //查询出来的某一页数据
        List<User> users = userService.queryByLimit(start, pageSize);
        model.addAttribute("users", users);
        model.addAttribute("userSize", userSize);
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;
        //防止下一页上一页越界
        if (nextPage >= maxPage) nextPage = maxPage;
        if (prevPage <= 1) prevPage = 1;
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);
        return "admin/userList";
    }

    /*
    登录界面跳转
     */
    @GetMapping("/toLogin")
    public String toLogin(Model model) {
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());
        return "login";
    }

    /*
    注册界面跳转
     */
    @GetMapping("/toRegister")
    public String toRegister(Model model) {
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());
        return "register";
    }

    /*
    忘记密码页面跳转
     */
    @GetMapping("/toForgetPwd")
    public String toForgetPwd(Model model) {
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());
        return "forgetPwd";
    }

    /*
    管理员后台  => 修改密码
     */
    @GetMapping("/admin/toAdminUpdatePwd")
    public String toAdminUpdatePwd() {
        return "admin/updatePwd";
    }

    /*
    管理员后台 => 修改个人信息
     */
    @GetMapping("/admin/toAdminUpdateInfo")
    public String toAdminUpdateInfo(Model model) {
        //将用户信息携带给后台修改信息界面
        User user = userService.queryAdminUser();
        model.addAttribute("adminUser", user);
        return "admin/personInfo";
    }


    /*
    跳转后台管理员管理用户页面
     */
    @GetMapping("/admin/toupdateUserInfo/{username}")
    public String toupdateUserInfo(@PathVariable String username, Model model) {
        User user = userService.queryUserByUsername(username);
        //将当前用户的信息传过去
        model.addAttribute("user", user);
        return "admin/updateUserInfo";
    }

    /*
    跳转后台管理员添加用户界面
     */
    @GetMapping("/admin/toAddUser")
    public String toAddUser() {
        return "admin/addUser";
    }

    /*
    跳转后台友链添加界面
     */
    @GetMapping("/admin/toAddFriendLink")
    public String toAddFriendLink() {
        return "admin/friendLinkAdmin";
    }

    /*
    后台用户跳转更新友链信息
    */
    @GetMapping("/admin/toUpdateLink/{id}")
    public String toUpdateLink(@PathVariable int id, Model model) {
        FriendLink friendLink = friendService.queryLinkById(id);
        model.addAttribute("friendLink", friendLink);
        model.addAttribute("id", id);
        return "admin/updateFriendLink";
    }

    /*
    跳转后台管理留言界面
     */
    @GetMapping("/admin/toManageLeaveMessage")
    public String toManageLeaveMessage(Model model) {
        List<LeaveMessage> leaveMessages = leaveMessageService.queryAllLeaveMessage();
        model.addAttribute("leaveMessages", leaveMessages);
        return "admin/leaveMessageList";
    }

    /*
    普通用户后台的编辑资料跳转
     */
    @GetMapping("/userAdmin/toUpdateUserInfo/{username}")
    public String toUpdateUserInfo(@PathVariable String username, Model model) {
        //将当前需要修改用户的信息传递过去
        User user = userService.queryUserByUsername(username);
        model.addAttribute("user", user);
        return "commonUserAdmin/updateUserInfo";
    }

    /*
    普通用户后台的修改密码跳转
     */
    @GetMapping("/userAdmin/toUpdatePwd/{username}")
    public String toUpdatePwd(@PathVariable String username, Model model) {
        //将当前需要修改用户的信息传递过去
        User user = userService.queryUserByUsername(username);
        model.addAttribute("user", user);
        return "commonUserAdmin/updatePwd";
    }

    /*
    普通后台用户的我的评论页面
     */
    @GetMapping("/userAdmin/toCommentList/{username}")
    public String toCommentList(@PathVariable String username, Model model) {
        //将当前需要修改用户的信息传递过去
        User user = userService.queryUserByUsername(username);
        model.addAttribute("user", user);
        //传递这个用户所有的评论
        List<Comment> comments = commentService.queryAllCommentByUsername(user.getUsername());
        model.addAttribute("comments", comments);
        model.addAttribute("commentSize", comments.size());
        return "commonUserAdmin/commentList";
    }

    /*
    首页跳转留言界面
     */
    @GetMapping("/toLeaveMessage")
    public String toLeaveMessage(Model model) {
        //将当前需要用户的信息传递过去
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());
        return "leaveMessage";
    }

    /*
    管理员跳转留言更新界面
     */
    @GetMapping("/admin/toUpdateLeaveMessage/{id}")
    public String toUpdateLeaveMessage(@PathVariable int id, Model model) {
        LeaveMessage leaveMessage = leaveMessageService.queryLeaveMessageById(id);
        model.addAttribute("leaveMessage", leaveMessage);
        return "admin/updateLeaveMessage";
    }

    /*
    管理员后台跳转管理评论界面
     */
    @GetMapping("/admin/toCommentManage")
    public String toCommentManage(Model model) {
        List<Comment> comments = commentService.queryAllComments();
        model.addAttribute("comments", comments);
        model.addAttribute("commentSize", comments.size());
        return "admin/commentList";
    }

    /*
    跳转关于我界面
     */
    @GetMapping("/toAboutMe")
    public String toAboutMe(Model model) {
        //将当前需要用户的信息传递过去
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl", user.getAvatarImgUrl());
        return "aboutMe";
    }
}
