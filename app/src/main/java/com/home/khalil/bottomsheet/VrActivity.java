package com.home.khalil.bottomsheet;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

public class VrActivity extends AppCompatActivity {

    private VrPanoramaView panoWidgetView;
    private VrPanoramaView panoWidgetView2;
    private ImageLoaderTask backgroundImageLoaderTask;
    private ImageLoaderTask backgroundImageLoaderTask2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);
        panoWidgetView2 = (VrPanoramaView) findViewById(R.id.pano_view2);
    }

    private synchronized void loadPanoImage() {
        ImageLoaderTask task = backgroundImageLoaderTask;
        ImageLoaderTask task2 = backgroundImageLoaderTask2;
        if (task != null && !task.isCancelled()) {
            // Cancel any task from a previous loading.
            task.cancel(true);
        }

        if (task2 != null && !task2.isCancelled()) {
            // Cancel any task from a previous loading.
            task2.cancel(true);
        }

        // pass in the name of the image to load from assets.
        VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;

        // use the name of the image in the assets/ directory.
        String panoImageName = "dd3_sphere.jpg";
        String panoImageName2 = "dd4_sphere.jpg";

        // create the task passing the widget view and call execute to start.
        task = new ImageLoaderTask(panoWidgetView, viewOptions, panoImageName);
        task2 = new ImageLoaderTask(panoWidgetView2, viewOptions, panoImageName2);
        task.execute(this.getAssets());
        backgroundImageLoaderTask = task;
        task2.execute(this.getAssets());
        backgroundImageLoaderTask2 = task2;
    }

    private synchronized void loadPanoImage2() {
        ImageLoaderTask task = backgroundImageLoaderTask;
        if (task != null && !task.isCancelled()) {
            // Cancel any task from a previous loading.
            task.cancel(true);
        }

        // pass in the name of the image to load from assets.
        VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;

        // use the name of the image in the assets/ directory.
        String panoImageName = "dd4_sphere.jpg";

        // create the task passing the widget view and call execute to start.
        task = new ImageLoaderTask(panoWidgetView2, viewOptions, panoImageName);
        task.execute(this.getAssets());
        backgroundImageLoaderTask = task;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadPanoImage();
       // loadPanoImage2();
    }
}
