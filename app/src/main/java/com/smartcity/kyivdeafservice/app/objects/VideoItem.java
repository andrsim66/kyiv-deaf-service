package com.smartcity.kyivdeafservice.app.objects;

import android.widget.BaseAdapter;

import com.oovoo.sdk.api.ui.VideoPanel;
import com.smartcity.kyivdeafservice.app.utils.Logger;

/**
 * Created by andrii on 06.09.15.
 */
public class VideoItem {

    private VideoPanel video = null;
    private boolean isAvatarVisible = true;
    private boolean isErrorMessageVisible = false;
    private final String userId;
    private final String userData;
    private BaseAdapter adapter = null;

    public VideoItem(String userId, String userData) {
        this.userId = userId;
        this.userData = userData;
        showAvatar();
    }

    public void setVideo(VideoPanel video) {
        if (video != null) {
            video.setVideoRenderStateChangeListener(new VideoPanel.VideoRenderStateChangeListener() {

                public String toString() {
                    return userId != null ? userId : "preview";
                }

                @Override
                public void onVideoRenderStart() {
                    try {
                        hideAvatar();
                        Logger.d("VideoControllerWrap -> VideoPanel -> Application  onVideoRenderStop hideAvatar " + userId != null ? userId : "preview" + ", adapter " + (adapter != null ? "set" : "null"));
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                    } catch (Exception err) {
                        Logger.e("onVideoRenderStart " + err);
                    }
                }

                @Override
                public void onVideoRenderStop() {
                    try {
                        showAvatar();
                        Logger.d("VideoControllerWrap -> VideoPanel -> Application  onVideoRenderStop showAvatar " + userId != null ? userId : "preview" + ", adapter " + (adapter != null ? "set" : "null"));
                        if (adapter != null)
                            adapter.notifyDataSetChanged();

                    } catch (Exception err) {
                        Logger.e("onVideoRenderStop " + err);
                    }

                }
            });
        } else {
            if (this.video != null) {
                this.video.setVideoRenderStateChangeListener(null);
            }
        }
        this.video = video;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public VideoPanel getVideo() {
        return this.video;
    }

    public boolean isAvatarVisible() {
        return this.isAvatarVisible;
    }

    public boolean isErrorMessageVisible() {
        return isErrorMessageVisible;
    }

    public void showAvatar() {
        this.isAvatarVisible = true;
    }

    public void hideAvatar() {
        this.isAvatarVisible = false;
        this.isErrorMessageVisible = false;
    }

    public void showErrorMessage() {
        this.isErrorMessageVisible = true;
    }

    public void hideErrorMessage() {
        this.isErrorMessageVisible = false;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserData() {
        return userData;
    }

}
