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

public class ThirdFragment extends Fragment {

    private ThirdFragmentListener listener;

    public ThirdFragment() {
        // Required empty public constructor
    }

    public static ThirdFragment newInstance() {

        ThirdFragment fragment = new ThirdFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ThirdFragmentArgs args = ThirdFragmentArgs.fromBundle(getArguments());

        View view = inflater.inflate(R.layout.fragment_third, container, false);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(args.getIdUsuario() + "");
        textView.setClickable(true);
        textView.setOnClickListener(v -> {

            if (listener != null) {

                listener.onThirdClicked();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if (context instanceof ThirdFragmentListener) {

            listener = (ThirdFragmentListener) context;
        }
    }

    public interface ThirdFragmentListener {

        void onThirdClicked();

    }

}