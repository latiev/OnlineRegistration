package id.latiev.onlineregistration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.latiev.onlineregistration.R;
import id.latiev.onlineregistration.model.Dokter;

/**
 * Created by latiev on 4/17/17.
 */

public class JadwalDokterAdapter extends RecyclerView.Adapter<JadwalDokterAdapter.MyViewHolder> {

    private Context context;
    private List<Dokter> dokterList;

    public JadwalDokterAdapter(Context context, List<Dokter> dokterList) {
        this.context = context;
        this.dokterList = dokterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Dokter dokter = dokterList.get(position);

        if (dokter.getKelamin() == "P"){
            Glide.with(context).load(R.drawable.ic_female).into(holder.dokterThumbnail);
        } else if (dokter.getKelamin() == "L"){
            Glide.with(context).load(R.drawable.ic_male).into(holder.dokterThumbnail);
        }

        holder.title.setText(dokter.getNama());
        holder.subtitle.setText(dokter.getKlinik());
        holder.content.setText(dokter.getWaktu());
    }

    @Override
    public int getItemCount() {
        return dokterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView dokterThumbnail;
        public TextView title, subtitle, content;

        public MyViewHolder(View view) {
            super(view);

            dokterThumbnail = (CircleImageView)view.findViewById(R.id.civ_cl_icon);
            title = (TextView) view.findViewById(R.id.tv_cl_title);
            content = (TextView) view.findViewById(R.id.tv_cl_content);
            subtitle = (TextView) view.findViewById(R.id.tv_cl_subtitle);
        }
    }
}
