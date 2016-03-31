package com.dmd.zsb.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.PostUploadRequest;
import com.dmd.dialog.GravityEnum;
import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.tutor.utils.Tools;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.R;
import com.android.volley.FormFile;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends Activity implements OnUploadProcessListener {
    private static final int UPFILEINIT = 0;
    private static final int UPFILE = 1;
    private static final int UPFILEING = 2;
    private static final int UPFILEDONE = 3;
    private static int RESULT_LOAD_IMAGE = 4;

    @Bind(R.id.button)
    Button button;
    private File file = null;
    String picturePath=null;
    private Map<String, String> params = new HashMap<String, String>();//post的StringBody
    MaterialDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
             picturePath = cursor.getString(columnIndex);
            cursor.close();
            file = new File(picturePath);
            if (file != null && file.exists()) {
                handler.sendEmptyMessage(UPFILE);
            } else {
                Toast.makeText(TestActivity.this, "图片文件未找到", Toast.LENGTH_LONG).show();
            }
        }

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    Log.v("zms", "case whate 0 准备 文件大小:" + msg.arg1);
                    break;
                }
                case 1: {
                    long max = file.length();
                    Log.v("zms", "case whate 1 开始传送 文件大小:" + max + ":" + Tools.FormetFileSize(max));
                    if (max > 2147483647){
                        Log.v("zms", "您选择的图片:" + Tools.FormetFileSize(max));
                        Toast.makeText(TestActivity.this, "您选择的图片容量为:" + Tools.FormetFileSize(max) + "请重新选择小于2M的图片！", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Log.v("zms", "begin uploadFile....");

                    dialog= new MaterialDialog.Builder(TestActivity.this)
                            .title("进度")
                            .content("请稍后...")
                            .contentGravity(GravityEnum.CENTER)
                            .progress(false, 100, true)
                            .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {

                                }
                            })
                            .showListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {

                                }
                            }).show();
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("sid", XmlDB.getInstance(TestActivity.this).getKeyString("sid","sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(TestActivity.this).getKeyString("uid","uid"));
                    jsonObject.addProperty("fileName", file.getName());
                    jsonObject.addProperty("fileMime", "image/png");
                    List<FormFile> formImages=new ArrayList<>();

                    FormFile formfile1 = new FormFile(file.getName(), file,"file", "application/octet-stream");
                    formImages.add(formfile1);
                    PostUploadRequest<JsonObject> postUploadRequest=new PostUploadRequest<JsonObject>(UriHelper.getInstance().changeAvatar(jsonObject), formImages,new TypeToken<JsonObject>(){}.getType(), new Response.Listener<JsonObject>() {
                        @Override
                        public void onResponse(JsonObject response) {
                            Log.e("Entity",response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Entity",error.getMessage()+"0000");
                        }
                    });
                    postUploadRequest.setOnUploadProcessListener(TestActivity.this);
                    postUploadRequest.setSequence(1024*5);
                    postUploadRequest.setShouldCache(true);
                    postUploadRequest.setTag("Upload");
                    VolleyHelper.getInstance().getRequestQueue().add(postUploadRequest);
                    //uploadFile(file);
                    break;
                }

                case 2: {
                    int i=msg.arg1*100/(int)file.length();
                    if(i%10==0){
                        dialog.setProgress(i);
                    }

                    Log.v("zms", " %   %:" + i);
                    Log.v("zms", " case what 2 当前完成大小:" + Tools.FormetFileSize(msg.arg1) + "/" + Tools.FormetFileSize(file.length()));
                    break;

                }
                case 3: {
                    switch (msg.arg1) {
                        case 0: {
                            Log.v("zms", " 文件不存在，返回码:" + msg.arg1 + "内容:" + msg.obj);
                            break;
                        }
                        case 1: {
                            Log.v("zms", " 传送成功，返回码:" + msg.arg1 + "内容:" + msg.obj);
                            break;
                        }
                        case 4: {
                            Log.v("zms", " 上传失败，返回码:" + msg.arg1 + "内容:" + msg.obj);
                            break;
                        }
                    }
                    dialog.dismiss();
                    dialog=null;
                    break;
                }

                default:
                    break;
            }
        }

    };
    @Override
    public void onUploadDone(int responseCode, String message) {
        Log.v("zms", "onUploadDone " + message);
        Message msg = Message.obtain();
        msg.what = UPFILEDONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Log.v("zms", "onUploadProcess " + uploadSize);
        Message msg2 = Message.obtain();
        msg2.what = UPFILEING;
        msg2.arg1 = uploadSize;
        msg2.obj = "传送中。。。。";
        handler.sendMessage(msg2);
    }

    @Override
    public void initUpload(int fileSize) {
        Log.v("zms", "initUpload " + fileSize);
        Message msg = Message.obtain();
        msg.what = UPFILEINIT;
        msg.arg1 = fileSize;
        handler.sendMessage(msg);
    }
}

