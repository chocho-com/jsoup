package com.chocho.jsoup.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//滚动截屏
//注意排除swagger的包，避免发生冲突
public class TakePageshot {
    public static void main(String[] args) throws IOException {
        //firefox
//		System.setProperty("webdriver.gecko.driver", "c://geckodriver.exe");
//		WebDriver webDriver = new FirefoxDriver();
        //chrome
        //引入驱动
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        System.setProperty("java.awt.headless", "true");
        ChromeOptions options = new ChromeOptions();
        //添加启动参数 无界面浏览器
        options.addArguments("--headless");
	//	options.addArguments("--start-maximized");
  //      options.addArguments("--start-fullscreen");
        options.setHeadless(true);
        ChromeDriver broswer = new ChromeDriver(options);
        //设置浏览器窗口大小
        int winHeight = 1000 ;
        int winWidth = 1280 ;
        Dimension dim = new Dimension(winWidth, winHeight);
        broswer.manage().window().setSize(dim);
        //等待5秒,
        broswer.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //打开url
        broswer.get("http://www.qq.com");//打开腾讯的网站
        //
        Object[] nil = new Object[] {};
        //获取页面的高度
        Long pageHeight = (Long)broswer.executeScript("return document.body.scrollHeight", nil);
        //
        if(pageHeight <= winHeight) { //只有一屏的页面
            File srcFile = ((TakesScreenshot)broswer).getScreenshotAs(OutputType.FILE);
            File f = new File("H:\\video_image\\" +srcFile.getName());
            FileUtils.copyFile(srcFile, f);
        }else { //多屏页面
            int pages = (pageHeight.intValue() / winHeight) + (pageHeight.intValue() % winHeight == 0 ? 0 : 1);
            //隐藏滚动条
            broswer.executeScript("document.body.style.overflow = \"hidden\"", nil);
            long scrollHeight = 0;
            //图片总高度
            int imageTotalHeight = 0 ;
            List<BufferedImage> images = new ArrayList<BufferedImage>();

            for(int i = 0 ; i < pages ; i ++) {
                //滚动
                broswer.executeScript("window.scrollTo(0 , "+ scrollHeight +")", nil);
                //截取当前屏幕图片
                File screenshotFile = ((TakesScreenshot)broswer).getScreenshotAs(OutputType.FILE);
                BufferedImage screenshot = ImageIO.read(screenshotFile);
                images.add(screenshot);
                //计算图片中的高度
                imageTotalHeight += screenshot.getHeight();
                //计算滚动条下次的位置
                scrollHeight += winHeight;
            }
            //
            BufferedImage pageshot = new BufferedImage(winWidth , imageTotalHeight , BufferedImage.TYPE_INT_RGB);
            Graphics2D gd2 = pageshot.createGraphics();
            //
            int offsetHeight = 0 ;
            //合并图片
            for (int i = 0; i < images.size(); i++) {
                BufferedImage image = images.get(i);
                gd2.drawImage(image, 0, offsetHeight, image.getWidth(), image.getHeight(), null);
                offsetHeight += image.getHeight();
            }
            //输出合并后的图片
            OutputStream output = new FileOutputStream(new File("H:\\video_image\\" + UUID.randomUUID().toString() + ".png"));
            ImageIO.write(pageshot, "png", output);
            //释放资源
            output.close();
            gd2.dispose();
        }
        //退出浏览器
        broswer.close();
        broswer.quit();
    }
}
