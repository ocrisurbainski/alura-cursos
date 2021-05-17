package br.com.ciss.fragmenttransition.ui.fragment;

import br.com.ciss.fragmenttransition.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    private FirstFragmentListener listener;

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance() {

        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        TextView textView = view.findViewById(R.id.textView);
        textView.setClickable(true);
        textView.setOnClickListener(v -> {

            if (listener != null) {

                listener.onFirstClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if (context instanceof FirstFragmentListener) {

            listener = (FirstFragmentListener) context;
        }
    }

    public interface FirstFragmentListener {

        void onFirstClicked();

    }

}