package cn.beecloud;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author Ray
 * Date: 15/7/08
 */
public class BCCache {
	
    private static String appID = null;
    private static String appSecret = null;
    private static String masterKey = null;
    private static boolean needLocalCache = true;
    private static int networkTimeout = 500000;
    public static String[] apiHostArray = initApiHostArray();


    static void setAppSecret(String appSecret) {
        BCCache.appSecret = appSecret;
    }

    static void setAppID(String appID) {
        BCCache.appID = appID;
    }

    static String getMasterKey() {
        return masterKey;
    }

    static void setMasterKey(String masterKey) {
        BCCache.masterKey = masterKey;
    }

    static boolean isNeedLocalCache() {
        return needLocalCache;
    }

    static void setNeedLocalCache(boolean needLocalCache) {
        BCCache.needLocalCache = needLocalCache;
    }

    static int getNetworkTimeout() {
        return networkTimeout;
    }

    static void setNetworkTimeout(int networkTimeout) {
        BCCache.networkTimeout = networkTimeout;
    }
    
    static String[] initApiHostArray()
    {
    	apiHostArray = new String[4];
    	
    	apiHostArray[0] = "https://apisz.beecloud.cn";
    	apiHostArray[1] = "https://apiqd.beecloud.cn";
    	apiHostArray[2] = "https://apibj.beecloud.cn";
    	apiHostArray[3] = "https://apihz.beecloud.cn";
    	InputStream inputStream;
    	inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
    	if (inputStream != null) {
    		Properties prop = new Properties();
    		try {
				prop.load(inputStream);
				String host = prop.getProperty("backend");
				if (!host.trim().equals("")) {
					apiHostArray[0] = "http://" + host + ":8080";
					apiHostArray[1] = "http://" + host + ":8080";
					apiHostArray[2] = "http://" + host + ":8080";
					apiHostArray[3] = "http://" + host + ":8080";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    	}
    	return apiHostArray;
    	
    	
    }
    
    public static String getAppSecret() {
        return appSecret;
    }
    
    public static String getAppID() {
        return appID;
    }
}
