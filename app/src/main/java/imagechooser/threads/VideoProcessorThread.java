/**
 * ****************************************************************************
 * Copyright 2013 Kumar Bibek
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *****************************************************************************
 */

package imagechooser.threads;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore.Video.Thumbnails;
import android.text.TextUtils;
import android.util.Log;

import com.hewuzhe.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import imagechooser.api.ChosenVideo;
import imagechooser.api.FileUtils;
import imagechooser.exceptions.ChooserException;
import imagechooser.helpers.StreamHelper;


public class VideoProcessorThread extends MediaProcessorThread {

    private final static String TAG = VideoProcessorThread.class.getSimpleName();

    private VideoProcessorListener listener;

    private String previewImage;

    public VideoProcessorThread(String filePath, String foldername,
                                boolean shouldCreateThumbnails) {
        super(filePath, foldername, shouldCreateThumbnails);
        setMediaExtension("mp4");
    }

    public void setListener(VideoProcessorListener listener) {
        this.listener = listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            manageDirectoryCache("mp4");
            processVideo();
        } catch (Exception e) { // catch all, just to be sure we can send message back to listener in all circumenstances.
            Log.e(TAG, e.getMessage(), e);
            if (listener != null) {
                listener.onError(e.getMessage());
            }
        }
    }

    private void processVideo() throws ChooserException {

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "Processing Video file: " + filePath);
        }

        // Picasa on Android >= 3.0
        if (filePath != null && filePath.startsWith("content:")) {
            filePath = getAbsoluteImagePathFromUri(Uri.parse(filePath));
        }
        if (filePath == null || TextUtils.isEmpty(filePath)) {
            if (listener != null) {
                listener.onError("Couldn't process a null file");
            }
        } else if (filePath.startsWith("http")) {
            downloadAndProcess(filePath);
        } else if (filePath
                .startsWith("content://com.google.android.gallery3d")
                || filePath
                .startsWith("content://com.microsoft.skydrive.content.external")) {
            processPicasaMedia(filePath, ".mp4");
        } else if (filePath
                .startsWith("content://com.google.android.apps.photos.content")
                || filePath
                .startsWith("content://com.android.providers.media.documents")
                || filePath
                .startsWith("content://com.google.android.apps.docs.storage")) {
            processGooglePhotosMedia(filePath, ".mp4");
        } else if (filePath.startsWith("content://media/external/video")) {
            processContentProviderMedia(filePath, ".mp4");
        } else {
            process();
        }
    }

    @Override
    protected void process() throws ChooserException {
        super.process();
        if (shouldCreateThumnails) {
            createPreviewImage();
            String[] thumbnails = createThumbnails(createThumbnailOfVideo());
            processingDone(this.filePath, thumbnails[0], thumbnails[1]);
        } else {
            processingDone(this.filePath, this.filePath, this.filePath);
        }
    }

    private String createPreviewImage() throws ChooserException {
        previewImage = null;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
                Thumbnails.FULL_SCREEN_KIND);
        if (bitmap != null) {
            previewImage = FileUtils.getDirectory(foldername) + File.separator
                    + Calendar.getInstance().getTimeInMillis() + ".jpg";
            File file = new File(previewImage);

            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch(IOException e) {
                throw new ChooserException(e);
            } finally {
                StreamHelper.flush(stream);
            }

        }
        return previewImage;
    }

    private String createThumbnailOfVideo() throws ChooserException {
        String thumbnailPath = null;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
                Thumbnails.MINI_KIND);
        if (bitmap != null) {
            thumbnailPath = FileUtils.getDirectory(foldername) + File.separator
                    + Calendar.getInstance().getTimeInMillis() + ".jpg";
            File file = new File(thumbnailPath);

            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch(IOException e) {
                throw new ChooserException(e);
            } finally {
                StreamHelper.flush(stream);
            }
        }
        return thumbnailPath;
    }

    @Override
    protected void processingDone(String original, String thumbnail,
                                  String thunbnailSmall) {
        if (listener != null) {
            ChosenVideo video = new ChosenVideo();
            video.setVideoFilePath(original);
            video.setThumbnailPath(thumbnail);
            video.setThumbnailSmallPath(thunbnailSmall);
            video.setVideoPreviewImage(previewImage);
            listener.onProcessedVideo(video);
        }
    }
}
