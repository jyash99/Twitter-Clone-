package tech.codingclub.helix.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.*;
import tech.codingclub.helix.global.SysProperties;
import tech.codingclub.helix.tables.Followers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

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

    @RequestMapping(method = RequestMethod.POST, value = "/profile-image/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile, HttpServletRequest request) {
        Long currentUserId = ControllerUtils.getUserId(request);
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        String path = "";
        try {
            Long currentMemberId = ControllerUtils.getUserId(request);
            path = saveUploadedFile(uploadfile,currentMemberId);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(path, new HttpHeaders(), HttpStatus.OK);
    }

    private String saveUploadedFile(MultipartFile uploadfile, Long userId) {

        try {
            String path = SysProperties.getBaseDir() + "\\Images\\profile-image\\" + userId + ".jpg";
            uploadfile.transferTo(new File(path));
            return "/images/profile-image/" + userId + ".jpg";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/update")
    public
    String updateUser(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "updateuser";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recommendations")
    public
    String recommendation(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        Member member = ControllerUtils.getCurrentMember(request);
        List<Member> mem = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, null, 10, tech.codingclub.helix.tables.Member.MEMBER.ID.desc());
        ArrayList<Long> memberIds = new ArrayList<Long>();
        for(Member s : mem) {
            memberIds.add(s.id);
        }
        Condition condition = Followers.FOLLOWERS.USER_ID.eq(member.id).and(Followers.FOLLOWERS.FOLLOWING_ID.in(memberIds));
        List<Follower> x = (List<Follower>) GenericDB.getRows(Followers.FOLLOWERS, Follower.class, condition, null);
        Set<Long> followedMemberIds = new HashSet<Long>();
        for(Follower follower : x) {
            followedMemberIds.add(follower.following_id);
        }
        for(Member memberTemp : mem) {
            if(followedMemberIds.contains(memberTemp.id)) {
                memberTemp.is_verified = true;
            }
        }
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

    @RequestMapping(method = RequestMethod.POST, value = "/un-follow-member/{member_id}")
    public @ResponseBody  String unfollowMember(@PathVariable("member_id") Long memberId, HttpServletResponse response, HttpServletRequest request) {
        Long currentUserId = ControllerUtils.getUserId(request);
        if(currentUserId != null && memberId != null && !currentUserId.equals(memberId)) {
            Condition condition = Followers.FOLLOWERS.USER_ID.eq(currentUserId).and(Followers.FOLLOWERS.FOLLOWING_ID.eq(memberId));
            boolean success = GenericDB.deleteRow(Followers.FOLLOWERS, condition);
            if(success) {
                return "UnFollowed Successfully!!";
            } else {
                return "Backend-Error!!";
            }
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