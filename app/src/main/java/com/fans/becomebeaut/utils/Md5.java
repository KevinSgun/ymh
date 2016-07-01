package com.fans.becomebeaut.utils;

import java.security.MessageDigest;

/**
 * Created by shiyitan on 16/5/5.
 */
public class Md5 {

    public static String encrypt16(String s)
    {
        String result = "";
        if (null == s || s.trim().length() < 0)
        {
            return result;
        }

        try
        {
            MessageDigest mDigest = MessageDigest.getInstance("md5");
            mDigest.update(s.getBytes());
            byte b[] = mDigest.digest();
            int i;
            StringBuilder buffer = new StringBuilder(16);

            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
            result = buffer.substring(8, 24);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static String encrypt32(String s)
    {
        String result = "";
        if (null == s || s.trim().length() < 0)
        {
            return result;
        }
        try
        {
            MessageDigest mDigest = MessageDigest.getInstance("md5");
            mDigest.update(s.getBytes());
            byte b[] = mDigest.digest();
            int i;
            StringBuilder buffer = new StringBuilder(32);

            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
            result = buffer.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
