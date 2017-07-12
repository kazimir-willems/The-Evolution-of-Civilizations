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

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.util.SharedPrefManager;

public class SettingsFragment extends Fragment implements BillingProcessor.IBillingHandler{

    @Bind(R.id.switch_admob)
    Switch switchAdmob;

    BillingProcessor bp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(SettingsFragment.this, v);

        switchAdmob.setChecked(SharedPrefManager.getInstance(getActivity()).getGoogleAds());

        switchAdmob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean isAvailable = BillingProcessor.isIabServiceAvailable(getActivity());
                if(!isAvailable) {
                    Log.v("EOC", "Unavailable");
                } else {
                    Log.v("EOC", "Available");
                }

                if(!b) {
                    //In-app Purchase
                    bp.purchase(getActivity(), "android.test.purchased");
                } else {


                    SharedPrefManager.getInstance(getActivity()).saveGoogleAds(b);
                    SharedPrefManager.getInstance(getActivity()).saveGoogleInterestial(b);
               }
            }
        });

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvCMRrMF2kSo2/6k5GstsTR2n7Xo2XIFLKya6430XlhCq1TyGH54nCXZUUQnSdLrCsMLZOy5ZZhtdO0AHmNs0ZHhn4JhlDZUp3FhGVA/sr+8901dl4dlE45qqqueNOc5YVX1TWEO/bu16H12RIrEubcIBTwVAz0KDNtsdnLlA0A/uTm6DTRRaL4iwCHPxWVe50PpoiFj0JzZE7PwnPzOc1WPB78sxue+v11OyX1Brfc7LFsrRBCS8Qn7fjljLrG9dKO1yZOnXsw/daXkFPF8ZCoPzJzWA8EKgr4dV3G5ZEEpI2tnHtCo/vw/OSWTBHEH6EauD/HnuwEu7xtKXRvoGQwIDAQAB";

        bp = new BillingProcessor(getActivity(), base64EncodedPublicKey, this);

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
    public void onBillingInitialized() {
    /*
    * Called when BillingProcessor was initialized and it's ready to purchase
    */
        Log.v("EOC", "Ready");
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
    /*
    * Called when requested PRODUCT ID was successfully purchased
    */
        Log.v("EOC", "Sth Purchased");
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
    /*
    * Called when some error occurred. See Constants class for more details
    *
    * Note - this includes handling the case where the user canceled the buy dialog:
    * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
    */
        Log.v("EOC", String.valueOf(errorCode));
    }

    @Override
    public void onPurchaseHistoryRestored() {
    /*
    * Called when purchase history was restored and the list of all owned PRODUCT ID's
    * was loaded from Google Play
    */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}