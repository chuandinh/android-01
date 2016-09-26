package com.example.dinh.whatsfordinner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dinh.whatsfordinner.data.Ingredient;
import com.example.dinh.whatsfordinner.data.MyDataContext;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroceriesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroceriesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroceriesListFragment extends ListFragment {
    List<Ingredient> ingredients;

    private OnFragmentInteractionListener mListener;
    private MyDataContext app = MyDataContext.getInstance();

    public GroceriesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method MyApplicationto create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeListFragment.
     */
    public static GroceriesListFragment newInstance() {
        GroceriesListFragment fragment = new GroceriesListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.app = (MyApplication) getActivity().getApplication();

        ingredients = new ArrayList<Ingredient>();
        for (Ingredient i : app.getShoppingList().keySet())
            ingredients.add(i);

        GroceryArrayAdapter adapter = new GroceryArrayAdapter(getActivity(),
                R.layout.fragment_ingredient_item,
                ingredients);

        setListAdapter(adapter);
    }

    /**
     * FragmentList to display the item
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_list, container, false);
    }

    /**
     * FragmentList Item click event
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Recipe recipe = recipes.get(position);
        mListener.onListInteraction(position, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        //AdapterView<?> arg0, View v, int index, long arg3
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int index, long arg3) {

                mListener.onListInteraction(index, true);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListInteraction(int index, boolean isLongClicked);
    }
}
