package com.example.videotest.controller;

import com.example.videotest.util.Signature;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@RestController
public class VideoController
{

    @RequestMapping("/plus")
    public ModelAndView plus()
    {
        return new ModelAndView("/video-plus.html");
    }

    @RequestMapping("/")
    public ModelAndView index()
    {
        return new ModelAndView("/video.html");
    }

    @RequestMapping("/original")
    public ModelAndView original()
    {
        return new ModelAndView("/video_backup.html");
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String getSign()
    {
        //得到Sign
        Signature sign = new Signature();
        //个人API密钥中的Secret Id
        sign.setSecretId("xxx");
        //个人API密钥中的Secret Key
        sign.setSecretKey("xxx");
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2);

        String signature = null;
        try
        {
            signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
        }
        catch (Exception e)
        {
            System.out.print("获取签名失败");
            e.printStackTrace();
        }

        return signature;
    }

}
