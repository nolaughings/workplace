package com.ruoyi.plugs.thirdlogin.wx;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.thirdlogin.BaseThirdLoginController;
import com.ruoyi.plugs.thirdlogin.domain.ThirdOauth;
import com.ruoyi.plugs.thirdlogin.domain.ThirdPartyUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
public class WxThirdLoginController extends BaseThirdLoginController {

    @Value("${third.login.wx.authorize_url}")
    public  String  AUTHORIZE_URL;
    @Value("${third.login.wx.client_id}")
    public  String CLIENT_ID;
    @Value("${third.login.wx.client_secret}")
    public  String CLIENT_SECRET;
    @Value("${third.login.wx.redirect_uri}")
    public  String REDIRECT_URL;
    @Value("${third.login.wx.scope}")
    public  String SCOPE;

    @GetMapping(value = "/thirdLogin/wx")
    public void wx(HttpServletRequest request, HttpServletResponse response){
        String authorizeUrl = getRedirectUrl(request);
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('"+authorizeUrl+"','_top')");
            out.println("</script>");
            out.println("</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "/thirdLogin/wx/callback")
    public String wxCallback(HttpServletRequest request) throws Exception {
        String host = request.getHeader("host");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String redirect = request.getParameter("redirect");
        if(StringUtils.isEmpty(code)){
            return "redirect:/thirdLogin/wx";
        }
        //获取用户AccessToken
        AjaxResult ajaxResult = this.getWxTokenAndOpenid(code,host);
        if(ajaxResult.isSuccess()){
            JSONObject jsonObject=(JSONObject)ajaxResult.get("data");
            String access_token = jsonObject.getString("access_token");
            if(StringUtils.isEmpty(access_token)){
                System.out.println("==>/thirdLogin/wx/callback=======获得access_token为空!");
                return "redirect:/thirdLogin/wx";
            }
            String openid=jsonObject.getString("openid");
            //获取用户
            ThirdPartyUser thirdPartyUser = this.getWxUserinfo(access_token,openid);
            if(thirdPartyUser!=null){
                thirdPartyUser.setAccessToken(access_token);
                //查询第三方登录
                ThirdOauth form=new ThirdOauth();
                form.setLoginType("wx");
                form.setOpenid(thirdPartyUser.getOpenid());
                List<ThirdOauth> list=thirdOauthService.selectThirdOauthList(form);
                if(CollectionUtils.isEmpty(list)){
                    //未绑定平台用户，去引导绑定用户
                    if(StringUtils.isEmpty(BIND_URL)){
                        //如果为空，自动绑定用户
                        //SysUser user = registThirdUser(thirdPartyUser);
                        SysUser user=null;//TODO
                        insertThirdOauth(thirdPartyUser,user.getUserId().toString());
                        list=thirdOauthService.selectThirdOauthList(form);
                    }else{
                        //跳转到绑定用户页面
                        String url=BIND_URL+"?type=wx&openid="+thirdPartyUser.getOpenid()+"&successUri="+redirect+"&thirdAccount="+thirdPartyUser.getAccount();
                        request.getSession().setAttribute("access_token",access_token);
                        return "redirect:"+url;
                    }
                }
                //已经绑定过
                //登录
                ThirdOauth thirdOauth=list.get(0);
                String userId=thirdOauth.getUserId();//本平台的用户ID
                AjaxResult ajaxResult1 = noPwdLoginService.loginNoPwd(Long.valueOf(userId));
                if(ajaxResult1.isSuccess()){
                    //返回
                    if(StringUtils.isEmpty(redirect)){
                        return "redirect:/";
                    }else{
                        return "redirect:"+redirect;
                    }
                }else{
                    return "redirect:/thirdLogin/wx";
                }
            }
            System.out.println("==>/thirdLogin/wx/callback=======获得ThirdPartyUser为空!");
            return "redirect:/thirdLogin/wx";
        }
        return "redirect:/thirdLogin/wx";
    }

    @GetMapping(value = "/thirdLogin/bind/wx")
    public String bind(HttpServletRequest request, ModelMap modelMap) {

        String openid=request.getParameter("openid");
        String successUri=request.getParameter("successUri");
        String thirdAccount=request.getParameter("thirdAccount");
        if(StringUtils.isNotEmpty(openid)){
            modelMap.addAttribute("type","wx");
            modelMap.addAttribute("openid",openid);
            modelMap.addAttribute("successUri",successUri);
            modelMap.addAttribute("thirdAccount",thirdAccount);
        }else{
            modelMap.addAttribute("errMsg","绑定账号参数异常!");
        }
        return "thirdlogin/bind";
    }
    @PostMapping(value = "/thirdLogin/bindSave/wx")
    @ResponseBody
    public AjaxResult bindSave(HttpServletRequest request) {
        String openid=request.getParameter("openid");
        String account=request.getParameter("account");
        String pwd=request.getParameter("pwd");
        String thirdAccount=request.getParameter("thirdAccount");
        if(StringUtils.isEmpty(account)){
            return AjaxResult.error("绑定账户名不能为空!");
        }
        if(StringUtils.isEmpty(pwd)){
            return AjaxResult.error("绑定账户密码不能为空!");
        }
        if(StringUtils.isEmpty(openid)){
            return AjaxResult.error("绑定账户参数异常!");
        }
        String access_token=String.valueOf(request.getSession().getAttribute("access_token"));
        SysUser user=sysLoginServicePlug.login(account,pwd,false);
        user.setThirdAccount(thirdAccount);//设置第三方登录账号
        ThirdPartyUser thirdPartyUser=new ThirdPartyUser();
        thirdPartyUser.setOpenid(openid);
        thirdPartyUser.setProvider("wx");
        if(StringUtils.isNotEmpty(access_token)){
            thirdPartyUser.setAccessToken(access_token);
        }
        int n=insertThirdOauth(thirdPartyUser,user.getUserId().toString());
        if(n>0){
            bindSaveCallBack(user);
        }
        AjaxResult result= noPwdLoginService.loginNoPwd(user.getUserId());
        if(result.isSuccess()){
            return AjaxResult.success("绑定成功,正在进行跳转!");
        }else{
            return AjaxResult.error("绑定成功,但登陆失败!");
        }

    }
    @RequestMapping(value = "/thirdLogin/unbind/wx")
    @ResponseBody
    public AjaxResult unbind(HttpServletRequest request, HttpServletResponse response) {
        SysUser user= ShiroUtils.getSysUser();
        if(user!=null){
            String userId=user.getUserId().toString();
            int n = thirdOauthService.deleteThirdOauthByUserIdAndLoginType(userId,"wx");
            if(n>0){
                return AjaxResult.success("解绑账号成功!");
            }
        }
        return error();
    }

    /**
     * 获取微信的认证token和用户OpenID
     *
     * @param code
     * @param host
     * @return
     */
    public AjaxResult getWxTokenAndOpenid(String code, String host) throws Exception {


        Map<String, String> map = new HashMap<String, String>();
        String redirectUrl="http://"+host+REDIRECT_URL;
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type" , "authorization_code"));
        params.add(new BasicNameValuePair("appid" , CLIENT_ID));
        params.add(new BasicNameValuePair("secret" , CLIENT_SECRET));
        params.add(new BasicNameValuePair("code" , code));
        params.add(new BasicNameValuePair("redirect_uri",redirectUrl));//?
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String result = httpPost(url,params);
        if(StringUtils.isNotEmpty(result)&&result.contains("access_token")){
            JSONObject jsonObject=JSONObject.parseObject(result);
            String openIdUrl ="https://api.weixin.qq.com/sns/userinfo";
            List<NameValuePair> openParams = new ArrayList<>();
            openParams.add(new BasicNameValuePair("access_token" , jsonObject.getString("access_token")));
            result=httpPost(openIdUrl,openParams);
            if(result.contains("(")&&result.contains(")")){
                int i = result.indexOf("(");
                int j = result.indexOf(")");
                result = result.substring(i + 1, j);
            }
            JSONObject openidObj = JSONObject.parseObject(result);
            map.put("access_token",jsonObject.getString("access_token"));
            map.put("openid",openidObj.getString("openid"));
            return AjaxResult.success(map);
        }
        return AjaxResult.error("wx获取token失败!");

    }
    /** 获取微信用户信息 */
    public  ThirdPartyUser getWxUserinfo(String token, String openid) throws Exception {
        ThirdPartyUser user = new ThirdPartyUser();
        user.setProvider("wx");
        String url = "https://api.weixin.qq.com/sns/userinfo";
        List<NameValuePair> openParams = new ArrayList<>();
        openParams.add(new BasicNameValuePair("access_token" , token));
        openParams.add(new BasicNameValuePair("openid" , openid));
        String result=httpPost(url,openParams);
        JSONObject json = JSONObject.parseObject(result);
        System.out.println(json.toJSONString());
        if (json.getString("errcode") == null) {
            user.setUserName(json.getString("nickname"));
            String img = json.getString("headimgurl");
            if (img != null && !"".equals(img)) {
                user.setAvatarUrl(img);
            }
            String sex = json.getString("sex");
            if ("0".equals(sex)) {
                user.setGender("0");
            } else {
                user.setGender("1");
            }
        } else {
            throw new IllegalArgumentException(json.getString("errmsg"));
        }
        return user;
    }

    private String httpPost(String url,List<NameValuePair> params) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent" , "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String getRedirectUrl(HttpServletRequest request) {
        String url = "";
        String host = request.getHeader("host");
        String redirectUrl="http://"+host+REDIRECT_URL;
        String state=request.getParameter("state");
        String state_str= StringUtils.isEmpty(state)?"":"&state="+state;
        url=AUTHORIZE_URL.replaceAll("\\[client_id\\]",CLIENT_ID).replaceAll("\\[redirect_uri\\]",redirectUrl).replaceAll("\\[scope\\]",SCOPE)+state_str;
        return url;
    }

    @Override
    public void bindSaveCallBack(SysUser user) {

    }
}
