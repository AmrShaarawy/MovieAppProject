package com.example.amr.movieappproject1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private  final String TAG = this.getClass().getSimpleName();
    public DataBase mydp;
    GridView gridView;
    public View bigView;
    public String s;
    public String Location="popular";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public DataList dataList;
    public ArrayAdapter<DataObject> arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        bigView = view;
        if (get_POP() == null) {
            try {


                s = new FetchMovie().execute("popular").get();
                Gson gson = new Gson();
                dataList = gson.fromJson(s, DataList.class);
                try {
                    arrayAdapter = new CustomAdapter(getActivity(), dataList.results);
                    gridView = (GridView) view.findViewById(R.id.grid);
                    gridView.setAdapter(arrayAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Gson gson = new Gson();
                            String a = gson.toJson(dataList.results.get(i));


                            Intent intent = new Intent(getActivity(), Detailed.class);
                            intent.putExtra("values", a).putExtra("check", 2);
                            ((Callback) getActivity()).onItemSelected(intent);

                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            return view;
        } else {

            try {


                s = new FetchMovie().execute(get_POP()).get();
                Gson gson = new Gson();
                dataList = gson.fromJson(s, DataList.class);
                try {
                    arrayAdapter = new CustomAdapter(getActivity(), dataList.results);
                    gridView = (GridView) view.findViewById(R.id.grid);
                    gridView.setAdapter(arrayAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Gson gson = new Gson();
                            String a = gson.toJson(dataList.results.get(i));


                            Intent intent = new Intent(getActivity(), Detailed.class);
                            intent.putExtra("values", a).putExtra("check", 2);
                            ((Callback) getActivity()).onItemSelected(intent);

                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            return view;
        }
    }
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Intent intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.Top_Rated)
        {
            Store_POP("top_rated");
            arrayAdapter.clear();
            String s= null;
            try {
                s = new FetchMovie().execute("top_rated").get();
                Gson gson=new Gson();
                dataList=gson.fromJson(s,DataList.class);
                arrayAdapter=new CustomAdapter(getActivity(),dataList.results);
                gridView.setAdapter(arrayAdapter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }



        }
       else if (item.getItemId()==R.id.favourit)
        {
            arrayAdapter.clear();
            mydp=new DataBase(getActivity());

            DataObject dataObject=new DataObject();
            Cursor res=mydp.getAllData();
            StringBuffer buffer=new StringBuffer();
            ArrayList<DataObject> dataObjects=new ArrayList<>();
            while (res.moveToNext()) {
                dataObject=new DataObject();
                dataObject.id= res.getString(0);
                dataObject.title = res.getString(1);
                dataObject.poster_path = res.getString(2);
                dataObject.release_date = res.getString(3);
                dataObject.vote_average = res.getString(5);
                dataObject.overview =res.getString(4);

                Log.i(TAG, "onOptionsItemSelected: poster path "+dataObject.poster_path);

                dataObjects.add(dataObject);
            }

            arrayAdapter=new CustomAdapter(getActivity(),dataObjects);
            gridView.setAdapter(arrayAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Gson gson = new Gson();
                    String a = gson.toJson(arrayAdapter.getItem(i));
                    Intent intent = new Intent(getActivity(), Detailed.class);
                    intent.putExtra("values", a).putExtra("check",2);
                    startActivity(intent);
                }
            });

        }
        else if(item.getItemId()==R.id.popular)
        {
            Store_POP("popular");
            arrayAdapter.clear();
            String s= null;
            try {
                s = new FetchMovie().execute("popular").get();
                Gson gson=new Gson();
                dataList=gson.fromJson(s,DataList.class);
                arrayAdapter=new CustomAdapter(getActivity(),dataList.results);
                gridView.setAdapter(arrayAdapter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        return super.onOptionsItemSelected(item);

    }
    void Store_POP(String state)
    {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(getString(R.string.SHARED_KEY1), state).commit();
    }
    String get_POP()
    {
        return   PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.SHARED_KEY1),"NO DATA YET");
    }


}
