package com.fans.becomebeaut.api.request;

import com.fans.becomebeaut.utils.Md5;
import com.google.gson.Gson;
import com.zitech.framework.data.network.entity.Global;
import com.zitech.framework.data.network.request.ApiRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class Request extends ApiRequest {
    private Global global = new Global();
    private RequestData data;
    private transient String method;

    public Request(RequestData data) {
        this.data = data;
    }

    // 它签名的类容就是query生成的json+一个固定的字符串生成的,在JsonParamsBuilder类getPostBody方法中调用
    public void sign() {
        if (global != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {

                mapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY,true);
                String Json;
                if(data!=null)
                    Json =  mapper.writeValueAsString(data);//bean转换成json,会触发Global中的 getToken()方法
                else
                    Json = new Gson().toJson(new Object());
                global.setSign(Md5.encrypt32("@)#($*%&^~!"+Json).toUpperCase());

            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public RequestData getData() {
        return data;
    }

    public void setData(RequestData data) {
        this.data = data;
    }

}
