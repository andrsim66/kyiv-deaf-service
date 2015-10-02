package com.smartcity.kyivdeafservice.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oovoo.core.sdk_error;
import com.oovoo.sdk.interfaces.Device;
import com.oovoo.sdk.interfaces.VideoController;
import com.oovoo.sdk.interfaces.VideoControllerListener;
import com.oovoo.sdk.interfaces.VideoDevice;
import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.adapters.VideoAdapter;
import com.smartcity.kyivdeafservice.app.objects.VideoItem;
import com.smartcity.kyivdeafservice.app.utils.MenuList;

import java.util.ArrayList;

public class CallFragment extends Fragment implements
        App.ParticipantsListener, App.CallControllerListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private GridView videoGridView;
    private VideoAdapter mAdapter;

    //    private Button microphoneBttn;
    private ImageView mIvMicMute;
    private ImageView mIvSpeakerMute;
    private ImageView mIvCameraMute;
    //    private Button speakerBttn;
//    private Button cameraBttn;
    private RelativeLayout mRlEndCall;
    private View callbar;
    private App app;

    private CameraState cameraState = CameraState.FRONT_CAMERA;


    public enum CameraState {
        BACK_CAMERA(0), FRONT_CAMERA(1), MUTE_CAMERA(2);

        private final int value;

        private CameraState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CallFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        callbar = view;

        app = (App) getActivity().getApplication();

        initViews(view);
        setupViews();

        ArrayList<VideoDevice> cameras = app.getVideoCameras();
        for (VideoDevice camera : cameras) {
            if (camera.toString().equals("FRONT") &&
                    !app.getActiveCamera().getID().equalsIgnoreCase(camera.getID())) {
                app.switchCamera(camera);
                break;
            }
        }

        app.changeResolution(VideoController.ResolutionLevel.ResolutionLevelMed);
        app.openPreview();

        addParticipantVideoPanel(null, "Me");
        mAdapter.hideAvatar(null);
        mAdapter.hideNoVideoMessage(null);

        app.addParticipantListener(this);
        app.setControllerListener(this);
        return view;
    }

    public View getCallbar() {
        return callbar;
    }

    private void initViews(View view) {
        videoGridView = (GridView) view.findViewById(R.id.gv_video_panels);
        mIvMicMute = (ImageView) view.findViewById(R.id.iv_mic_mute);
//        speakerBttn = (Button) view.findViewById(R.id.speakersButton);
        mIvSpeakerMute = (ImageView) view.findViewById(R.id.iv_speaker_mute);
//        cameraBttn = (Button) view.findViewById(R.id.cameraButton);
        mIvCameraMute = (ImageView) view.findViewById(R.id.iv_camera_mute);
        mRlEndCall = (RelativeLayout) view.findViewById(R.id.rl_end_call);
    }

    private void setupViews() {
        mAdapter = new VideoAdapter(getActivity(), this);
        videoGridView.setAdapter(mAdapter);

        mIvMicMute.setOnClickListener(this);
//        speakerBttn.setOnClickListener(this);
        mIvSpeakerMute.setOnClickListener(this);
        mRlEndCall.setOnClickListener(this);

//        setupCameraButton();
        updateController();
    }

//    private void setupCameraButton() {
//        prepareButtonMenu(cameraBttn, new MenuList() {
//            @Override
//            public void fill(View view, ContextMenu menu) {
//                try {
//                    menu.setHeaderTitle(R.string.change_camera);
//                    ArrayList<VideoDevice> cameras = app.getVideoCameras();
//                    for (VideoDevice camera : cameras) {
//                        MenuItem item = null;
//
//                        if (camera.toString().equals("FRONT")) {
//                            item = menu.add(view.getId(), CameraState.FRONT_CAMERA.getValue(), 0,
//                                    R.string.front_camera);
//                        } else if (camera.toString().equals("BACK")) {
//                            item = menu.add(view.getId(), CameraState.BACK_CAMERA.getValue(), 0,
//                                    R.string.back_camera);
//                        }
//
//                        item.setOnMenuItemClickListener(new DeviceMenuClickListener(camera) {
//                            @Override
//                            public boolean onMenuItemClick(Device camera, MenuItem item) {
//                                app.switchCamera((VideoDevice) camera);
//                                app.muteCamera(false);
//                                mAdapter.hideAvatar(null);
//                                if (item.getItemId() == CameraState.FRONT_CAMERA.getValue()) {
//                                    cameraState = CameraState.FRONT_CAMERA;
//                                } else {
//                                    cameraState = CameraState.BACK_CAMERA;
//                                }
//                                cameraBttn.setSelected(false);
//                                return true;
//                            }
//
//                        });
//                    }
//
//                    MenuItem item = menu.add(view.getId(), CameraState.MUTE_CAMERA.getValue(), 0, R.string.mute_camera);
//                    item.setOnMenuItemClickListener(new MuteCameraMenuClickListener(app) {
//
//                        @Override
//                        public boolean onMenuItemClick(boolean state, MenuItem item) {
//                            app.muteCamera(state);
//                            mAdapter.showAvatar(null);
//                            cameraState = state ? CameraState.MUTE_CAMERA : CameraState.MUTE_CAMERA;
//                            cameraBttn.setSelected(true);
//                            return true;
//                        }
//                    });
//
//                    for (int i = 0; i < menu.size(); ++i) {
//                        MenuItem mi = menu.getItem(i);
//                        if (cameraState.getValue() == mi.getItemId()) {
//                            mi.setChecked(true);
//                            break;
//                        }
//                    }
//
//                    menu.setGroupCheckable(view.getId(), true, true);
//                } catch (Exception err) {
//                    err.printStackTrace();
//                }
//            }
//        });
//    }

    abstract class DeviceMenuClickListener implements MenuItem.OnMenuItemClickListener {
        private Device device = null;

        DeviceMenuClickListener(Device device) {
            this.device = device;
        }

        @Override
        public final boolean onMenuItemClick(MenuItem item) {
            return onMenuItemClick(device, item);
        }

        public abstract boolean onMenuItemClick(Device device, MenuItem item);
    }

    abstract class MuteCameraMenuClickListener implements MenuItem.OnMenuItemClickListener {
        App app = null;

        MuteCameraMenuClickListener(App app) {
            this.app = app;
        }

        @Override
        public final boolean onMenuItemClick(MenuItem item) {
            return onMenuItemClick(!app.isCameraMuted(), item);
        }

        public abstract boolean onMenuItemClick(boolean state, MenuItem item);
    }

    private void prepareButtonMenu(final Button button, MenuList list) {
        button.setOnClickListener(this);
        button.setTag(list);
        getActivity().registerForContextMenu(button);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected void addParticipantVideoPanel(String userId, String userData) {
        try {
            mAdapter.addItem(new VideoItem(userId, userData));

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onParticipantJoined(final String userId, final String userData) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addParticipantVideoPanel(userId, userData);
                }
            });
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onParticipantLeft(final String userId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeParticipantVideoPanel(userId);
            }
        });
//        mutedUserIds.remove(userId);
    }

    protected void removeParticipantVideoPanel(String userId) {
        try {
            mAdapter.removeItem(userId);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onRemoteVideoStateChanged(final String userId, final VideoControllerListener.RemoteVideoState state, final sdk_error error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case RVS_Started:
                    case RVS_Resumed:

                        mAdapter.hideAvatar(userId);
                        mAdapter.hideNoVideoMessage(userId);
                        break;
                    case RVS_Stopped:
                        //videoAdapter.showAvatar(userId);
                        break;
                    case RVS_Paused:

                        //videoAdapter.showAvatar(userId);
                        mAdapter.showNoVideoMessage(userId);
                        break;
                }

                if (error == sdk_error.ResolutionNotSupported) {
                    mAdapter.showAvatar(userId);
                }
            }
        });
    }

    @Override
    public void onTransmitStateChanged(boolean state, sdk_error err) {

    }

    @Override
    public void updateController() {
        try {
            if (app.isMicMuted()) {
                mIvMicMute.setImageDrawable(getActivity()
                        .getResources().getDrawable(R.drawable.ic_mic_white_48dp));
            } else {
                mIvMicMute.setImageDrawable(getActivity()
                        .getResources().getDrawable(R.drawable.ic_mic_off_white_48dp));
            }
//            speakerBttn.setEnabled(true);
//            speakerBttn.setSelected(app.isSpeakerMuted());
            if (app.isSpeakerMuted()) {
                mIvSpeakerMute.setImageDrawable(getActivity()
                        .getResources().getDrawable(R.drawable.ic_volume_up_white_48dp));
            } else {
                mIvSpeakerMute.setImageDrawable(getActivity()
                        .getResources().getDrawable(R.drawable.ic_volume_off_white_48dp));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        mAdapter.removeAllItems();

        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_speaker_mute:
//                speakerBttn.setEnabled(false);
                if (app.isSpeakerMuted()) {
                    mIvSpeakerMute.setImageDrawable(getActivity()
                            .getResources().getDrawable(R.drawable.ic_volume_up_white_48dp));
                } else {
                    mIvSpeakerMute.setImageDrawable(getActivity()
                            .getResources().getDrawable(R.drawable.ic_volume_off_white_48dp));
                }
                app.onSpeakerClick();
                break;
            case R.id.iv_mic_mute:
                if (app.isMicMuted()) {
                    mIvMicMute.setImageDrawable(getActivity()
                            .getResources().getDrawable(R.drawable.ic_mic_white_48dp));
                } else {
                    mIvMicMute.setImageDrawable(getActivity()
                            .getResources().getDrawable(R.drawable.ic_mic_off_white_48dp));
                }
                app.onMicrophoneClick();
                break;
            case R.id.iv_camera_mute:
                if (cameraState == CameraState.MUTE_CAMERA) {
                    app.muteCamera(false);
//                    mAdapter.showAvatar(null);
                    mIvCameraMute.setImageDrawable(getActivity()
                            .getResources().getDrawable(R.drawable.ic_videocam_white_48dp));
                } else {
                    app.muteCamera(true);
                    mIvCameraMute.setImageDrawable(getActivity()
                            .getResources().getDrawable(R.drawable.ic_videocam_off_white_48dp));
                }
                cameraState = cameraState == cameraState.MUTE_CAMERA ? cameraState.FRONT_CAMERA :
                        cameraState.MUTE_CAMERA;
                break;
            case R.id.rl_end_call:
                app.onEndOPfCall();
                if (mListener != null)
                    mListener.onCallEnded();
//                int count = getFragmentManager().getBackStackEntryCount();
//                String name = getFragmentManager().getBackStackEntryAt(count - 2).getName();
//                getFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onCallEnded();
    }

}
