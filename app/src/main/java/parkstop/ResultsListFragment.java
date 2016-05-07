package parkstop;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsListFragment extends ListFragment implements AdapterView.OnItemClickListener{

    private static final String LOG_TAG = ResultsListFragment.class.getSimpleName();
    private ArrayList<Park> parks;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        parks = (ArrayList<Park>) getActivity().getIntent().getSerializableExtra("parksList");

        if(parks.size() == 0)
        {
            Toast.makeText(getActivity().getApplicationContext(), "No parks match criteria!", Toast.LENGTH_LONG).show();
        }

        ParkAdapter adapter = new ParkAdapter(parks);


        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private class ParkAdapter extends ArrayAdapter<Park>
    {
        /**
         * private inner class constructor for arrayadapter
         */
        public ParkAdapter(ArrayList<Park> parks)
        {
            super(getActivity(), 0, parks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.park_item, null);
            }

            Park park = getItem(position);

            TextView parkNameTxt = (TextView) convertView.findViewById(R.id.parkNameTxt);
            parkNameTxt.setText(park.getName());

            TextView parkDistanceTxt = (TextView) convertView.findViewById(R.id.parkDistanceTxt);
            parkDistanceTxt.setText(park.getZip() + "");

            TextView parkAddressTxt = (TextView) convertView.findViewById(R.id.parkAddressTxt);
            parkAddressTxt.setText(park.getAddress());

            return convertView;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        Park park = ((ParkAdapter) getListAdapter()).getItem(position);
        Log.d(LOG_TAG, park.getName() + " was clicked");

        // start DetailActivity
        Intent i = new Intent(getActivity(), ParkDetailActivity.class);
        i.putExtra("thePark", park);
        startActivity(i);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((ParkAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
