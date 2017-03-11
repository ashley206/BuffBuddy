package com.fractaltechnologies.ashleyw.buffbuddy;

import android.view.View;

/**
 * Created by Ashley H on 3/10/2017.
 */

public interface DialogInputInterface {
    // onBuildDialog() is called when the dialog builder is ready to accept a view to insert
    // into the dialog
    View onBuildDialog();
    // onCancel() is called when the user clicks on 'Cancel'
    void onCancel();
    // onResult(View v) is called when the user clicks on 'Ok'
    void onResult(View v);

}
