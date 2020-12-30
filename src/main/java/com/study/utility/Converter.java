package com.study.utility;

import com.study.projectjeju.vos.UserVo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Converter {
    private Converter() {}

    public static String imageToString(MultipartFile image) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:image/png;base64,");
        stringBuilder.append(StringUtils.newStringUtf8(Base64.encodeBase64(image.getBytes())));       // apache commons codec (mvn dependency add)
        return stringBuilder.toString();
    }

    public static int stringToInt(String idText, int fallback) {
        try {
            return Integer.parseInt(idText);
        } catch (Exception ignored) {
            return fallback;
        }
    }

    public static UserVo getUserVo(HttpServletRequest request) {
        Object userVoObject = request.getSession().getAttribute("UserVo");
        UserVo userVo = null;
        if (userVoObject instanceof UserVo) {
            userVo = (UserVo) userVoObject;
        }
        return userVo;
    }
}
