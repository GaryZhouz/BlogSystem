package com.wzz.controller;

import com.wzz.pojo.Article;
import com.wzz.pojo.Comment;
import com.wzz.pojo.User;
import com.wzz.service.ArticleServiceImpl;
import com.wzz.service.CommentServiceImpl;
import com.wzz.service.LeaveMessageServiceImpl;
import com.wzz.service.UserServiceImpl;
import com.wzz.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.wzz.utils.MyUtils.*;

@Controller
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    LeaveMessageServiceImpl leaveMessageService;
    @Autowired
    CommentServiceImpl commentService;

    public ArticleServiceImpl getArticleService() {
        return articleService;
    }

    /*
        从编辑文章界面获取文章信息
    */
    @RequestMapping("/admin/writeArticle")
    public String writeArticle(String title,String author,String content,String tags,String type){
        Article article = new Article(author,title,content,type,tags);
        article.setPublishDate(formatTime(new Date()));

        //将文章添加进入数据库
        articleService.addArticle(article);
        return "redirect:/admin/toEditorPage";
    }

    /*
    首页文章分页渲染
        page是前端异步请求传来的当前页数
     */
    @RequestMapping("/{page}")
    public String indexArticle(@PathVariable int page,Model model){

        //查询网站首页的所有文章信息
        List<Article> articles = articleService.queryAllArticle();
        //文章总篇数
        int articleSize = articles.size();
        //一页最大显示数量
        int pageSize = 3;
        //最多分多少页
        int maxPage = articleSize/pageSize;
        if (articleSize%pageSize!=0) maxPage++;
        if (page<=1) page = 1;
        if (page>=maxPage) page = maxPage;

        //当前查询开始的数值
        int start = (page-1)*pageSize;

        //查询到当前页面目标文章
        List<Article> targetArticles = articleService.queryArticleByLimit(start, pageSize);

        //当前页数传递给前端
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);

        //将当前页面的文章**处理后**传给前端
        List<Article> contentHand = contentHand(targetArticles);
        model.addAttribute("articles",contentHand);
        System.out.println(page);

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
        tags = tags.substring(1,tags.length()-1);
        tags = tags.replaceAll(" ","");
        model.addAttribute("tags",tags);

        //首页网站信息传递
        model.addAttribute("articleSize",articleService.queryAllArticle().size());
        model.addAttribute("tagSize",tags.split(",").length);
        //最新留言传递
        model.addAttribute("leaveMessages",leaveMessageService.queryAllLeaveMessageByLimit());
        model.addAttribute("leaveMessageSize",leaveMessageService.queryAllLeaveMessage().size());

        //将网站超级用户信息传给首页
        User user = userService.queryAdminUser();
        model.addAttribute("adminUser",user);
        return "index";
    }


    /*
    文章详情页面展示
     */
    @GetMapping("/articlePage/{id}")
    public String articlePage(@PathVariable int id,Model model){
        //将这片文章信息传递过去
        Article article = articleService.queryArticleById(id);
        model.addAttribute("article",article);

        //传递这篇文章所有的评论数
        model.addAttribute("commentCount",commentService.querySumByArticle(id));

        //传递这篇文章所有的顶级评论
        List<Comment> topComments = commentService.queryAllCommentPid(id);
        int size = topComments.size();
        model.addAttribute("commentSize",size);
        model.addAttribute("topComments",topComments);

        //查询并处理所有的子评论
        List<Map<Integer, Comment>> childComment = new ArrayList();//存放所有子评论的顶级评论id 和他的信息
        Map<Integer,Comment> map = new HashMap();//存放子评论的最顶级的评论的id,和它的信息
        List<Comment> childComments = commentService.queryAllComment(id);
        int count = 0;//帮助计数
        for (Comment comment : childComments) {
            if (comment.getPid()!=0){//不是顶级评论
                int i = MyUtils.queryTopCommentId(commentService, comment.getId(), comment.getPid());
                Comment targetComment = commentService.queryCommentById(comment.getId());
                targetComment.setPid(i);//设置当前的子评论id为最顶级的评论id  以便以传递前端判断
                map.put(count,targetComment);
                count++;
            }
        }
        childComment.add(map);
        model.addAttribute("childComment",childComment);

        //传递头部头像信息
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl",user.getAvatarImgUrl());
        return "articlePage";
    }

    /*
    文章分类主页面
     */
    @GetMapping("/articleTypePage/{type}/{page}")
    public String articleTypePage(Model model,@PathVariable int page,@PathVariable String type){
        //查询所有文章
        List<Article> articles = articleService.queryAllArticle();
        int allArticles = articles.size();
        //一页6篇
        int pageSize = 6;
        //最多分多少页
        int maxPage = allArticles/pageSize;
        if (allArticles%pageSize!=0) maxPage++;
        int start = (page-1)*pageSize;
        //目标页数的文章信息
        List<Article> targetArticles = articleService.queryArticleByLimit(start, pageSize);
        model.addAttribute("articles",targetArticles);
        //下一页上一页
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);
        //文章页面url区分****下一页上一页链接url是不同的
        model.addAttribute("url","/articleTypePage/");
        //查询文章分类信息
        List<Map<String, String>> maps = articleService.queryArticleType();
        model.addAttribute("maps",maps);
        //传递头部头像信息
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl",user.getAvatarImgUrl());
        return "articleType";
    }

    /*
    文章分类界面的类型查询 及其分页
     */
    //保存第一次传进来的类型
    static String tempType = null;
    @RequestMapping("/articleType/{type}/{page}")
    public String articleType(@PathVariable String type,@PathVariable int page,Model model){
        if (type!=null) tempType = type;
        //查询当前的文章分类信息
        List<Article> articles = articleService.queryArticleByType(tempType,0,9999);
        //当前类型的文章的总数
        int allArticles = articles.size();
        //一页分为多少篇
        int pageSize = 6;
        //最大页面数
        int maxPage = allArticles/pageSize;
        if (allArticles%pageSize!=0) maxPage++;
        int start = (page-1)*pageSize;
        //查询目标页文章信息
        List<Article> targetArticle = articleService.queryArticleByType(tempType, start, pageSize);
        model.addAttribute("articles",targetArticle);
        //下一页上一页
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);
        //下一页上一页url传递
        model.addAttribute("url","/articleType/");

        //查询文章分类信息
        List<Map<String, String>> maps = articleService.queryArticleType();
        model.addAttribute("maps",maps);
        //当前的类型传递过去
        model.addAttribute("type",type);
        //传递头部头像信息
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl",user.getAvatarImgUrl());
        return "articleType";
    }

    /*
    所有文章界面和分页(后台)
    */
    @GetMapping("/admin/allArticle/{page}")
    public String allArticle(@PathVariable int page,Model model){
        List<Article> articles = articleService.queryAllArticle();
        //文章总数
        int articleSize = articles.size();
        model.addAttribute("articleSize",articleSize);
        //一页展示10条
        int pageSize = 10;
        //最多分多少页
        int maxPage = articleSize/pageSize;
        if (articleSize%pageSize!=0) maxPage++;
        //开始的文章的条数
        int start = (page-1)*pageSize;
        //将目标页数的文章信息传递过去
        List<Article> targetArticles = articleService.queryArticleByLimit(start, pageSize);
        model.addAttribute("articles",targetArticles);
        //将下一页跟上一页信息传递过去
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);
        return "admin/allArticle";
    }

    /*
    后台用户删除文章
     */
    @GetMapping("/admin/deleteArticle/{id}")
    public String deleteArticle(@PathVariable int id){
        articleService.deleteArticle(id);
        return "redirect:/admin/allArticle/1";
    }

    /*
    后台用户更新已存在的文章
     */
    @GetMapping("/admin/toUpdateArticle/{id}")
    public String toUpdateArticle(@PathVariable int id,Model model){
        //将需要更新的文章信息传递过去
        Article article = articleService.queryArticleById(id);
        model.addAttribute("article",article);
        return "admin/updateArticle";
    }

    /*
    后台更新文章信息提交
     */
    @PostMapping("/admin/updateArticle")
    public String updateArticle(int id,String title,String author,String content,String tags,String type,int likes){
        articleService.updateArticle(id,title,author,content,tags,type,likes,formatTime(new Date()));
        return "redirect:/admin/allArticle/1";
    }

    /*
    后台用户检索文章及分页
     */
    static String title = null;
    @RequestMapping("/admin/articleSearch/{page}")
    public String articleSearch(@PathVariable int page,String key,Model model){
        //记录第一次传进来的title 用静态变量保存
        if (key!=null) title = key;
        //查询所有相关的文章
        List<Article> articles = articleService.queryArticleByTitleLimit("%"+title+"%", 0, 9999);
        int articleSize = articles.size();
        model.addAttribute("articleSize",articleSize);
        //一页展示多少条
        int pageSize = 10;
        //最多有多少页
        int maxPage = articleSize/pageSize;
        if (articleSize%pageSize!=0) maxPage++;
        //查询当前页数的文章信息
        int start = (page-1)*pageSize;
        List<Article> targetArticle = articleService.queryArticleByTitleLimit("%"+title+"%", start, pageSize);
        model.addAttribute("articles",targetArticle);
        //上一页下一页的页数传递过去
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);
        return "admin/articleSearch";
    }

    /*
   跳转标签云界面分页
    */
    @GetMapping("/toTagCloud/{page}")
    public String toTagCloud(Model model,@PathVariable int page){
        //查询所有的tag信息
        List<Map<String, Object>> list = articleService.queryAllTags();
        Set<String> set = new HashSet<>();
        for (Map<String, Object> map : list) {
            String[] tags = map.get("articleTags").toString().split(",");
            for (String tag : tags) {
                set.add(tag);
            }
        }
        //字符串处理
        String tags = set.toString();
        tags = tags.substring(1,tags.length()-1);
        tags = tags.replaceAll(" ","");
        model.addAttribute("tags",tags);
        //查询当前页文章信息
        int pageSize = 6;
        List<Article> articles = articleService.queryAllArticle();
        int articleSize = articles.size();
        int maxPage = articleSize/pageSize;
        if (articleSize%pageSize!=0) maxPage++;
        int start = (page-1)*pageSize;
        List<Article> targetArticle = articleService.queryArticleByLimit(start, pageSize);
        model.addAttribute("articles",targetArticle);
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);
        //下一页上一页url传递
        model.addAttribute("url","/toTagCloud/");
        //传递头像信息
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl",user.getAvatarImgUrl());
        return "tagcloud";
    }

    /*
    标签云界面点击标签查询
     */
    //保存第一次传进来的类型
    static String tempTag = null;
    @RequestMapping("/tagCloud/{type}/{page}")
    public String tagCloud(@PathVariable String type,@PathVariable int page,Model model){
        if (type!=null) tempTag = type;
        //查询当前的文章分类信息
        List<Article> articles = articleService.queryArticleByTags("%"+tempTag+"%",0,9999);
        //当前类型的文章的总数
        int allArticles = articles.size();
        //一页分为多少篇
        int pageSize = 6;
        //最大页面数
        int maxPage = allArticles/pageSize;
        if (allArticles%pageSize!=0) maxPage++;
        int start = (page-1)*pageSize;
        //查询目标页文章信息
        List<Article> targetArticle = articleService.queryArticleByTags("%"+tempTag+"%", start, pageSize);
        model.addAttribute("articles",targetArticle);
        //文章总数
        model.addAttribute("articleSize",articles.size());
        //下一页上一页
        int next = page+1;
        int prev = page-1;
        if (next>=maxPage) next = maxPage;
        if (prev<=1) prev = 1;
        model.addAttribute("next",next);
        model.addAttribute("prev",prev);
        //下一页上一页url传递
        model.addAttribute("url","/tagCloud/");
        //查询所有的tag信息
        List<Map<String, Object>> list = articleService.queryAllTags();
        Set<String> set = new HashSet<>();
        for (Map<String, Object> map : list) {
            String[] tags = map.get("articleTags").toString().split(",");
            for (String tag : tags) {
                set.add(tag);
            }
        }
        //字符串处理
        String tags = set.toString();
        tags = tags.substring(1,tags.length()-1);
        tags = tags.replaceAll(" ","");
        model.addAttribute("tags",tags);
        //传递当前的tag
        model.addAttribute("currentTag",tempTag);
        //传递头部头像信息
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl",user.getAvatarImgUrl());
        return "tagcloud";
    }

    /*
    文章界面喜欢数
    username前端当前登陆的用户名
     */
    @RequestMapping("/checkLike")
    @ResponseBody
    public String like(int id){
        articleService.updateLikes(id);
        //传给前端当前的喜欢数
        return String.valueOf(articleService.queryArticleById(id).getLikes());
    }
}
