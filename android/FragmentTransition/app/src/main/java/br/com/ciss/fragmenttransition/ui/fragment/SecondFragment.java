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

public class SecondFragment extends Fragment {

    private SecondFragmentListener listener;

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance() {

        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        TextView textView = view.findViewById(R.id.textView);
        textView.setClickable(true);
        textView.setOnClickListener(v -> {

            if (listener != null) {

                listener.onSecondClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if (context instanceof SecondFragmentListener) {

            listener = (SecondFragmentListener) context;
        }
    }

    public interface SecondFragmentListener {

        void onSecondClicked();

    }

}