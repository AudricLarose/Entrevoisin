package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private  List<Neighbour> list_favoris;
    private String trueName;
    private static final String TAG = "MyNeighbourRecyclerView";
    private String avatarUrl;
    private NeighbourApiService mApiService;

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mApiService = DI.getNeighbourApiService();
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });

        holder.ligne.setOnClickListener(new View.OnClickListener() {
            public Neighbour getContent(){
                return neighbour;
            }
            @Override
            public void onClick(View v) {
                Log.d(TAG, "neighbour: " + neighbour);
                trueName= neighbour.getName();
                int id=neighbour.getId();
                avatarUrl=neighbour.getAvatarUrl();
                int position=mNeighbours.indexOf(neighbour);
                Intent intent = new Intent(v.getContext(), Section_perso.class);
                list_favoris=mApiService.getFav();
                if (list_favoris.contains(neighbour)) {
                    boolean alter= true;
                    intent.putExtra("alter", alter);
               } else {
                    boolean alter=false;
                    intent.putExtra("alter", alter);
               }
                intent.putExtra("Big_Name",trueName);
                intent.putExtra("Name",trueName);
                intent.putExtra("Location","Paris");
                intent.putExtra("posi",position);
                intent.putExtra("url",avatarUrl);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.layout)
        public ConstraintLayout ligne;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
