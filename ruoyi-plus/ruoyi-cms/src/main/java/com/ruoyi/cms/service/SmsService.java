package com.ruoyi.cms.service;

import com.ruoyi.cms.domain.CmsTemplate;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.plugs.third.sms.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsService {

    @Autowired
    private ICmsTemplateService templateService;

    public AjaxResult sendByTemplate(String templateCode, String phone, Map<String,String> params){
        CmsTemplate template = templateService.selectTemplateByCode(templateCode);
        if(template!=null){

            String content=template.getTemplateContent();

            for(Map.Entry<String, String> a:params.entrySet()){
                String key=a.getKey();
                String value=a.getValue();
                content=content.replaceAll(key,value);
            }

            AjaxResult result=SmsUtil.sendLeYunSms(phone,template.getTemplateName(),content);

            return result;
        }
        return null;
    }
}
