package com.lifetech.dhap.pathcloud.security;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by HP on 2016/7/11.
 */
public class BodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = readBytes(request.getInputStream(),request.getContentLength());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), "UTF-8"));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;

            int readLengthThisTime = 0;

            byte[] message = new byte[contentLen];

            try {

                while (readLen != contentLen) {

                    readLengthThisTime = is.read(message, readLen, contentLen
                            - readLen);

                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }

                    readLen += readLengthThisTime;
                }

                return message;
            } catch (IOException e) {
                // Ignore
                // e.printStackTrace();
            }
        }

        return new byte[] {};
    }
}
