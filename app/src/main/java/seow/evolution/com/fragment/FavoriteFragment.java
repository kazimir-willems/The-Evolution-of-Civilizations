package seow.evolution.com.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import seow.evolution.com.R;
import seow.evolution.com.adapter.FavoriteAdapter;
import seow.evolution.com.db.FavoriteDB;
import seow.evolution.com.model.FavoriteItem;
import seow.evolution.com.ui.ChapterActivity;
import seow.evolution.com.ui.ContentActivity;
import seow.evolution.com.ui.DividerItemDecoration;

public class FavoriteFragment extends Fragment {

    @Bind(R.id.favorite_list)
    RecyclerView favoriteList;

    private ArrayList<FavoriteItem> favoriteItems = new ArrayList<>();

    private FavoriteAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(FavoriteFragment.this, v);

        FavoriteDB db = new FavoriteDB(getActivity());
        favoriteItems = db.fetchAllFavorites();

        favoriteList.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoriteList.setLayoutManager(mLinearLayoutManager);
        favoriteList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        adapter = new FavoriteAdapter(FavoriteFragment.this);
        favoriteList.setAdapter(adapter);

        adapter.addItems(favoriteItems);
        adapter.notifyDataSetChanged();

        return v;
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment f = new FavoriteFragment();

        return f;
    }

    public void removeFavoriteItem(final FavoriteItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.remove_favorite_item);
        builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FavoriteDB db = new FavoriteDB(getActivity());
                db.removeItem(item.getId());

                favoriteItems = db.fetchAllFavorites();
                adapter.addItems(favoriteItems);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}