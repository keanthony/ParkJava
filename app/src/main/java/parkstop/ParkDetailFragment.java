package parkstop;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ParkDetailFragment extends Fragment
{
    private Park park;

    public static final String EXTRA_PARK_ID = "parkstop.park_id";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //UUID uuid = (UUID)getArguments().getSerializable(EXTRA_PARK_ID);
        park = (Park) getActivity().getIntent().getSerializableExtra("thePark");
        //park = ParkData.getInstance(getActivity()).getPark(uuid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_park_detail, parent, false);

        // All your beautiful widgets

        TextView parkNameTxt = (TextView) v.findViewById(R.id.detailNameTxt);
        parkNameTxt.setText(park.getName());

        TextView parkAddressTxt = (TextView) v.findViewById(R.id.detailAddressTxt);
        parkAddressTxt.setText(park.getAddress());

        Button button = (Button) v.findViewById(R.id.getDirectionsBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleMapJSON mapJSON = new GoogleMapJSON(getActivity(), park.getAddress());
                try {
                    String s = mapJSON.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                startActivityForResult(viewOnMap(mapJSON.getLat(), mapJSON.getLng()), 0);
            }
        });

        return v;
    }

    public Intent viewOnMap(String lat, String lng) {
        //return new Intent(Intent.ACTION_VIEW,
                //Uri.parse(String.format("geo:%s,%s", lat, lng)));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.streetview:cbll=" + lat + "," +lng));
        intent.setPackage("com.google.android.apps.maps");
        return intent;

    }

    public static ParkDetailFragment newInstance(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PARK_ID, crimeId);

        ParkDetailFragment fragment = new ParkDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
