package com.example.xzy.wordidtyandcltn.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件描述：package com.example.dhcommonlib.util;
 * 功能说明：
 * 版权申明：
 * @author ding_qili 
 * @version 2015-4-13上午9:22:49
 */

public class Base64Help {
	public static String encode(byte[] bytes){
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
	
	public static byte[] decode(String str) {
		return Base64.decode(str, Base64.DEFAULT);
	}

	public static String bitmapToBase64(Bitmap bitmap) {

//		String result = null;
		String result1 = null;
		String result2 = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result1 = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
				byte[] encode = Base64.encode(bitmapBytes, Base64.DEFAULT);
				result2 = new String(encode, "UTF-8");

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result2;
	}



	/**
	 * 将图片转换成Base64编码的字符串
	 * @param path
	 * @return base64编码的字符串
	 */
	public static String imageToBase64(String path){
		if(TextUtils.isEmpty(path)){
			return null;
		}
		InputStream is = null;
		byte[] data = null;
		String result = null;
		try{
			is = new FileInputStream(path);
			//创建一个字符流大小的数组。
			data = new byte[is.available()];
			//写入数组
			is.read(data);
			//用默认的编码格式进行编码
			result = Base64.encodeToString(data,Base64.DEFAULT);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(null !=is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}



	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
