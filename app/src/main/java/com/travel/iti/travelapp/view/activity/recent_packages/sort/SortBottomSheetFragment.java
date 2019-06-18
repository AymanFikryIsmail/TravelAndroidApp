package com.travel.iti.travelapp.view.activity.recent_packages.sort;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;


public class SortBottomSheetFragment extends BottomSheetDialogFragment {
   // private PackagesAdapter packagesAdapter;
    private EditText editTextPriceRange;
    Button btnApply;
    private SortFragmentinterface sortFragmentinterface;
    RadioGroup radioGroup;
    AlertDialog alertDialog1;
    private String priceRange = "" ;
    private  String sortType = "";
    private int price = 0;
    CharSequence[] values = {" Low to High "," High to Low "};


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sort_bottom_sheet, null);
        dialog.setContentView(view);

        radioGroup = view.findViewById(R.id.sort_radio_group);
        radioGroup.clearCheck();
        editTextPriceRange = view.findViewById(R.id.sort_price_range);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId != -1) {
                    Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
                sortType = rb.getText().toString();
                createAlertDialogWithRadioButtonGroup() ;
            }
        });

        sortFragmentinterface = (SortFragmentinterface) getActivity();

        btnApply = view.findViewById(R.id.apply_sort_btn);
        btnApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (sortFragmentinterface != null) {
                    sortFragmentinterface.passSortData(priceRange , sortType, price);
                }

                dialog.dismiss();
            }
        });
    }

    private void createAlertDialogWithRadioButtonGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Price Range");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        editTextPriceRange.setText("Low to High");
                        priceRange = "asc";
                        Toast.makeText(getContext(), "Low to High", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        editTextPriceRange.setText("High to Low");
                        priceRange ="desc";//editTextPriceRange.getText().toString();
                        Toast.makeText(getContext(), "High to Low", Toast.LENGTH_LONG).show();
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    public SortBottomSheetFragment() {
        // Required empty public constructor
    }

}
