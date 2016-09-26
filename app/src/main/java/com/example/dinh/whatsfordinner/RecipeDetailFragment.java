package com.example.dinh.whatsfordinner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Recipe;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;
    private OnFragmentInteractionListener mListener;

    protected MyDataContext app = MyDataContext.getInstance();
    //protected Activity context;
    private int recipeIndex;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(String param1, String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();

        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (b != null && b.containsKey(MainActivity.BUNDLE_INDEX_KEY)) {
            recipeIndex = b.getInt(MainActivity.BUNDLE_INDEX_KEY, -1);
        }

        if (recipeIndex != -1)
            recipe = app.getRecipes().get(recipeIndex);
        else
            recipe = app.newRecipe();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView recipeNameTextView = (TextView) view.findViewById(R.id.recipeNameTextView);
        ImageView recipeImage = (ImageView) view.findViewById(R.id.recipeImageView);
        TextView ingredientsTextView = (TextView) view.findViewById(R.id.ingredientsTextView);
        TextView directionsTextView = (TextView) view.findViewById(R.id.directionsTextView);

        recipeNameTextView.setText(recipe.name);
        ingredientsTextView.setText(app.getIngredientsText(recipe));

        // Load the Recipe image
        if (recipe.imageUrl != null) {
            if (recipe.imageUrl.toString().toLowerCase().startsWith("http://")) {
                try {
                    new DownloadImageTask(recipeImage).execute(recipe.imageUrl.toString());
                } catch (Exception ex) {
                    recipeImage.setImageResource(R.drawable.recipe_default);
                }
            } else {
                try {
                    final Uri imageUri = recipe.imageUrl;

                    final InputStream imageStream = view.getContext().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    recipeImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    recipeImage.setImageResource(R.drawable.recipe_default);
                }
            }
        }

        directionsTextView.setText(recipe.directions);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onDetailInteraction(recipe);
        }
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
        void onDetailInteraction(Recipe recipe);
    }
}
