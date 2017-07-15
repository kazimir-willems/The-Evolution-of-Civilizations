package seow.evolution.com.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.util.IabHelper;
import seow.evolution.com.util.IabResult;
import seow.evolution.com.util.Inventory;
import seow.evolution.com.util.Purchase;
import seow.evolution.com.util.SharedPrefManager;

public class SettingsFragment extends Fragment {

    @Bind(R.id.switch_admob)
    Switch switchAdmob;

    IabHelper mHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(SettingsFragment.this, v);

        switchAdmob.setChecked(SharedPrefManager.getInstance(getActivity()).getGoogleAds());

        switchAdmob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    //In-app Purchase
                    mHelper.launchPurchaseFlow(getActivity(), "com.jnspl.eoc.productid", 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
                } else {


                    SharedPrefManager.getInstance(getActivity()).saveGoogleAds(b);
                    SharedPrefManager.getInstance(getActivity()).saveGoogleInterestial(b);
               }
            }
        });

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvCMRrMF2kSo2/6k5GstsTR2n7Xo2XIFLKya6430XlhCq1TyGH54nCXZUUQnSdLrCsMLZOy5ZZhtdO0AHmNs0ZHhn4JhlDZUp3FhGVA/sr+8901dl4dlE45qqqueNOc5YVX1TWEO/bu16H12RIrEubcIBTwVAz0KDNtsdnLlA0A/uTm6DTRRaL4iwCHPxWVe50PpoiFj0JzZE7PwnPzOc1WPB78sxue+v11OyX1Brfc7LFsrRBCS8Qn7fjljLrG9dKO1yZOnXsw/daXkFPF8ZCoPzJzWA8EKgr4dV3G5ZEEpI2tnHtCo/vw/OSWTBHEH6EauD/HnuwEu7xtKXRvoGQwIDAQAB";

        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
           public void onIabSetupFinished(IabResult result)
           {
               if (!result.isSuccess()) {
                   Log.d("STAW", "In-app Billing setup failed: " +
                           result);
               } else {
                   Log.d("STAW", "In-app Billing is set up OK");
               }
           }
       });

        return v;
    }

    public static SettingsFragment newInstance() {
        SettingsFragment f = new SettingsFragment();

        return f;
    }

    @OnClick(R.id.tv_more_from_dev)
    void onClickMoreFromDev() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SEOW%20WEIJIE")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SEOW%20WEIJIE")));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals("com.jnspl.eoc.productid")) {
                consumeItem();
            }

        }
    };

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase("com.jnspl.eoc.productid"),
                        mConsumeFinishedListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                    } else {
                        // handle error
                    }
                }
            };
}