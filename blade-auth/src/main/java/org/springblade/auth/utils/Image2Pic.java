//package org.springblade.auth.utils;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.geom.Ellipse2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.net.URL;
//
//import javax.imageio.ImageIO;
//
//public class Image2Pic {
////	public static void main(String[] args) {
////        String backgroundPath = "G:\\书法\\2_slices\\bg2.png";
////        String qrCodePath = "G:\\书法\\2_slices\\erw.png";
////        String headUrl = "https://thirdwx.qlogo.cn/mmopen/vi_32/akib86ia17pOicRFy2roiasx6Hf4Irdm72kIMYSyvPAZiaQGGn0qshW9ZK0NNo6s844cnPTzCrxu8arFsT231znee3A/132";
////        String message01 ="扫描下方二维码，欢迎大家添加我的淘宝返利机器人";
////        String message02 = "居家必备，省钱购物专属小秘书！";
////        String outPutPath="E:\\end.png";
//////        overlapImage(backgroundPath,headUrl,qrCodePath,message01,message02,outPutPath);
////    }
//
//    public static String overlapImage(String backgroundPath,String headUrl,String qrCodePath,String outPutPath){
//        try {
//            //设置图片大小
//            BufferedImage background = resizeImage(563,1000, ImageIO.read(new File(backgroundPath)));
//            BufferedImage qrCode = resizeImage(110,110,ImageIO.read(new File(qrCodePath)));
//            BufferedImage headImg = resizeImage(50,50,ImageIO.read(new URL(headUrl)));
//
//
//            //在背景图片中添加入需要写入的信息，例如：扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！
//            //String message = "扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！";
//            Graphics2D g = background.createGraphics();
//            g.setColor(Color.white);
//            g.setFont(new Font("微软雅黑",Font.BOLD,20));
//            Ellipse2D.Double shape = new Ellipse2D.Double(18, 18, headImg.getWidth(),headImg.getHeight());
//            g.clip(shape);
//
//            g.drawImage(headImg, 18, 18, headImg.getWidth(), headImg.getHeight(), null);
//          //  ghead.dispose();
//            //在背景图片上添加二维码图片
//            g = background.createGraphics();
//            g.drawImage(qrCode, 370, 835, qrCode.getWidth(), qrCode.getHeight(), null);
//            g.dispose();
//            ImageIO.write(background, "png", new File(outPutPath));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "success";
//    }
//
//    public static BufferedImage resizeImage(int x, int y, BufferedImage bfi){
//        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
//        bufferedImage.getGraphics().drawImage(
//                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
//        return bufferedImage;
//    }
//
//}
//
