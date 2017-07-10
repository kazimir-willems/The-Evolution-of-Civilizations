package seow.evolution.com.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import seow.evolution.com.R;
import seow.evolution.com.fragment.FavoriteFragment;
import seow.evolution.com.model.FavoriteItem;
import seow.evolution.com.ui.ContentActivity;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private FavoriteFragment parent;
    private List<FavoriteItem> items = new ArrayList<>();

    public FavoriteAdapter(FavoriteFragment parent) {
        this.parent = parent;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, int position) {
        final FavoriteItem item = items.get(position);

        holder.tvFavorite.setText(item.getTitle());
        holder.favoriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getActivity(), ContentActivity.class);

                intent.putExtra("title", item.getTitle());
                intent.putExtra("slide_no", item.getSlideNo());
                intent.putExtra("content_id", item.getContentID());
                intent.putExtra("from_reading", false);

                parent.startActivity(intent);
            }
        });

        holder.favoriteLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                parent.removeFavoriteItem(item);

                return false;
            }
        });
    }

    public FavoriteItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(FavoriteItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<FavoriteItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.tv_favorite)
        TextView tvFavorite;
        @Bind(R.id.favorite_layout)
        LinearLayout favoriteLayout;

        public FavoriteViewHolder(View view) {
            super(view);
            this.view = view;

            ButterKnife.bind(this, view);
        }
    }
}
