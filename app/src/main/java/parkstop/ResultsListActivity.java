package parkstop;
import android.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultsListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new ResultsListFragment();
    }
}
