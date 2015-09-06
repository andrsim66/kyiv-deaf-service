package com.smartcity.kyivdeafservice.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.oovoo.sdk.api.ui.VideoPanel;
import com.oovoo.sdk.interfaces.VideoController;
import com.oovoo.sdk.interfaces.VideoDevice;
import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.adapters.ContactsAdapter;
import com.smartcity.kyivdeafservice.app.customViews.VideoPanelPreviewRect;
import com.smartcity.kyivdeafservice.app.objects.Contact;

import java.util.ArrayList;

public class ContactsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private App app;

    private ListView mLvContacts;
    private ContactsAdapter mAdapter;
    private ArrayList<Contact> mContacts;
    private VideoPanelPreviewRect previewRect;
    private OnFragmentInteractionListener mListener;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        app = (App) getActivity().getApplication();

        initViews(view);
        addContacts();
        setupViews();

//        VideoPanel panel = (VideoPanel) view.findViewById(R.id.preview_view);
//        previewRect = (VideoPanelPreviewRect) view.findViewById(R.id.preview_rect);



        String lastSessionId = app.getSettings().get("avs_session_id");
        String lastDisplayName = app.getSettings().get("avs_session_display_name");


//        app.bindVideoPanel(null, panel);

        return view;
    }

    private void initViews(View view) {
        mLvContacts = (ListView) view.findViewById(R.id.lv_contacts);
    }

    private void setupViews() {
        mAdapter = new ContactsAdapter(getActivity(), R.layout.item_contact, mContacts);
        mLvContacts.setAdapter(mAdapter);
        mLvContacts.setOnItemClickListener(this);
    }

    private void addContacts() {
        mContacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setName("Андрій Симончук");
        contact.setPhoneNumber("+380633670987");
        contact.setPhotoUrl("http://i.imgur.com/XdiXi65.png");
        mContacts.add(contact);

        contact = new Contact();
        contact.setName("Антон Петров");
        contact.setPhoneNumber("+380637575937");
        contact.setPhotoUrl("http://i.imgur.com/kVG7S3Y.png");
        mContacts.add(contact);

        contact = new Contact();
        contact.setName("Олександр Воронський");
        contact.setPhoneNumber("+380639947725");
        contact.setPhotoUrl("http://i.imgur.com/3WLI2bH.png");
        mContacts.add(contact);

        contact = new Contact();
        contact.setName("Олександр Мусаткін");
        contact.setPhoneNumber("+380635524952");
        contact.setPhotoUrl("http://i.imgur.com/a71of4l.png");
        mContacts.add(contact);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mListener != null) {
            mListener.onContactSelect(0);
        }
    }

    public interface OnFragmentInteractionListener {
        void onContactSelect(int position);
    }
}
