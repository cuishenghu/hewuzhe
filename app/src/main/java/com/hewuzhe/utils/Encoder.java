package com.hewuzhe.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encoder {

    public static int sizeInfo = 1;


    public static String getDecodeStr(String string) {
        byte[] content = Base64.decode(string.getBytes(), Base64.DEFAULT);
        return new String(content);

    }


    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.length());
    }


    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) {
        File file = new File(path);
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);

            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @param filePath 图片路径
     * @return Base64 字符串
     */
    public static String getEnocodeStr(String filePath) {

        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            // Calculate inSampleSize
            if (sizeInfo == 1) {
                options.inSampleSize = calculateInSampleSize(options, 400, 400);
            } else if (sizeInfo == 2) {
                options.inSampleSize = calculateInSampleSize(options, 700, 700);
            } else if (sizeInfo == 3) {
                options.inSampleSize = calculateInSampleSize(options, 900, 900);
            } else {
                options.inSampleSize = calculateInSampleSize(options, options.outWidth, options.outHeight);
            }
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            //Bitmap bitmap = BitmapFactory.decodeFile(string, options);

            Bitmap bitmap = getimage(filePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);

            bitmap.recycle();
            bitmap = null;

            byte[] image = stream.toByteArray();
            return Base64.encodeToString(image, 0);
        } catch (Exception e) {
            return "";
        }
    }


    // select the image size based on user select
    public static File getimageFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = 800f;
        float ww = 480f;

        if (sizeInfo == 1) {
            hh = 130f;
            ww = 130f;
        } else if (sizeInfo == 2) {
            hh = 200f;
            ww = 200f;
        } else if (sizeInfo == 3) {
            hh = 400f;
            ww = 320f;
        } else if (sizeInfo == 4) {
            hh = 800f;
            ww = 480f;
        } else {
            hh = 1280f;
            ww = 720f;
        }

        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImageToFile(bitmap);
    }

    // select the image size based on user select
    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = 800f;
        float ww = 480f;

        if (sizeInfo == 1) {
            hh = 130f;
            ww = 130f;
        } else if (sizeInfo == 2) {
            hh = 200f;
            ww = 200f;
        } else if (sizeInfo == 3) {
            hh = 400f;
            ww = 320f;
        } else {
            hh = 800f;
            ww = 480f;
        }

        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);
    }

    // compress the image size as what user selected the image size
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    public static File compressImageToFile(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        // ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //  Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

        FileOutputStream out;
        try {
            out = new FileOutputStream(getTempImage().getPath());
            baos.writeTo(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getTempImage();
    }


    public static File getTempImage() {
        String fileName = "jinying" + "-temp.jpg";

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File tempFile = new File(Environment.getExternalStorageDirectory(), fileName);
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return tempFile;
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }
}
