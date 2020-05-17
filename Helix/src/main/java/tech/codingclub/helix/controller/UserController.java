package tech.codingclub.helix.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.*;
import tech.codingclub.helix.tables.Followers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: rishabh
 */
@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/create-post")
    public @ResponseBody  String createTweet(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
        Tweet tweet = new Tweet(data, null, new Date().getTime(), ControllerUtils.getUserId(request));
        new GenericDB<Tweet>().addRow(tech.codingclub.helix.tables.Tweet.TWEET, tweet);
        return "Posted Successfully";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recommendations")
    public
    String recommendation(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        Member member = ControllerUtils.getCurrentMember(request);
        List<Member> mem = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, null, 10, tech.codingclub.helix.tables.Member.MEMBER.ID.desc());
        modelMap.addAttribute("RECOMMENDATIONS", mem);
        return "recommendations";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/follow-member/{member_id}")
    public @ResponseBody  String followMember(@PathVariable("member_id") Long memberId, HttpServletResponse response, HttpServletRequest request) {
        Long currentUserId = ControllerUtils.getUserId(request);
        if(currentUserId != null && memberId != null && !currentUserId.equals(memberId)) {
            Follower follower = new Follower(currentUserId, memberId);
            new GenericDB<Follower>().addRow(Followers.FOLLOWERS, follower);
            return "Connected Successfully";
        } else {
            return "Not Permitted";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/followed")
    public
    String followed(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        Long currentUserId = ControllerUtils.getUserId(request);
        Condition condition = Followers.FOLLOWERS.USER_ID.eq(currentUserId);
        ArrayList<Long> followedId = (ArrayList<Long>) new GenericDB<Long>().getColumnRows(Followers.FOLLOWERS.FOLLOWING_ID, Followers.FOLLOWERS, Long.class, condition, 100);
        List<Member> followedMembers = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, tech.codingclub.helix.tables.Member.MEMBER.ID.in(followedId), 10, tech.codingclub.helix.tables.Member.MEMBER.ID.desc());
        modelMap.addAttribute("FOLLOWED", followedMembers);
        return "followed";
    }
}