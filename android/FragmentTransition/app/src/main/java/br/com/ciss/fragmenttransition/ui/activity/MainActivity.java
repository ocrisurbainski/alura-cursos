package br.com.ciss.fragmenttransition.ui.activity;

import br.com.ciss.fragmenttransition.R;
import br.com.ciss.fragmenttransition.ui.fragment.FirstFragment.FirstFragmentListener;
import br.com.ciss.fragmenttransition.ui.fragment.FirstFragmentDirections;
import br.com.ciss.fragmenttransition.ui.fragment.SecondFragment.SecondFragmentListener;
import br.com.ciss.fragmenttransition.ui.fragment.SecondFragmentDirections;
import br.com.ciss.fragmenttransition.ui.fragment.SecondFragmentDirections.ActionSecondFragmentToThirdFragment;
import br.com.ciss.fragmenttransition.ui.fragment.ThirdFragment.ThirdFragmentListener;
import br.com.ciss.fragmenttransition.ui.fragment.ThirdFragmentDirections;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity
        extends AppCompatActivity
        implements FirstFragmentListener, SecondFragmentListener, ThirdFragmentListener {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public void onFirstClicked() {

        navController.navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment());
    }

    @Override
    public void onSecondClicked() {

        ActionSecondFragmentToThirdFragment action = SecondFragmentDirections
                .actionSecondFragmentToThirdFragment(1);

        navController.navigate(action);
    }

    @Override
    public void onThirdClicked() {

        navController.navigate(ThirdFragmentDirections.actionThirdFragmentToFirstFragment());
    }

}