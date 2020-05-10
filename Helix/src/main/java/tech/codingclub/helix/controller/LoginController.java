package tech.codingclub.helix.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.LoginResponse;
import tech.codingclub.helix.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: rishabh
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public String welcome(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "welcomeLogin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/welcome")
    public @ResponseBody
    LoginResponse handleLogin(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        List<Member> x = (List<Member>) new GenericDB<Member>().getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, tech.codingclub.helix.tables.Member.MEMBER.EMAIL.eq(member.getEmail()).and(tech.codingclub.helix.tables.Member.MEMBER.PASSWORD.eq(member.getPassword())), 1);
        if(x != null && x.size() > 0) {
            Member memberTemp = x.get(0);
            memberTemp.role = "cm";
            ControllerUtils.setUserSession(request, memberTemp);
            return new LoginResponse(memberTemp.id, "Login Successfully", true);
        } else {
            return new LoginResponse(null, "Wrong Combination", false);
        }
    }

}