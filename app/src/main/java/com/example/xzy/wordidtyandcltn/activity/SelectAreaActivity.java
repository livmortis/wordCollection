package com.example.xzy.wordidtyandcltn.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xzy.wordidtyandcltn.R;
import com.example.xzy.wordidtyandcltn.entity.WordsInfo;
import com.example.xzy.wordidtyandcltn.util.Base64Help;
import com.example.xzy.wordidtyandcltn.util.PicUtils;
import com.example.xzy.wordidtyandcltn.util.UIUtility;
import com.example.xzy.wordidtyandcltn.view.CustomHScrollView;
import com.example.xzy.wordidtyandcltn.view.CustomScrollView;
import com.example.xzy.wordidtyandcltn.view.AreaView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by livmortis on 6/22/18.
 */

public class SelectAreaActivity extends BaseActivity implements View.OnClickListener {
    //labels
    private ImageView iviv;
    private TextView tvConfirm;
    private TextView tvDelete;
    private RelativeLayout rl;
    private RelativeLayout emptyToCemara;
    private ImageView emptyIv1;
    private ImageView emptyIv2;

    //view
    private CustomScrollView mCustomScrollView;
    private CustomHScrollView mCustomHScrollView;
    private AreaView mAreaView;

    //variables
    private byte[][] areaByte;
    private int mMotionWidth;                                //区域控件宽
    private int mMotionHeight;                                //区域控件高
    private int photoHeight = 0;    //照片的高度（最底部的像素坐标）
    private int photoWidth = 0;     //照片的宽度（最右侧的像素坐标）

    //constant
    private final static int TAKE_PHOTO_REQUEST = 0;
    private final static int ALBUM_IMAGE_REQUEST = 4;
    private final static int UPLOAD_SCCESS = 1;
    private final static int UPLOAD_FAILD = 2;
    private final static int RESPONSE_DATA = 3;

    //XunFei params
    private final static String APP_ID = "5b2e2319";
    private final static String API_KEY = "acdd7908b2d749bd7bd0b459003849cc"; //接口密钥，由讯飞开放平台提供
    private String curTime;
    private final static String xParam = "eyJsYW5ndWFnZSI6ImVuIiwibG9jYXRpb24iOiJ0cnVlIn0=";  //由{"language":"en","location":"true"}经过base64编码而来，表示"英语"，"需要位置信息"。

    //data
    private List<WordsInfo> wordsList = new ArrayList<>();

    //controller
    private boolean isFakeData = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_settings_area_settings);
        initview();
        initAreaView();
        initData();
    }

    private void initData() {

    }


    private void initview() {
//        rl = findViewById(R.id.window_layout);
        mCustomScrollView = findViewById(R.id.motion_vScroll);
        mCustomHScrollView = findViewById(R.id.motion_hScroll);
        mAreaView = findViewById(R.id.motion_areaView);
        iviv = findViewById(R.id.iv);
        emptyToCemara = findViewById(R.id.empty_goto_camera);
        tvConfirm = findViewById(R.id.area_confirm);
        tvDelete = findViewById(R.id.area_clear);
        tvDelete.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
//        emptyToCemara.setOnClickListener(this);

        mCustomScrollView.setVisibility(View.GONE); //默认展示空图片
        emptyToCemara.setVisibility(View.VISIBLE);

        emptyIv1 = findViewById(R.id.empty_iv);
        emptyIv2 = findViewById(R.id.empty_iv2);
        emptyIv1.setOnClickListener(this);
        emptyIv2.setOnClickListener(this);
    }


    private void initAreaView() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth;
        screenWidth = display.getWidth();
        Log.d("xzyPixel-screenWidth", screenWidth+"");
        mMotionWidth = screenWidth - UIUtility.dip2px(SelectAreaActivity.this, 30);
        Log.d("xzyPixel-motionWidth", mMotionWidth+"");
        mMotionHeight = mMotionWidth * 4 / 5;
        Log.d("xzyPixel-motionHeight", mMotionHeight+"");


        RelativeLayout winP = (RelativeLayout) findViewById(R.id.window_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mMotionWidth, mMotionHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        winP.setLayoutParams(params);

        mCustomScrollView.setLayoutParams(new RelativeLayout.LayoutParams(mMotionWidth, mMotionHeight));
        mCustomHScrollView.setLayoutParams(new FrameLayout.LayoutParams(mMotionWidth, mMotionHeight));
        iviv.setLayoutParams(new FrameLayout.LayoutParams(mMotionWidth, mMotionHeight));
        mAreaView.setLayoutParams(new FrameLayout.LayoutParams(mMotionWidth, mMotionHeight));
        mAreaView.setScrollView(mCustomScrollView, mCustomHScrollView);
        mAreaView.setImageView(iviv);
        mAreaView.setMode(AreaView.EDIT);


//        mCustomScrollView.setLayoutParams(new RelativeLayout.LayoutParams(990, 792));
//        mCustomHScrollView.setLayoutParams(new FrameLayout.LayoutParams(990, 792));
//        iviv.setLayoutParams(new FrameLayout.LayoutParams(660, 440));
//        mAreaView.setLayoutParams(new FrameLayout.LayoutParams(660, 440));

//
//        iviv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.america));
//        Log.d("xzyPixel-picWidth", iviv.getMaxWidth()+"");
//        Log.d("xzyPixel-picHeight", iviv.getMaxHeight()+"");

//        mCustomScrollView.setLayoutParams(new RelativeLayout.LayoutParams(iviv.getWidth(), iviv.getHeight()));
//        mCustomHScrollView.setLayoutParams(new FrameLayout.LayoutParams(iviv.getWidth(), iviv.getHeight()));
//        mAreaView.setLayoutParams(new FrameLayout.LayoutParams(iviv.getWidth(), iviv.getHeight()));


//        mAreaView.setScrollView(mCustomScrollView, mCustomHScrollView);
//        mAreaView.setImageView(iviv);
//        mAreaView.setMode(AreaView.EDIT);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case TAKE_PHOTO_REQUEST:    //相机返回
                if (data != null) {
//                    Bitmap photo = data.getParcelableExtra("data");
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    photoHeight = photo.getHeight();
//                    photoWidth = photo.getWidth();
//                    Log.d("xzyPxel-size", PicUtils.getBitmapBytes(photo) + "");
//                    Log.d("xzyPxel-pixelSize", PicUtils.getBitmapPixelSize(photo) + "");
//                    Log.d("xzyPxel-height", UIUtility.dip2px(this,photoHeight) + "");
//                    Log.d("xzyPxel-width", UIUtility.dip2px(this,photoWidth) + "");
                    String photoAfterB64 = Base64Help.bitmapToBase64(photo);
                    uploadPic(photoAfterB64);
                    iviv.setImageBitmap(photo);
                }
                break;

            case ALBUM_IMAGE_REQUEST:   //相册返回
                if (resultCode == RESULT_OK && data != null) {//resultcode是setResult里面设置的code值

                    try {
                        String path = "";
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);

                        String albumAfterB64 = Base64Help.imageToBase64(path);
                        uploadPic(albumAfterB64);
                        iviv.setImageBitmap(bitmap);


                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.area_confirm:
                if (mAreaView != null) {
                    areaByte = mAreaView.getAreas();
                    for(int i = 0 ; i < areaByte.length ; i++) {
                        for(int j = 0 ; j < areaByte[0].length ; j++) {
                            Log.d("xzyAreaByte", areaByte[i][j]+"");
                            collectWords();
                        }
                    }
                }
                break;
            case R.id.area_clear:
                if (mAreaView != null) {
                    if (areaByte != null && areaByte.length > 0)
                        mAreaView.clearAerea(areaByte);
                    else
                        mAreaView.clearAerea();
                }
                break;

            case R.id.empty_iv: //跳转相机
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,TAKE_PHOTO_REQUEST);
                break;
            case R.id.empty_iv2: //跳转相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_IMAGE_REQUEST);

                break;
        }

    }

    private void collectWords() {
        if (areaByte == null) {
            toast("请在图片上拖拽以选择识别区域");
            return;
        }
        if (wordsList.size() == 0) {
            toast("所选区域没有识别到单词");
        }

        for (int i =0 ; i < wordsList.size() ; i++) {
            WordsInfo wordInfo = wordsList.get(i);
            String word = wordInfo.getWord();
            int top_left_X = wordInfo.getTop_left_X();
            int top_left_Y = wordInfo.getTop_left_Y();
            int right_bottom_X = wordInfo.getRight_bottom_X();
            int right_bottom_Y = wordInfo.getRight_bottom_Y();

            int centerX = right_bottom_X - top_left_X;
            int centerY = right_bottom_Y - top_left_Y;

            Log.d("xzyPixel-photoH", photoHeight+"");
            Log.d("xzyPixel-photoW", photoWidth+"");
            Log.d("xzyPixel-centerX", centerX+"");
            Log.d("xzyPixel-centerY", centerY+"");
            Log.d("xzyPixel-word", word+"");




        }
    }


    private void uploadPic(String albumOrCameraAfterB64 ) {

        final String photoAfterB64 = albumOrCameraAfterB64;



        Log.d("xzyupload", "photoAfterB64 is   " + photoAfterB64);
        final String curtime = getCurTime();

        //检测base64编码是否成功
//        iviv.setImageBitmap(Base64Help.stringtoBitmap(photoAfterB64));
//        mCustomScrollView.setVisibility(View.VISIBLE);
//        emptyToCemara.setVisibility(View.GONE);


        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection urlConnection = null;
                Log.d("xzyupload", "begin upload .........");

                try {
                    URL url = null;
                    url = new URL("http://webapi.xfyun.cn/v1/service/v1/ocr/general");
                    urlConnection = (HttpURLConnection) url.openConnection();//打开http连接
                    urlConnection.setConnectTimeout(5000);//连接的超时时间
                    urlConnection.setUseCaches(false);//不使用缓存
                    //urlConnection.setFollowRedirects(false);是static函数，作用于所有的URLConnection对象。
                    urlConnection.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
                    urlConnection.setReadTimeout(5000);//响应的超时时间
                    urlConnection.setDoInput(true);//设置这个连接是否可以写入数据
                    urlConnection.setDoOutput(true);//设置这个连接是否可以输出数据
                    urlConnection.setRequestMethod("POST");//设置请求的方式
                    Log.d("xzyupload", 111111 + "");


                    //设置请求头

//            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//设置消息的类型
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");//设置消息的类型

                    urlConnection.setRequestProperty("X-Appid", APP_ID);
                    //设置讯飞开放平台上注册的应用appid
                    urlConnection.setRequestProperty("X-CurTime", curtime);
                    //当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数
                    urlConnection.setRequestProperty("X-Param", xParam);
                    //相关参数JSON串经Base64编码后的字符串，见各接口详细说明
                    urlConnection.setRequestProperty("X-CheckSum", UIUtility.md5(API_KEY + curtime + xParam));
                    //令牌，计算方法：MD5(apiKey + curTime + param)，三个值拼接的字符串，进行MD5哈希计算（32位小写），其中apiKey由讯飞提供，调用方管理。
                    Log.d("xzyupload", 2222222 + "");


//                    urlConnection.connect();// 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
//                    JSONObject json = new JSONObject();//创建json对象
//                    json.put("image", URLEncoder.encode(photoAfterB64, "UTF-8"));//使用URLEncoder.encode对特殊和不可见字符进行编码
//
//                    String jsonstr = json.toString();//把JSON对象按JSON的编码格式转换为字符串




                    //-------------使用字节流发送数据--------------
                    //OutputStream out = urlConnection.getOutputStream();
                    //BufferedOutputStream bos = new BufferedOutputStream(out);//缓冲字节流包装字节流
                    //byte[] bytes = jsonstr.getBytes("UTF-8");//把字符串转化为字节数组
                    //bos.write(bytes);//把这个字节数组的数据写入缓冲区中
                    //bos.flush();//刷新缓冲区，发送数据
                    //out.close();
                    //bos.close();

                    //------------字符流写入数据，wordCollection更换------------
//                    OutputStream out = urlConnection.getOutputStream();//输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
//                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));//创建字符流对象并用高效缓冲流包装它，便获得最高的效率,发送的是字符串推荐用字符流，其它数据就用字节流
//                    bw.write(jsonstr);//把json字符串写入缓冲区中
//                    bw.flush();//刷新缓冲区，把数据发送出去，这步很重要
//                    out.close();
//                    bw.close();//使用完关闭
                    Log.d("xzyupload", 3333333 + "");


                    //-------demo使用 ---------
                    PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                    out.print("image=" + photoAfterB64);
                    out.flush();





//                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {//得到服务端的返回码是否连接成功
                        //------------字节流读取服务端返回的数据------------
                        //InputStream in = urlConnection.getInputStream();//用输入流接收服务端返回的回应数据
                        //BufferedInputStream bis = new BufferedInputStream(in);//高效缓冲流包装它，这里用的是字节流来读取数据的，当然也可以用字符流
                        //byte[] b = new byte[1024];
                        //int len = -1;
                        //StringBuffer buffer = new StringBuffer();//用来接收数据的StringBuffer对象
                        //while((len=bis.read(b))!=-1){
                        //buffer.append(new String(b, 0, len));//把读取到的字节数组转化为字符串
                        //}
                        //in.close();
                        //bis.close();
                        //Log.d("xzy", buffer.toString());//{"json":true}
                        //JSONObject rjson = new JSONObject(buffer.toString());//把返回来的json编码格式的字符串数据转化成json对象
                        //------------字符流读取服务端返回的数据------------
                        Log.d("xzyupload", "3.53.5.35.3.5");

                        InputStream in = urlConnection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String str = null;
                        StringBuffer buffer = new StringBuffer();
                        while ((str = br.readLine()) != null) {//BufferedReader特有功能，一次读取一行数据
                            buffer.append(str);
                        }
                        in.close();
                        br.close();
                        String result = buffer.toString();
                        Log.d("xzyupload", 4444444 + "");


                        //假数据
                        if (isFakeData) {
                            result = "{code:0,data:{block:[{line:[{confidence: 1," +
                                    "word: [{content: I'm,location: {right_bottom: {y: 28,x: " +
                                    "18},top_left: {y: 0,x: 0}}}],location: {right_bottom: {y: 28,x: 478}," +
                                    "top_left: {y: 0,x: 0}}}],type: text}]},sid: wcr00000009@ch0fc40d9e4cdf000100," +
                                    "desc: success}";
                        }
                        Log.d("xzyupload", 5555555 + "");


                        JSONObject rjson = new JSONObject(result);
                        Log.d("xzyupload", "rjson=" + rjson);//rjson={"json":true}

                        int resCode = rjson.getInt("code");     //解析code字段
                        if (resCode != 0) {
                            mHandler.sendEmptyMessage(UPLOAD_FAILD);
                            return;
                        }
                        Message m = new Message();
                        m.obj = rjson;
                        m.what = RESPONSE_DATA;
                        mHandler.sendMessage(m);


                        Log.d("xzyupload", 666666 + "");


//                boolean result = rjson.getBoolean("json");
//                if(result){//判断结果是否正确
//                    mHandler.sendEmptyMessage(UPLOAD_SCCESS);
//                }else{
//                    mHandler.sendEmptyMessage(UPLOAD_FAILD);
//                }


//                    } else {
//                        Log.d("xzyupload", "连接失败了哦" + "");
//                        mHandler.sendEmptyMessage(UPLOAD_FAILD);
//
//                    }
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(UPLOAD_FAILD);

                    Log.d("xzyupload", e.getMessage());

                } finally {
                    urlConnection.disconnect();//使用完关闭TCP连接，释放资源
                }


            }
        }).start();

    }


    //处理讯飞平台返回的数据
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == RESPONSE_DATA) {
                JSONObject jsonResponse = (JSONObject) msg.obj;
                parseJson(jsonResponse);
                mCustomScrollView.setVisibility(View.VISIBLE);
                emptyToCemara.setVisibility(View.GONE);
            }
        }
    };


    //解析返回的json串并存入集合wordsList。
    private void parseJson(JSONObject jsonResponse) {

        JSONObject dataJson = null;  //解析data字段
        try {
            dataJson = jsonResponse.getJSONObject("data");
            JSONArray blockArray = dataJson.getJSONArray("block");  //解析block字段
            for (int iOfBlock = 0; iOfBlock < blockArray.length(); iOfBlock++) {
                JSONObject oneBlockJson = blockArray.getJSONObject(iOfBlock);
                JSONArray lineArray = oneBlockJson.getJSONArray("line");       //解析line字段

                for (int iOfLine = 0 ; iOfLine < lineArray.length() ; iOfLine++) {
                    JSONObject oneLineJson = lineArray.getJSONObject(iOfLine);
                    int confidence = oneLineJson.getInt("confidence");   //解析confidence字段
                    JSONObject locationOfLine = oneLineJson.getJSONObject("location");   //解析每个line的location字段
                    JSONArray wordArray = oneLineJson.getJSONArray("word");     //解析word字段。

                    for (int iOfWord = 0 ; iOfWord < wordArray.length() ; iOfWord++) {
                        JSONObject oneWordJson = wordArray.getJSONObject(iOfWord);
                        String content = oneWordJson.getString("content");  //解析content
                        JSONObject location = oneWordJson.getJSONObject("location");    //解析每个word的location字段

                        JSONObject top_left = location.getJSONObject("top_left");
                        JSONObject right_bottom = location.getJSONObject("right_bottom");
                        int top_left_X = top_left.getInt("x");
                        int top_left_Y = top_left.getInt("y");
                        int right_bottom_X = right_bottom.getInt("x");
                        int right_bottom_Y = right_bottom.getInt("y");

                        WordsInfo wordsInfo = new WordsInfo();
                        wordsInfo.setWord(content);
                        wordsInfo.setTop_left_X(top_left_X);
                        wordsInfo.setTop_left_Y(top_left_Y);
                        wordsInfo.setRight_bottom_X(right_bottom_X);
                        wordsInfo.setRight_bottom_Y(right_bottom_Y);

                        wordsList.add(wordsInfo);
                        Log.d("xzyList", wordsList.size()+"");

                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String getCurTime() {
        curTime = System.currentTimeMillis()/1000 +"";

        return curTime;
    }
}
