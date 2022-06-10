package com.ruoyi.spider.resolver;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.spider.config.SpiderConstants;
import com.ruoyi.spider.domain.SpiderConfig;
import com.ruoyi.spider.domain.SpiderField;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Map;

public class JsonResolver implements Resolver {
    @Override
    public void process(Page page, SpiderConfig spiderConfig) {
        List<SpiderField> fields = spiderConfig.getFieldsList();

        String jsonStr=page.getRawText();
        Map<String, Object> map=JSON.parseObject(jsonStr, Map.class);
        /*if(CollectionUtils.isEmpty(fields)){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                page.putField(entry.getKey(),entry.getValue());
            }
        }else{
            for (SpiderField field : fields) {
                String column=field.getField();
                String type = field.getExtractType();
                if (type.equals(SpiderConstants.FIELD_EXTRACT_TYPE_CONSTANT)) {
                    page.putField(column,field.getConstantValue());
                } else {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if(entry.getKey().equals(column)){
                            page.putField(entry.getKey(),entry.getValue());
                            break;
                        }
                    }
                }
            }
        }*/
        for (SpiderField field : fields) {
            if (SpiderConstants.FIELD_EXTRACT_TYPE_CONSTANT.equals(field.getExtractType())) {
                page.putField(field.getField(),field.getConstantValue());
            }
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            page.putField(entry.getKey(),entry.getValue());
        }

    }
}
