package com.example.videotest.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created with IntelliJ IDEA.
 * Author: zky
 * Date: 2020-10-30
 * Time: 11:28:40
 * Description:
 */
public class Signature
{
    private String secretId;
    private String secretKey;
    private long currentTime;
    private int random;
    private int signValidDuration;
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";

    public static byte[] byteMerger(byte[] byte1, byte[] byte2)
    {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

    public String getUploadSignature() throws Exception
    {
        String strSign = "";
        String contextStr = "";

        long endTime = this.currentTime + this.signValidDuration;
        contextStr = contextStr + "secretId=" + URLEncoder.encode(this.secretId, "utf8");
        contextStr = contextStr + "&currentTimeStamp=" + this.currentTime;
        contextStr = contextStr + "&expireTime=" + endTime;
        contextStr = contextStr + "&random=" + this.random;
        contextStr = contextStr + "&procedure=QCVB_SimpleProcessFile(1,1)";
        try
        {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secretKey);
            byte[] hash = mac.doFinal(contextStr.getBytes("UTF-8"));
            byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
            strSign = new String(Base64.getEncoder().encode(sigBuf));
            strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        }
        catch (Exception e)
        {
            throw e;
        }
        return strSign;
    }

    public void setSecretId(String secretId)
    {
        this.secretId = secretId;
    }

    public void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    public void setCurrentTime(long currentTime)
    {
        this.currentTime = currentTime;
    }

    public void setRandom(int random)
    {
        this.random = random;
    }

    public void setSignValidDuration(int signValidDuration)
    {
        this.signValidDuration = signValidDuration;
    }

}
