package cn.beecloud;

public class BeeCloud {
    /**
     * BeeCloud SDK release version.
     */
    public static final String kBeeCloudVersionString = "2.3.1";
    public static final double kBeeCloudVersionNumber = 2.31;

    /**
     * 设置appID和appSecret，在程序开始时运行一遍即可
     *
     * @param appID 用户在比可网络中的应用标识符
     * @param appSecret 应用密码
     */
    public static void registerApp(String appID, String appSecret) {
    	
        BCCache.setAppID(appID);
        BCCache.setAppSecret(appSecret);
        BCAPIClient.initClient();
    }

    /**
     * 设置masterkey, 后续ACL需求预留
     *
     * @param masterKey masterkey
     */
    public static void setMasterKey(String masterKey) {
        BCCache.setMasterKey(masterKey);
    }

    /**
     * 设置网络超时时间, 单位ms, 默认5000
     *
     * @param timeout 访问比可网络的超时时间
     */
    public static void setNetworkTimeout(int timeout) {
        BCCache.setNetworkTimeout(timeout);
    }

    /**
     * 设置是否需要本地缓存云端数据及元数据, 默认为true
     *
     * @param needLocalCache 是否本地缓存
     */
    public static void setNeedCache(boolean needLocalCache) {
        BCCache.setNeedLocalCache(needLocalCache);
    }

    /**
     * 清除所有本地缓存
     */
    public static void clearAllCache() {
    }
}
