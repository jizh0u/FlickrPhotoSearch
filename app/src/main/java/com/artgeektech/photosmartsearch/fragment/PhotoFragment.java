package com.artgeektech.photosmartsearch.fragment;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.artgeektech.photosmartsearch.R;
import com.artgeektech.photosmartsearch.model.GalleryItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment {

    public static final String TAG = PhotoFragment.class.getSimpleName();

    private GalleryItem mItem;
    private ImageView mPhoto;
    private Bitmap bitmap;

    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        mItem = (GalleryItem) getActivity().getIntent().getSerializableExtra("item");
        mPhoto = (ImageView) view.findViewById(R.id.photo);
        Glide.with(this).load(mItem.getUrl()).thumbnail(0.5f).into(mPhoto);

        Button buttonSetWallpaper = (Button) view.findViewById(R.id.wallpaper);

        Glide.with(this).load(mItem.getUrl()).asBitmap().into(
                new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        bitmap = resource; // Possibly runOnUiThread()
                        Log.d(TAG, "Get bitmap from url here.");
                    }
        });
        //// FIXME: 2016/10/25 Can not set wall paper twice.
        buttonSetWallpaper.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager myWallpaperManager = WallpaperManager
                        .getInstance(getActivity());
                try {
                    // Set the clicked bitmap
                    myWallpaperManager.setBitmap(bitmap);
                    Toast.makeText(
                            getActivity(), "Wallpaper set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(
                            getActivity(), "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });

        return view;
    }

}
