package cn.edu.gdmec.s07131018.demo_loader;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.R.anim;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView lv;
	private LoaderManager loaderManager;
	private List<People> peoples;
	private ArrayAdapter<People> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		
		loaderManager = getLoaderManager();
		loaderManager.initLoader(0, null, new LoaderCallbacks<Cursor>() {

			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				Uri uri = Uri.parse("content://com.android.contacts/contacts");
				return new CursorLoader(MainActivity.this, uri, null, null,
						null, null);
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
				peoples = new ArrayList<People>();
				while (data.moveToNext()) {
					String id = data.getString(data
							.getColumnIndex("_id"));
					People people = new People(Integer.parseInt(id));
					Log.i("info",id);
					peoples.add(people);
				}
				if (adapter == null) {
					adapter = new ArrayAdapter<People>(MainActivity.this,
							android.R.layout.simple_list_item_1,
							android.R.id.text1, peoples);
					lv.setAdapter(adapter);
				}
				
				else{
					adapter.clear();
					adapter.addAll(peoples);
					lv.setAdapter(adapter);
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				// TODO Auto-generated method stub

			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
