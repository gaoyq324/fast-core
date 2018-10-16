package cn.ey.gds.ita.fast.core.controller;


import cn.ey.gds.ita.fast.core.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * rest接口表示
 */
@RestController
@RequestMapping("/rest")
public class SandboxController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping(value = "/list", name = "接口表示")
    public R queryOrderData() throws Exception {

        Map map = this.handlerMapping.getHandlerMethods();
        Iterator<?> iterator = map.entrySet().iterator();

        List<RequestUrlDto> urlDtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            RequestUrlDto requestUrlDto = new RequestUrlDto();

            //url
            RequestMappingInfo mappingInfo = (RequestMappingInfo) entry.getKey();
            //method
            HandlerMethod handlerMethod = (HandlerMethod) entry.getValue();
            Method method = handlerMethod.getMethod();
            //url
            requestUrlDto.setUrl(mappingInfo.getPatternsCondition().toString());
            //inputParam
            Class<?>[] parameterTypes = method.getParameterTypes();
            String inputParam = null;
            String inputParamDto = null;
            int i = 1;
            for (Class parameterType :
                    parameterTypes) {
                inputParam = "parameter" + i + ":" + parameterType.getSimpleName();
                inputParamDto = "parameter" + i + ":" + toJsonn(parameterType);
            }
            requestUrlDto.setInputParam(inputParam);
            requestUrlDto.setInputParamDto(inputParamDto);

            //returnParam
            Class<?> returnType = method.getReturnType();
            String returnParam = "Result:" + returnType.getSimpleName();
            String returnParamDto = "Result:" + toJsonn(returnType);
            requestUrlDto.setReturnParam(returnParam);
            requestUrlDto.setReturnParamDto(returnParamDto);

            //Desc
            if(mappingInfo.getName() == null) {
                requestUrlDto.setDesc("");
            }else{
                requestUrlDto.setDesc( mappingInfo.getName());
            }
            //ServiceName
            requestUrlDto.setServiceName(handlerMethod.getBeanType().getName());
            //BeanName
            requestUrlDto.setBeanName(handlerMethod.getBean().toString());
            //method
            requestUrlDto.setMethod(mappingInfo.getMethodsCondition().toString());

            urlDtoList.add(requestUrlDto);
        }


        return R.ok().put("result", urlDtoList);

    }

    private String toJsonn(Class clazz) throws Exception {
        if (clazz.getName().contains("cn.ey.gds.ita") ||
                clazz.getSimpleName().equals("R")) {
            String json = JSON.toJSONString(clazz.newInstance(), SerializerFeature.WriteMapNullValue);
            return clazz.getSimpleName() + ":" + json;
        } else {
            return clazz.getSimpleName();
        }
    }

}