package com.smartcity.kyivdeafservice.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment implements App.ParticipantsListener, App.CallControllerListener, View.OnClickListener {
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

    private Button microphoneBttn;
    private Button speakerBttn;
    private Button cameraBttn;
    private Button endOfCall;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
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

        app = (App) getActivity().getApplication();

        initViews(view);
        initControlBar(view);
        setupViews();
        callbar = view;

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
    }

    private void setupViews() {
        mAdapter = new VideoAdapter(getActivity(), this);
        videoGridView.setAdapter(mAdapter);
    }

    private void initControlBar(View callbar) {
        this.callbar = callbar;

        microphoneBttn = (Button) callbar.findViewById(R.id.microphoneButton);
        microphoneBttn.setOnClickListener(this);

        speakerBttn = (Button) callbar.findViewById(R.id.speakersButton);
        speakerBttn.setOnClickListener(this);

        cameraBttn = (Button) callbar.findViewById(R.id.cameraButton);
        prepareButtonMenu(cameraBttn, new MenuList() {
            @Override
            public void fill(View view, ContextMenu menu) {
                try {
                    menu.setHeaderTitle(R.string.change_camera);
                    ArrayList<VideoDevice> cameras = app.getVideoCameras();
                    for (VideoDevice camera : cameras) {
                        MenuItem item = null;

                        if (camera.toString().equals("FRONT")) {
                            item = menu.add(view.getId(), CameraState.FRONT_CAMERA.getValue(), 0,
                                    R.string.front_camera);
                        } else if (camera.toString().equals("BACK")) {
                            item = menu.add(view.getId(), CameraState.BACK_CAMERA.getValue(), 0,
                                    R.string.back_camera);
                        }

                        item.setOnMenuItemClickListener(new DeviceMenuClickListener(camera) {
                            @Override
                            public boolean onMenuItemClick(Device camera, MenuItem item) {
                                app.switchCamera((VideoDevice) camera);
                                app.muteCamera(false);
                                mAdapter.hideAvatar(null);
                                if (item.getItemId() == CameraState.FRONT_CAMERA.getValue()) {
                                    cameraState = CameraState.FRONT_CAMERA;
                                } else {
                                    cameraState = CameraState.BACK_CAMERA;
                                }
                                cameraBttn.setSelected(false);
                                return true;
                            }

                        });
                    }

                    MenuItem item = menu.add(view.getId(), CameraState.MUTE_CAMERA.getValue(), 0, R.string.mute_camera);
                    item.setOnMenuItemClickListener(new MuteCameraMenuClickListener(app) {

                        @Override
                        public boolean onMenuItemClick(boolean state, MenuItem item) {
                            app.muteCamera(state);
                            mAdapter.showAvatar(null);
                            cameraState = state ? CameraState.MUTE_CAMERA : CameraState.MUTE_CAMERA;
                            cameraBttn.setSelected(true);
                            return true;
                        }
                    });

                    for (int i = 0; i < menu.size(); ++i) {
                        MenuItem mi = menu.getItem(i);
                        if (cameraState.getValue() == mi.getItemId()) {
                            mi.setChecked(true);
                            break;
                        }
                    }

                    menu.setGroupCheckable(view.getId(), true, true);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        endOfCall = (Button) callbar.findViewById(R.id.endOfCallButton);
        endOfCall.setOnClickListener(this);

        updateController();
    }

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
//            disableFullScreenView();

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
            microphoneBttn.setEnabled(true);
            speakerBttn.setEnabled(true);
            microphoneBttn.setSelected(app.isMicMuted());
            speakerBttn.setSelected(app.isSpeakerMuted());
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
            case R.id.speakersButton:
                speakerBttn.setEnabled(false);
                app.onSpeakerClick();
                break;
            case R.id.microphoneButton:
                microphoneBttn.setEnabled(false);
                app.onMicrophoneClick();
                break;
            case R.id.endOfCallButton:
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
