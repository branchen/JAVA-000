package io.github.kimmking.gateway.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author bran.chen
 * @description
 */
public class HttpClientOutboundHandler {
    private String backendUrl;

    public HttpClientOutboundHandler(String backendUrl) {
        if (backendUrl.endsWith("/")) {
            backendUrl = backendUrl.substring(0, backendUrl.length() - 1);
        }
        this.backendUrl = backendUrl;
    }

    public void handle(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        String url = this.backendUrl + fullHttpRequest.uri();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        for (Map.Entry<String, String> header : fullHttpRequest.headers()) {
            httpGet.addHeader(header.getKey(),header.getValue());
        }

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            handleResponse(response, fullHttpRequest, ctx);
//            HttpEntity entity = response.getEntity();
//            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleResponse(CloseableHttpResponse response, FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        DefaultFullHttpResponse defaultHttpResponse=null;
        try {
            byte[] body = EntityUtils.toByteArray(response.getEntity());
            defaultHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(body));
            defaultHttpResponse.headers().set("Content-Type", "application/json");
            defaultHttpResponse.headers().setInt("Content-Length", Integer.parseInt(response.getFirstHeader("Content-Length").getValue()));
        } catch (IOException e) {
            e.printStackTrace();
            defaultHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(defaultHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(defaultHttpResponse);
                }
            }
            ctx.flush();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
