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
import com.smartcity.kyivdeafservice.app.objects.Interpreter;
import com.smartcity.kyivdeafservice.app.utils.UtilsDevice;

import java.util.List;

/**
 * Created by andrii on 06.09.15.
 */
public class InterpretersAdapter extends ArrayAdapter<Interpreter> {

    private Context mContext;
    private int mLayoutResourceId;

    public InterpretersAdapter(Context context, int layoutResourceId, List<Interpreter> interpreters) {
        super(context, layoutResourceId, interpreters);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
    }

    public static class ViewHolder {
        public final ImageView ivIcon;
        public final TextView tvName;
        public final TextView tvPrice;
        public final TextView tvSchedule;
        public final View vStatus;

        public ViewHolder(View view) {
            ivIcon = (ImageView) view.findViewById(R.id.iv_interpreter_photo);
            tvName = (TextView) view.findViewById(R.id.tv_interpreter_name);
            tvPrice = (TextView) view.findViewById(R.id.tv_interpreter_price);
            tvSchedule = (TextView) view.findViewById(R.id.tv_interpreter_schedule);
            vStatus = view.findViewById(R.id.v_interpreter_status);
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

        Interpreter interpreter = getItem(position);

        UtilsDevice.loadImage(interpreter.getPhotoUrl(), holder.ivIcon, parent.getWidth());
        holder.tvName.setText(interpreter.getName());
        holder.tvPrice.setText(interpreter.getPricePerHour());
        holder.tvSchedule.setText(interpreter.getWorkSchedule());

        if (interpreter.isActive()) {
            holder.vStatus.setBackgroundResource(R.drawable.active_circle);
        } else {
            holder.vStatus.setBackgroundResource(R.drawable.not_active_circle);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Interpreter getItem(int position) {
        return super.getItem(position);
    }
}
