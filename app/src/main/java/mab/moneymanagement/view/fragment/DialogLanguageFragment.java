package mab.moneymanagement.view.fragment;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import mab.moneymanagement.R;

/**
 * Created by Gihan on 3/2/2018.
 */

public class DialogLanguageFragment extends DialogFragment {

    static DialogLanguageFragment newInstance() {
        return new DialogLanguageFragment();
    }

    void showDialog() {
        // Create the fragment and show it as a dialog.
        DialogFragment newFragment = DialogLanguageFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        newFragment = DialogLanguageFragment.newInstance();
        ft.replace(R.id.setting_fragment, newFragment);
        ft.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View language_layout = inflater.inflate(R.layout.language, container, false);


        return language_layout;
    }


}
