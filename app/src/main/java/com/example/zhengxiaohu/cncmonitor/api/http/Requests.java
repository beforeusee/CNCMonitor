package com.example.zhengxiaohu.cncmonitor.api.http;

import android.util.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by XiaoHu Zheng on 2017/5/20.
 * Email: 1050087728@qq.com
 * the http request
 */

public class Requests {
    private static String POST="POST";
    private static String GET="GET";
    private static final String TAG = "Requests";

    /**
     * the method of http requestMethod is post.
     * @param url the address of http request
     * @param postDatas the data of post
     * @return result of request
     */
    public static String post(String url,PostData[] postDatas){
        return runRequest(url,postDatas,POST,3);
    }

    /**
     * the method of http requestMethod is get
     * @param url the address of http request
     * @return result of http request
     */
    public static String get(String url){
        return runRequest(url,null,GET,3);
    }

    /**
     * get the InputStream
     * @param url the request address of the http
     * @return result of the http request ,InputStream
     */
    public static InputStream getInputStream(String url){
        return runGetInputStream(url,3);
    }

    /**
     * get the inputStream result document
     * @param url address of the http request
     * @return document
     */
    public static Document getResult(String url){
        return runGetDocument(url,3);
    }
    /**
     * run getInputStream request
     * @param url address
     * @param maxAttempts max times to request connection
     * @return the InputStream
     */
    private static InputStream runGetInputStream(String url,int maxAttempts){
        InputStream inputStream=null;
        HttpURLConnection connection=null;
        Boolean success=false;
        int i=0;
        while (!success&&i<maxAttempts){
            try{
                //Open Connection
                URL url1=new URL(url);
                connection=getConnection(url,GET);
                if (connection==null){
                    Log.d(TAG, "runGetInputStream: connection is null");
                }
                if (connection!=null){
                    //read stream
                    inputStream=connection.getInputStream();

                    Log.d(TAG,"runGetInputStream: inputStream is null? "+String.valueOf(inputStream==null));
                    //connect success,set flag success to true
                    success=true;
                }
            } catch (IOException e) {
                Log.d(TAG, "runGetInputStream: IOException happened");
                e.printStackTrace();
            } finally {
                if (connection!=null){
                    //disconnect the connection
                    connection.disconnect();
                }
            }

            //Delay a little before next request attempt
            if (!success){
                try{
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
        Log.d(TAG,"runGetInputStream: inputStream is null? "+String.valueOf(inputStream==null));
        return inputStream;
    }

    private static Document runGetDocument(String url, int maxAttempts){
        InputStream inputStream=null;
        HttpURLConnection connection=null;
        Document document=null;

        Boolean success=false;
        int i=0;
        while (!success&&i<maxAttempts){
            try{
                //Open Connection
                URL url1=new URL(url);
                connection=getConnection(url,GET);
                if (connection==null){
                    Log.d(TAG, "runGetDocument: connection is null");
                }
                if (connection!=null){
                    //read stream
                    inputStream=connection.getInputStream();

                    SAXReader saxReader=new SAXReader();
                    document=saxReader.read(inputStream);

                    Log.d(TAG,"runGetInputStream: inputStream is null ? "+String.valueOf(inputStream==null));
                    //connect success,set flag success to true
                    success=true;
                }
            } catch (IOException e) {
                Log.d(TAG, "runGetInputStream: IOException happened");
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null){
                    //disconnect the connection
                    connection.disconnect();
                }
            }

            //Delay a little before next request attempt
            if (!success){
                try{
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
        Log.d(TAG, "runGetDocument: document is null ? "+String.valueOf(document==null));
        return document;
    }
    /**
     * run http request
     * @param url address
     * @param postDatas post data
     * @param method the method of requestMethod,including "POST" and "GET"
     * @param maxAttempts the max times of reconnect to the url.
     * @return the result of inputStream
     */
    private static String runRequest(String url, PostData[] postDatas, String method, int maxAttempts) {

        String result=null;

        //Format PostData array
        String postData=formatPostData(postDatas);

        InputStream inputStream=null;

        Boolean success=false;
        int i=0;
        while (!success&&i<maxAttempts){
            try{
                HttpURLConnection connection=getConnection(url,method);
                if (connection!=null){
                    //if postData!=null，post data
                    if (postData!=null){
                        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(connection.getOutputStream());

                        outputStreamWriter.write(postData);
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                    }

                    //read stream
                    inputStream=connection.getInputStream();
                    result=readTextStream(inputStream);

                    //disconnect the connection
                    connection.disconnect();
                    //connect success,set flag success to true
                    success=true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //delay a little before next request attempt
            if (!success){
                try{
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return result;
    }

    /**
     *  change the inputStream to String
     * @param inputStream inputStream from the http request
     * @return result of inputStream
     * @throws IOException
     */
    private static String readTextStream(InputStream inputStream) throws IOException {

        String result=null;
        if (inputStream!=null){
            InputStreamReader streamReader=new InputStreamReader(inputStream);
            BufferedReader reader=new BufferedReader(streamReader);
            StringBuilder stringBuilder=new StringBuilder();
            String line=null;

            //Build response string
            while ((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            result=stringBuilder.toString();

            //close IO Readers
            reader.close();
            streamReader.close();
        }
        return result;
    }

    /**
     * http连接
     * @param url URL address
     * @param method requestMethod of connection
     * @return HttpURLConnection obj connection;
     */
    private static HttpURLConnection getConnection(String url, String method) {

        try{
            //Open Connection
            URL url1=new URL(url);

            HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
            if (connection==null){
                Log.d(TAG, "getConnection: connect failed.");
                return null;
            }
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setRequestMethod(method);

            connection.setDoInput(true);
            connection.setDoOutput(method.equals(POST));
            connection.connect();
            Log.d(TAG, "getConnection: connect the MTConnect agent http server success.");
            return connection;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化PostData的数组对象，各个元素用&连接
     * @param postDatas PostData数组对象
     * @return 返回格式化后的postData
     */
    private static String formatPostData(PostData[] postDatas) {
        String result=null;
        if (postDatas!=null){
            result="";
            for (int i=0;i<postDatas.length;i++){
                result+=encodePostData(postDatas[i]);
                if (i<postDatas.length-1){
                    result+="&";
                }
            }
        }
        return result;
    }

    /**
     * 编码PostData的实例
     * @param postData PostData的实例
     * @return 返回编码后的结果
     */
    private static String encodePostData(PostData postData) {
        String result=null;
        try{
            String id=postData.id;
            String value=postData.value;
            result= URLEncoder.encode(id,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
