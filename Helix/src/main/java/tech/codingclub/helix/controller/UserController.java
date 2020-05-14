package tech.codingclub.helix.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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

}