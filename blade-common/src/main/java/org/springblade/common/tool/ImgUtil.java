package org.springblade.common.tool;

//import org.springblade.common.vo.BASE64DecodedMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class ImgUtil {

    /**
     * 将MultipartFile 图片文件编码为base64
     *
     * @param file
     * @return
     */
    public static String generateBase64(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("图片不能为空！");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String contentType = file.getContentType();
        byte[] imageBytes = null;
        String base64EncoderImg = "";
        try {
            imageBytes = file.getBytes();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            /**
             * 1.Java使用BASE64Encoder 需要添加图片头（"data:" + contentType + ";base64,"），
             *   其中contentType是文件的内容格式。
             * 2.Java中在使用BASE64Enconder().encode()会出现字符串换行问题，这是因为RFC 822中规定，
             *   每72个字符中加一个换行符号，这样会造成在使用base64字符串时出现问题，
             *   所以我们在使用时要先用replaceAll("[\\s*\t\n\r]", "")解决换行的问题。
             */
//            base64EncoderImg = "data:" + contentType + ";base64," + base64Encoder.encode(imageBytes);
            base64EncoderImg = base64Encoder.encode(imageBytes);
            base64EncoderImg = base64EncoderImg.replaceAll("[\\s*\t\n\r]", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64EncoderImg;
    }

//    public static MultipartFile base64ToMultipart(String base64) {
//        try {
//            String[] baseStr = base64.split(",");
//
//            BASE64Decoder decoder = new BASE64Decoder();
//            byte[] b = new byte[0];
//            b = decoder.decodeBuffer(baseStr[1]);
//
//            for(int i = 0; i < b.length; ++i) {
//                if (b[i] < 0) {
//                    b[i] += 256;
//                }
//            }
//
//            return new BASE64DecodedMultipartFile(b, baseStr[0]);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
