package com.smartcity.kyivdeafservice.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.objects.Contact;
import com.smartcity.kyivdeafservice.app.utils.UtilsDevice;

import java.util.List;

/**
 * Created by andrii on 06.09.15.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {

    private Context mContext;
    private int mLayoutResourceId;

    public ContactsAdapter(Context context, int layoutResourceId, List<Contact> contacts) {
        super(context, layoutResourceId, contacts);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
    }

    public static class ViewHolder {
        public final ImageView ivIcon;
        public final TextView tvName;
        public final TextView tvPhoneNumber;

        public ViewHolder(View view) {
            ivIcon = (ImageView) view.findViewById(R.id.iv_contact_photo);
            tvName = (TextView) view.findViewById(R.id.tv_contact_name);
            tvPhoneNumber = (TextView) view.findViewById(R.id.tv_contact_phone_number);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = getItem(position);

        UtilsDevice.loadImage(contact.getPhotoUrl(), holder.ivIcon, parent.getWidth());
        holder.tvName.setText(contact.getName());
        holder.tvPhoneNumber.setText(contact.getPhoneNumber());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Contact getItem(int position) {
        return super.getItem(position);
    }
}
