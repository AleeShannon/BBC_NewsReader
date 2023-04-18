package com.example.bbc_newsreader;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HeadlineDownloader {

    public HeadlineDownloader(Context context) {
    }

    public void downloadHeadline(String link, Callback callback) {
        new DownloadTask(callback).execute(link);
    }

    public interface Callback {
        void onHeadlineDownloaded(Headline headline);

        void onDownloadFailed();
    }

    private class DownloadTask extends AsyncTask<String, Void, Headline> {

        private final Callback callback;

        public DownloadTask(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected Headline doInBackground(String... links) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(links[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    String html = response.body().string();
                    return HeadlineParser.parse(html);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Headline headline) {
            if (headline != null) {
                callback.onHeadlineDownloaded(headline);
            } else {
                callback.onDownloadFailed();
            }
        }
    }
}




