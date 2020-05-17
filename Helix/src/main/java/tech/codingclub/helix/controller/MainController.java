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
@RequestMapping("/")
public class MainController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/helloworld")
    public String getQuiz(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String signUp(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public @ResponseBody SignupResponse signUpData(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        boolean user_created = false;
        String message = "";
        if(GenericDB.getCount(tech.codingclub.helix.tables.Member.MEMBER, tech.codingclub.helix.tables.Member.MEMBER.EMAIL.eq(member.getEmail())) > 0) {
            message = "User Already Exist for this Email Id";
        } else {
            member.role = "CM";
            user_created = true;
            message = "User Created";
            new GenericDB<Member>().addRow(tech.codingclub.helix.tables.Member.MEMBER, member);
        }
        return new SignupResponse(message, user_created);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/time")
    public @ResponseBody String getTime(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        TimeApi time = new TimeApi(new Date().toString(), new Date().getTime());
        return new Gson().toJson(time);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/wiki")
    public @ResponseBody String getWikiResult(ModelMap modelMap, @RequestParam("country") String country, HttpServletResponse response, HttpServletRequest request) {
        WikipediaDownloader wikipediaDownloader = new WikipediaDownloader(country);
        WikiResult x = wikipediaDownloader.getResult();
        return new Gson().toJson(x);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/wiki/html")
    public String getWikiResultHtml(ModelMap modelMap, @RequestParam("country") String country, HttpServletResponse response, HttpServletRequest request) {
        WikipediaDownloader wikipediaDownloader = new WikipediaDownloader(country);
        WikiResult x = wikipediaDownloader.getResult();

        modelMap.addAttribute("IMAGE", x.getImage_url());
        modelMap.addAttribute("DESCRIPTION", x.getText());

        return "wikiApi";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/music")
    public String getMusic(ModelMap modelMap, @RequestParam("song") String song, HttpServletResponse response, HttpServletRequest request) {
        SongsFetcher songsFetcher = new SongsFetcher(song);
        SongResult x = songsFetcher.getResult();
        modelMap.addAttribute("IMAGE", x.getImage_url());
        modelMap.addAttribute("TITLE", x.getTitle());
        modelMap.addAttribute("SOURCE", x.getSource_link());
        modelMap.addAttribute("DOWNLOAD", x.getDownload_link());
        return "music";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/song")
    public String getSong(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "songs";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download")
    public String getDownload(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "download";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public
    String uiTest(ModelMap model) {
        return "welcome";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/handle")
    public @ResponseBody String handleEncrypt(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        return "ok";
    }

}