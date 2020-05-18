package com.dreanei.communion.server;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

/**
 * Created by Александр on 16.05.2017.
 */

public class VkAPI {

    private static VkAPI instance;
    static {
        instance = new VkAPI();
    }

    private VkAPI(){};

    public static VkAPI getInstance(){
        return instance;
    }

    public void getPhoto(VKRequest.VKRequestListener vkRequestListener){
        VKRequest request2 = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max"));
        request2.executeWithListener(vkRequestListener);
    }
}
