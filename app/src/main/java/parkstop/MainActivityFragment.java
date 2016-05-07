package parkstop;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivityFragment extends Fragment
{

    private EditText zipCodeEdTxt;
    private CheckBox parkingCB;
    private CheckBox dogParkCB;
    private CheckBox playgroundCB;
    private CheckBox restroomsCB;
    private CheckBox hikeTrailsCB;
    private CheckBox joggingCB;

    public JSONTask task;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_main, parent, false);


        zipCodeEdTxt = (EditText) v.findViewById(R.id.zipcodeEdit);


        // CheckBoxes
        parkingCB = (CheckBox) v.findViewById(R.id.parkingCB);
        dogParkCB = (CheckBox) v.findViewById(R.id.dogParkCB);
        playgroundCB = (CheckBox) v.findViewById(R.id.playgroundCB);
        restroomsCB = (CheckBox) v.findViewById(R.id.restroomsCB);
        hikeTrailsCB = (CheckBox) v.findViewById(R.id.hikeTrailsCB);
        joggingCB = (CheckBox) v.findViewById(R.id.picnicAreaCB);

        // Search Button
        // TODO: Display a list of search results. Data take from here, requests defined here
        Button searchBtn = (Button) v.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                // Call to async task as button is pushed and pauses UI Thread
                task = new JSONTask(getActivity(), zipCodeEdTxt.getText().toString(), getAllAttributes());
                try {
                    String result = task.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                // Loading intent
                Intent intent = new Intent(getActivity(), ResultsListActivity.class);
                intent.putExtra("parksList", task.getParksList());
                startActivity(intent);
            }
        });

        // Reset Button
        Button resetBtn = (Button) v.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });



        return v;
    }

    /**Method: getAllAttributes
     *
     * @return an arraylist of all attributes
     */
    private ArrayList<String> getAllAttributes()
    {
        ArrayList<String> attributes = new ArrayList<>();

        if(restroomsCB.isChecked())
            attributes.add(0,"TRUE");
        else
        {
            attributes.add(0,"FALSE");
        }

        if(joggingCB.isChecked())
            attributes.add(1, "TRUE");
        else
        {
            attributes.add(1, "FALSE");
        }

        if(playgroundCB.isChecked())
            attributes.add(2,"TRUE");
        else
        {
            attributes.add(2,"FALSE");
        }

        if(dogParkCB.isChecked())
            attributes.add(3,"TRUE");
        else
        {
            attributes.add(3,"FALSE");
        }

        // CHECKBOXES DISABLED FOR NOW
//        if(hikeTrailsCB.isChecked())
//            attributes.add((String) hikeTrailsCB.getText());
//
//        if(joggingCB.isChecked())
//            attributes.add((String) joggingCB.getText());

        return attributes;
    }

    /**Method: reset
     * Clears all the widgets on the screen
     */
    private void reset()
    {
        zipCodeEdTxt.getText().clear();

        if(parkingCB.isChecked())
            parkingCB.toggle();

        if(dogParkCB.isChecked())
            dogParkCB.toggle();

        if(playgroundCB.isChecked())
            playgroundCB.toggle();

        if(restroomsCB.isChecked())
            restroomsCB.toggle();

        if(hikeTrailsCB.isChecked())
            hikeTrailsCB.toggle();

        if(joggingCB.isChecked())
            joggingCB.toggle();
    }



}
