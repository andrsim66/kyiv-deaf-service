package com.smartcity.kyivdeafservice.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oovoo.sdk.api.ui.VideoPanel;
import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.fragments.CallFragment;
import com.smartcity.kyivdeafservice.app.objects.VideoItem;
import com.smartcity.kyivdeafservice.app.utils.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andrii on 06.09.15.
 */
public class VideoAdapter extends BaseAdapter {
    private final List<VideoItem> mItems = new ArrayList<>();
    private final LayoutInflater mInflater;
    private Context mContext;
    private App app;
    private CallFragment callFragment;


    public VideoAdapter(Context context, CallFragment callFragment) {
        app = (App) ((Activity) context).getApplication();
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.callFragment = callFragment;
    }

    public int getCount() {
        return mItems.size();
    }

    @Override
    public VideoItem getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).getVideo().getId();
    }

    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = view;

        VideoItem item = getItem(position);

        if (v == null) {
            v = mInflater.inflate(R.layout.item_video_grid, viewGroup, false);

            VideoPanel video = (VideoPanel) v.findViewById(R.id.video_panel_view);

            v.setTag(R.id.video_panel_view, video);

            TextView displayNameTextView = (TextView) v.findViewById(R.id.display_name_text_view);
            v.setTag(R.id.display_name_text_view, displayNameTextView);

            ImageView avatarImageView = (ImageView) v.findViewById(R.id.avatar_image_view);
            v.setTag(R.id.avatar_image_view, avatarImageView);

            TextView noVideoMessage = (TextView) v.findViewById(R.id.no_video_message);
            v.setTag(R.id.no_video_message, noVideoMessage);

            if (item.getVideo() == null) {
//                if (mutedUserIds.contains(item.getUserId())) {
                item.showAvatar();
//                } else {
//                    item.setVideo(video);
//                    app.bindVideoPanel(item.getUserId(), video);
//                    video.setVisibility(View.VISIBLE);
//                }
            }
        } else {
            if (item.getVideo() == null) {
                VideoPanel video = (VideoPanel) v.getTag(R.id.video_panel_view);
                item.setVideo(video);
                app.bindVideoPanel(item.getUserId(), video);
                video.setVisibility(View.VISIBLE);
            }
        }
        VideoPanel video = (VideoPanel) v.getTag(R.id.video_panel_view);

        final View bottomView = callFragment.getCallbar().findViewById(R.id.fl_controls);
        final Window window = ((Activity) mContext).getWindow();
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int contentViewBottom = bottomView.getMeasuredHeight();
        contentViewBottom = contentViewBottom == 0 ? contentViewTop : contentViewBottom;

        int width = getDisplaySize().x / 2;
        int height = (int) ((getDisplaySize().y -
                (contentViewTop + contentViewBottom)) / 2.4) - (v.getPaddingTop() * 4);
        video.setTag(new Point(width, height));

        TextView displayNameTextView = (TextView) v.getTag(R.id.display_name_text_view);
        displayNameTextView.setText(item.getUserData());

        ImageView avatarImageView = (ImageView) v.getTag(R.id.avatar_image_view);
        if (item.isAvatarVisible()) {
            avatarImageView.setVisibility(View.VISIBLE);
        } else {
            avatarImageView.setVisibility(View.INVISIBLE);
        }

        TextView errorMessage = (TextView) v.getTag(R.id.no_video_message);
        if (item.isErrorMessageVisible()) {
            errorMessage.setText(mContext.getResources().getString(R.string.video_cannot_be_viewed));
            errorMessage.setVisibility(View.VISIBLE);
            avatarImageView.setVisibility(View.VISIBLE);
        } else {
            errorMessage.setVisibility(View.GONE);
            if (!item.isAvatarVisible()) {
                avatarImageView.setVisibility(View.INVISIBLE);
            }
        }

        return v;
    }

    public Point getDisplaySize() {
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public void addItem(VideoItem item) {
        item.setAdapter(this);
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(String userId) {

        Iterator<VideoItem> iter = mItems.iterator();
        while (iter.hasNext()) {
            VideoItem item = iter.next();
            if ((item.getUserId() == null && userId == null)
                    || (item.getUserId() != null
                    && item.getUserId().equals(userId))) {
                VideoPanel video = item.getVideo();
                if (video != null) {
                    video.setVisibility(View.INVISIBLE);
                    item.setAdapter(null);
                    item.setVideo(null);
                }
                iter.remove();
                app.unbindVideoPanel(item.getUserId(), video);
            } else if (item.getUserId() != null) {
                VideoPanel video = item.getVideo();
                item.setAdapter(null);
                item.setVideo(null);
                app.unbindVideoPanel(item.getUserId(), video);
            }
        }

        notifyDataSetChanged();
    }

    public void removeAllItems() {
        Iterator<VideoItem> iter = mItems.iterator();
        while (iter.hasNext()) {
            VideoItem item = iter.next();
            VideoPanel video = item.getVideo();
            if (video != null) {
                video.setVisibility(View.INVISIBLE);
                item.setVideo(null);
            }
            iter.remove();
            app.unbindVideoPanel(item.getUserId(), video);
        }
    }

    public void showAvatar(String userId) {
        try {
            for (VideoItem item : mItems) {
                if ((item.getUserId() == null && userId == null)
                        || (item.getUserId() != null
                        && userId != null && item.getUserId().equals(userId))) {
                    item.showAvatar();
                    break;
                }
            }

            notifyDataSetChanged();
        } catch (Exception err) {
            Logger.e("showAvatar " + err);
        }
    }

    public void showNoVideoMessage(String userId) {
        for (VideoItem item : mItems) {
            if ((item.getUserId() == null && userId == null)
                    || (item.getUserId() != null
                    && userId != null && item.getUserId().equals(userId))) {
                item.showErrorMessage();
                break;
            }
        }

        notifyDataSetChanged();
    }

    public void hideAvatar(String userId) {
        try {
            for (VideoItem item : mItems) {
                if ((item.getUserId() == null && userId == null)
                        || (item.getUserId() != null
                        && userId != null && item.getUserId().equals(userId))) {
                    item.hideAvatar();
                    break;
                }
            }

            notifyDataSetChanged();
        } catch (Exception err) {
            Logger.e("hideAvatar = " + err);
        }
    }

    public void hideNoVideoMessage(String userId) {
        try {
            for (VideoItem item : mItems) {
                if ((item.getUserId() == null && userId == null)
                        || (item.getUserId() != null
                        && userId == null && item.getUserId().equals(userId))) {
                    item.hideErrorMessage();
                    break;
                }
            }

            notifyDataSetChanged();
        } catch (Exception err) {
            Logger.e("hideNoVideoMessage = " + err);
        }
    }
}
